package elrejunte.demo.mapper;

import elrejunte.demo.entity.Category;
import elrejunte.demo.entity.Product;
import elrejunte.demo.dto.ProductDTO;

import elrejunte.demo.dto.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDTO(Product p) {
        if (p == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        if (p.getCategory() != null) {
            dto.setCategoryId(p.getCategory().getId());
            dto.setCategoryName(p.getCategory().getName());
        }
        return dto;
    }


    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        if (dto.getCategoryId() != null) {
            p.setCategory(new Category(dto.getCategoryId()));
        }
        return p;
    }
}
