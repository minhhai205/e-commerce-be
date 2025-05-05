package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.mapper.UserMapper;
import com.minhhai.ecommercebe.model.Cart;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Long saveUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())
                || userRepository.existsByEmail(userRequestDTO.getEmail())) {
            return -1L;
        }

        User user = userMapper.toEntity(userRequestDTO);
        System.out.println(user.getAddresses());

        // set address
        user.getAddresses().forEach(address -> {
            address.setUser(user);
        });

        // set card
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        // set role

        // hash password

        userRepository.save(user);

        log.info("User has added successfully, userId={}", user.getId());

        return user.getId();
    }
}
