package com.cacsire.bibliotecanet.repository;

import com.cacsire.bibliotecanet.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findByName(String nameCategory);
    CategoryEntity findByNameAndIdNot (String nameCategory, Long idCategory);
}
