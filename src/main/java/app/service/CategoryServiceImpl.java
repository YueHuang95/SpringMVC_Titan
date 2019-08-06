package app.service;

import app.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements  CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public boolean exist(String categoryName) {
        return categoryDAO.exist(categoryName);
    }

    @Override
    public void saveCategory(String name) {
        categoryDAO.saveCategory(name);
    }
}
