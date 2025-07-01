package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.ReviewRequestDTO;
import com.minhhai.ecommercebe.dto.response.ReviewResponseDTO;
import com.minhhai.ecommercebe.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO dto);

    @Mapping(target = "userId", source = "user", qualifiedByName = "toUserId")
    ReviewResponseDTO toResponseDTO(Review entity);
}
