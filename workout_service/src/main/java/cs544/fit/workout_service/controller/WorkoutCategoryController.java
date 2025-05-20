package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class WorkoutCategoryController {

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    // ACCESSED ONLY BY ADMIN AND COACH
    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkoutCategoryDTO dto) {
        try {
            WorkoutCategory category = new WorkoutCategory();
            category.setName(dto.name());
            category.setDescription(dto.description());
            WorkoutCategory saved = categoryRepository.save(category);
            return ResponseEntity.ok(new WorkoutCategoryDTO(saved.getId(), saved.getName(), saved.getDescription()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Category name already exists. Please choose a different name.");
        }
    }

    // ACCESSED ONLY BY ADMIN, COACH AND USER
    @GetMapping
    public ResponseEntity<List<WorkoutCategoryDTO>> getAll() {
        List<WorkoutCategoryDTO> categories = categoryRepository.findAll().stream()
                .map(c -> new WorkoutCategoryDTO(c.getId(), c.getName(), c.getDescription()))
                .toList();
        return ResponseEntity.ok(categories);
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> getById(@PathVariable("id") Long id) {
        return categoryRepository.findById(id)
                .map(c -> new WorkoutCategoryDTO(c.getId(), c.getName(), c.getDescription()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ACCESSED ONLY BY ADMIN AND COACH
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> update(@PathVariable("id") Long id, @RequestBody WorkoutCategoryDTO dto) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setDescription(dto.description());
                    WorkoutCategory updated = categoryRepository.save(existing);
                    return ResponseEntity.ok(new WorkoutCategoryDTO(updated.getId(), updated.getName(), updated.getDescription()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ACCESSED ONLY BY ADMIN AND COACH
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
