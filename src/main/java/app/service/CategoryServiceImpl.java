package app.service;

        import app.dao.CategoryDAO;
        import app.entity.Category;
        import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public Category findById(Integer id) {
        return categoryDAO.findById(id);
    }

    @Override
    public void saveCategory(Category category) {
        categoryDAO.saveCategory(category);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public List<Category> unionSelect(String itemId) {
        return categoryDAO.unionSelect(itemId);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public void saveRelationTable(Integer category_id, String item_id)  {
        categoryDAO.saveRelationTable(category_id, item_id);
    }

}
