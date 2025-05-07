package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiErrorResponse;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.UserMapper;
import com.minhhai.ecommercebe.model.Cart;
import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.RoleRepository;
import com.minhhai.ecommercebe.repository.UserRepository;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Long saveUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())
                || userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toEntity(userRequestDTO);

        // set address
        user.getAddresses().forEach(address -> {
            address.setUser(user);
        });

        // set card
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        // set role
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // hash password
        passwordEncoder.encode(user.getPassword());

        userRepository.save(user);

        log.info("User has added successfully, userId={}", user.getId());

        return user.getId();
    }
}
