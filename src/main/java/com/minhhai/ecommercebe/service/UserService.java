package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiErrorResponse;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.AddressMapper;
import com.minhhai.ecommercebe.mapper.UserMapper;
import com.minhhai.ecommercebe.model.Address;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
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
        Set<Role> roles = roleRepository.findByNameIn(userRequestDTO.getRoleNames());
        if (userRequestDTO.getRoleNames().size() != roles.size()) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        user.setRoles(roles);

        // hash password
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        userRepository.save(user);

        log.info("-------------User has added successfully, userId={}-------------", user.getId());

        return user.getId();
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getUsername().equals(userRequestDTO.getUsername())
                && userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        } else if (!user.getEmail().equals(userRequestDTO.getEmail())
                && userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setUrlAvatar(userRequestDTO.getUrlAvatar());
        user.setGender(userRequestDTO.getGender());
        user.setStatus(userRequestDTO.getStatus());

        // set address
        if (!userRequestDTO.getAddresses().isEmpty()) {
            Set<Address> addresses = userRequestDTO.getAddresses()
                    .stream().map(addressMapper::toEntity).collect(Collectors.toSet());
            user.getAddresses().clear();
            user.getAddresses().addAll(addresses);
            user.getAddresses().forEach(address -> address.setUser(user));
        }

        // set role
        if (!user.getRoles().isEmpty()) {
            Set<Role> roles = roleRepository.findByNameIn(userRequestDTO.getRoleNames());
            if (userRequestDTO.getRoleNames().size() != roles.size()) {
                throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
            }
            user.setRoles(roles);
        }

        userRepository.save(user);

        log.info("---------User has updated successfully, userId={}----------", user.getId());

        return userMapper.toResponseDTO(user);
    }

}
