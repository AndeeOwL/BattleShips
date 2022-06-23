package bg.softuni.battleShips_ExamPrep.service;

import bg.softuni.battleShips_ExamPrep.model.entity.CategoryEntity;
import bg.softuni.battleShips_ExamPrep.model.entity.CategoryNameEnum;
import bg.softuni.battleShips_ExamPrep.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void initCategories() {
        if (categoryRepository.count() != 0) {
            return;
        }

        Arrays.stream(CategoryNameEnum.values())
                .forEach(categoryNameEnum -> {

                    CategoryEntity category = new CategoryEntity();
                    category.setName(categoryNameEnum);

                    categoryRepository.save(category);
                });
    }

    public CategoryEntity findByCategoryEnum(CategoryNameEnum category) {
        return categoryRepository.findByName(category)
                .orElse(null);
    }
}
