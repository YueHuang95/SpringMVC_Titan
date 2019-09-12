package app.service;

import app.entity.Category;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
