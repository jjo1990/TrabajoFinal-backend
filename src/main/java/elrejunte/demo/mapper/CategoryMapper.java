package elrejunte.demo.mapper;

import elrejunte.demo.entity.Category;
import elrejunte.demo.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category c) {
        if (c == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        return dto;
    }


    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category c = new Category();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());
        return c;
    }
}
