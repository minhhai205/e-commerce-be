package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.CategoryRequestDTO;
import com.minhhai.ecommercebe.dto.response.CategoryResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.CategoryMapper;
import com.minhhai.ecommercebe.model.Category;
import com.minhhai.ecommercebe.repository.CategoryRepository;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDTO> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(categoryMapper::toResponseDTO).toList();
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);

        category = categoryRepository.save(category);

        log.info("-----------Category created id = {}-----------", category.getId());
        return categoryMapper.toResponseDTO(category);
    }

    public CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO) {

        Category categoryUpdate = categoryRepository.findById(categoryId).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        categoryUpdate.setName(categoryRequestDTO.getName());
        categoryUpdate.setDescription(categoryRequestDTO.getDescription());

        categoryRepository.save(categoryUpdate);
        log.info("-----------Category updated id = {}-----------", categoryId);
        return categoryMapper.toResponseDTO(categoryUpdate);
    }
}
