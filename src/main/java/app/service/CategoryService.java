package app.service;

import app.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 根据id进行查找
     * @param id
     * @return
     */
    Category findById(Integer id);

    void saveCategory(Category category);

    List<Category> findByName(String name);

    List<Category> unionSelect(String itemId);

    List<Category> findAll();

    void saveRelationTable(Integer category_id, String item_id) ;
}
