package elrejunte.demo.impl;

import elrejunte.demo.dto.ProductDTO;
import elrejunte.demo.entity.Category;
import elrejunte.demo.entity.Product;
import elrejunte.demo.exception.ResourceNotFoundException;
import elrejunte.demo.mapper.ProductMapper;
import elrejunte.demo.repository.CategoryRepository;
import elrejunte.demo.repository.ProductRepository;
import elrejunte.demo.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;


    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }


    @Override
    public List<ProductDTO> findAll() {
        return productRepo.findAll().stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    public ProductDTO findById(Long id) {
        Product p = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return ProductMapper.toDTO(p);
    }


    @Override
    public ProductDTO create(ProductDTO dto) {
        Product p = ProductMapper.toEntity(dto);
        if (dto.getCategoryId() != null) {
            Category c = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + dto.getCategoryId()));
            p.setCategory(c);
        }
        return ProductMapper.toDTO(productRepo.save(p));
    }


    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());
        if (dto.getCategoryId() != null) {
            Category c = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + dto.getCategoryId()));
            existing.setCategory(c);
        } else {
            existing.setCategory(null);
        }
        return ProductMapper.toDTO(productRepo.save(existing));
    }


    @Override
    public void delete(Long id) {
        if (!productRepo.existsById(id)) throw new ResourceNotFoundException("Product not found: " + id);
        productRepo.deleteById(id);
    }

}
