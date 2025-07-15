package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.CategoryRequestDTO;
import com.minhhai.ecommercebe.dto.response.CategoryResponseDTO;
import com.minhhai.ecommercebe.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper {
    @Named("toCategoryName")
    default String toCategoryName(Category category) {
        return category != null ? category.getName() : null;
    }

    Category toEntity(CategoryRequestDTO dto);
    
    CategoryResponseDTO toResponseDTO(Category entity);
}
