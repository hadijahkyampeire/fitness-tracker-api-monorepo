package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;

import java.util.List;

public interface WorkoutCategoryService {
    WorkoutCategoryDTO createCategory(WorkoutCategoryDTO dto);
    List<WorkoutCategoryDTO> getAllCategories();
    WorkoutCategoryDTO getCategoryById(Long id);
    WorkoutCategoryDTO updateCategory(Long id, WorkoutCategoryDTO dto);
    void deleteCategory(Long id);
}
