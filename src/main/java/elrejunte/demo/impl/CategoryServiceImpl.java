package elrejunte.demo.impl;

import elrejunte.demo.dto.CategoryDTO;
import elrejunte.demo.entity.Category;
import elrejunte.demo.exception.ResourceNotFoundException;
import elrejunte.demo.mapper.CategoryMapper;
import elrejunte.demo.repository.CategoryRepository;
import elrejunte.demo.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;


    public CategoryServiceImpl(CategoryRepository repo) { this.repo = repo; }


    @Override
    public List<CategoryDTO> findAll() {
        return repo.findAll().stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    public CategoryDTO findById(Long id) {
        Category c = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return CategoryMapper.toDTO(c);
    }


    @Override
    public CategoryDTO create(CategoryDTO dto) {
// evitar duplicados por nombre
        repo.findByNameIgnoreCase(dto.getName()).ifPresent(existing -> { throw new IllegalArgumentException("Category name already exists"); });
        Category c = CategoryMapper.toEntity(dto);
        Category saved = repo.save(c);
        return CategoryMapper.toDTO(saved);
    }


    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        return CategoryMapper.toDTO(repo.save(existing));
    }


    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Category not found: " + id);
        repo.deleteById(id);
    }
}
