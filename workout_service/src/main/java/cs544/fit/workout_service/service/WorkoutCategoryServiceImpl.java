package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.entity.WorkoutPlan;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutCategoryServiceImpl implements WorkoutCategoryService {

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    @Override
    public WorkoutCategoryDTO createCategory(WorkoutCategoryDTO dto) {
        WorkoutCategory category = new WorkoutCategory();
        category.setName(dto.name());
        category.setDescription(dto.description());
        WorkoutCategory saved = categoryRepository.save(category);
        return toDTO(saved);
    }

    @Override
    public List<WorkoutCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public WorkoutCategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    @Override
    public WorkoutCategoryDTO updateCategory(Long id, WorkoutCategoryDTO dto) {
        WorkoutCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

        category.setName(dto.name());
        category.setDescription(dto.description());

        WorkoutCategory updated = categoryRepository.save(category);
        return toDTO(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private WorkoutCategoryDTO toDTO(WorkoutCategory category) {
        return new WorkoutCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription());
    }
}
