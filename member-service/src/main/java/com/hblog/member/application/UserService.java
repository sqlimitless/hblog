package com.hblog.member.application;

import com.hblog.member.application.dto.UserDTO;
import com.hblog.member.domain.Authority;
import com.hblog.member.domain.Role;
import com.hblog.member.domain.UserEntity;
import com.hblog.member.infra.UserRepository;
import com.hblog.member.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO createUser(UserDTO userdto) {
        UserEntity userEntity = null;
        try {
            userEntity = UserEntity.builder()
                    .userId(userdto.getUserId())
                    .password(SHA256.encrypt(userdto.getPassword()))
                    .authority(new HashSet<>())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        userEntity.addRole(userdto.getRoles());
        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public List<UserDTO> getUserByAll() {
        List<UserDTO> result = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        userEntities.forEach(v -> {
            result.add(modelMapper.map(v, UserDTO.class));
        });
        return result;
    }

    public UserDTO getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow();
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO loginUser(UserDTO userDto) {
        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId()).orElseThrow();
        try {
            if (!SHA256.encrypt(userDto.getPassword()).equals(userEntity.getPassword())) {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Set<Role> roles = userEntity.getAuthority().stream().map(Authority::getRole).collect(Collectors.toSet());
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        userDTO.setRoles(roles);
        return userDTO;
    }
}
