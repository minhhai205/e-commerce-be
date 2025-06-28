package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.ShopRequestDTO;
import com.minhhai.ecommercebe.dto.response.ShopDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ShopResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.ShopMapper;
import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.model.Shop;
import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.repository.RoleRepository;
import com.minhhai.ecommercebe.repository.ShopRepository;
import com.minhhai.ecommercebe.repository.UserRepository;
import com.minhhai.ecommercebe.util.commons.SecurityUtil;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShopMapper shopMapper;

    public ShopResponseDTO adminCreateShop(Long userId, ShopRequestDTO shopRequestDTO) {
        log.info("----------------- Admin Create Shop -------------------");

        User userRegisterShop = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return processCreateShop(userRegisterShop, shopRequestDTO);
    }

    public ShopResponseDTO sellerRegisterShop(ShopRequestDTO shopRequestDTO) {
        log.info("----------------- Seller Register Shop ------------------");

        User userRegisterShop = SecurityUtil.getCurrentUser();

        return processCreateShop(userRegisterShop, shopRequestDTO);
    }

    private ShopResponseDTO processCreateShop(User userRegisterShop, ShopRequestDTO shopRequestDTO) {

        if (userRegisterShop.getRoles().stream().anyMatch(role -> role.getName().equals("SELLER"))) {
            throw new AppException(ErrorCode.SELLER_ALREADY_HAS_SHOP);
        }

        Role role = roleRepository.findByName("SELLER").orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        userRegisterShop.getRoles().add(role);
        userRepository.save(userRegisterShop);

        Shop newShop = shopMapper.toEntity(shopRequestDTO);
        newShop.setUser(userRegisterShop);
        shopRepository.save(newShop);

        log.info("------------------ create shop successfully ----------------");
        return shopMapper.toResponseDTO(newShop);
    }

    public ShopDetailResponseDTO getShopDetailByShopId(Integer shopId) {
        log.info("------------------ Get Shop Detail ----------------");

        Shop shop = shopRepository.findShopById(shopId).orElseThrow(
                () -> new AppException(ErrorCode.SHOP_NOT_EXISTED));

        log.info("------------------ Get Shop Detail Successfully ----------------");
        return shopMapper.toDetailResponseDTO(shop);
    }

    public ShopDetailResponseDTO getMyShopDetail() {
        log.info("------------------ Get My Shop Detail ----------------");

        User shopOwner = SecurityUtil.getCurrentUser();

        Shop shop = shopRepository.findShopByUserId(shopOwner.getId()).orElseThrow(
                () -> new AppException(ErrorCode.SHOP_NOT_EXISTED));

        log.info("------------------ Get My Shop Detail Successfully ----------------");
        return shopMapper.toDetailResponseDTO(shop);
    }


}
