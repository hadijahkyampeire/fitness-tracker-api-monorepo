package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class WorkoutCategoryController {

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    // Create category
    @PostMapping
    public ResponseEntity<WorkoutCategoryDTO> create(@RequestBody WorkoutCategoryDTO dto) {
        WorkoutCategory category = new WorkoutCategory();
        category.setName(dto.name());
        category.setDescription(dto.description());

        WorkoutCategory saved = categoryRepository.save(category);
        return ResponseEntity.ok(new WorkoutCategoryDTO(saved.getId(), saved.getName(), saved.getDescription()));
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<WorkoutCategoryDTO>> getAll() {
        List<WorkoutCategoryDTO> categories = categoryRepository.findAll().stream()
                .map(c -> new WorkoutCategoryDTO(c.getId(), c.getName(), c.getDescription()))
                .toList();
        return ResponseEntity.ok(categories);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(c -> new WorkoutCategoryDTO(c.getId(), c.getName(), c.getDescription()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update category
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> update(@PathVariable Long id, @RequestBody WorkoutCategoryDTO dto) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setDescription(dto.description());
                    WorkoutCategory updated = categoryRepository.save(existing);
                    return ResponseEntity.ok(new WorkoutCategoryDTO(updated.getId(), updated.getName(), updated.getDescription()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
