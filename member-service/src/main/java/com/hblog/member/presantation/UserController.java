package com.hblog.member.presantation;

import com.hblog.member.application.RefreshTokenService;
import com.hblog.member.application.UserService;
import com.hblog.member.application.dto.UserDTO;
import com.hblog.member.jwt.JwtTokenProvider;
import com.hblog.member.jwt.Token;
import com.hblog.member.presantation.request.UserRequest;
import com.hblog.member.presantation.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
        UserDTO userDto = modelMapper.map(userRequest, UserDTO.class);
        UserDTO user = userService.loginUser(userDto);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        Token token = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole());
        refreshTokenService.saveRefreshToken(user.getUserId(), token.getRefreshToken());
        userResponse.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request){
        String refreshToken = request.getHeader("RefreshToken");
        String accessToken = refreshTokenService.findRefreshToken(refreshToken);
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserDTO userDto = modelMapper.map(userRequest, UserDTO.class);
        UserDTO user = userService.createUser(userDto);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUser() {
        List<UserDTO> userList = userService.getUserByAll();
        List<UserResponse> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(modelMapper.map(v, UserResponse.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String userId) {
        UserDTO userDto = userService.getUserByUserId(userId);
        UserResponse result = modelMapper.map(userDto, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
