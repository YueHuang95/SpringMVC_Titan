package app.dao;

import app.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements  CategoryDAO{
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean exist(String categoryName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Category> query = currentSession.createQuery("from Category c where c.name=:name", Category.class);
        query.setParameter("name", categoryName);
        List<Category> list = query.getResultList();
        if (list == null) {
            return false;
        }
        return true;
    }

    @Override
    public void saveCategory(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Category category = new Category(name);
        currentSession.save(category);
    }
}
