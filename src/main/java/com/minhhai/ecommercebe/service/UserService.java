package com.minhhai.ecommercebe.service;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.PageResponse;
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
import com.minhhai.ecommercebe.repository.specification.SpecificationsBuilder;
import com.minhhai.ecommercebe.util.commons.AppConst;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public Long createUser(UserRequestDTO userRequestDTO) {
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
//        Cart cart = new Cart();
//        user.setCart(cart);
//        cart.setUser(user);

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

    /**
     * Get all user with pagination, search and filter.
     *
     * @param pageable: {
     *                  "page": x,
     *                  "size": y,
     *                  "sort": ["abc:desc|asc", "def:desc|asc"]
     *                  }
     * @param filters:  format: "([']?)([\\w]+)([><:~!])(\\*?)([^*]+)(\\*?)"
     * @return List UserResponseDTO.
     */
    public PageResponse<List<UserResponseDTO>> getAllUsers(Pageable pageable, String[] filters) {
        log.info("-------- Get users by specifications --------");

        if (filters != null) {
            SpecificationsBuilder builder = new SpecificationsBuilder();
            Pattern pattern = Pattern.compile(AppConst.SEARCH_SPEC_OPERATOR);

            for (String data : filters) {
                Matcher matcher = pattern.matcher(data);

                if (matcher.find()) {
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4),
                            matcher.group(5), matcher.group(6));
                }
            }

            Page<User> users = userRepository.findAll(Objects.requireNonNull(builder.<User>build()), pageable);
            return convertToPageResponse(users, pageable);
        }

        return convertToPageResponse(userRepository.findAll(pageable), pageable);
    }

    private PageResponse<List<UserResponseDTO>> convertToPageResponse(Page<User> users, Pageable pageable) {
        List<Long> userIds = users.getContent().stream().map(User::getId).toList();

        List<User> userList = userRepository.findByIdIn(userIds);

        List<UserResponseDTO> response = userList.stream().map(userMapper::toResponseDTO).toList();

        log.info("--------- Get users successfully ----------");

        return PageResponse.<List<UserResponseDTO>>builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPage(users.getTotalPages())
                .items(response)
                .build();
    }

}
