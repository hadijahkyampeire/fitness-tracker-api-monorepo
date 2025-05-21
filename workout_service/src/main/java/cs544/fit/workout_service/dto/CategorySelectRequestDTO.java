package cs544.fit.workout_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySelectRequestDTO {
    private List<Long> categoryIds;
}
