package bg.softuni.battleShips_ExamPrep.repository;

import bg.softuni.battleShips_ExamPrep.model.entity.CategoryEntity;
import bg.softuni.battleShips_ExamPrep.model.entity.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByName(CategoryNameEnum category);
}
