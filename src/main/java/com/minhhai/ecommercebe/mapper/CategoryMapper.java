package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.model.Category;
import com.minhhai.ecommercebe.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper {
    @Named("toCategoryName")
    default String toCategoryName(Category category) {
        return category != null ? category.getName() : null;
    }
}
