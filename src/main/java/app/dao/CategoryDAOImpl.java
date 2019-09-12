//package app.dao;
//
//import app.entity.Category;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//public class CategoryDAOImpl implements  CategoryDAO{
//    @Autowired
//    private SessionFactory sessionFactory;
//    @Override
//    public boolean exist(Category category) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        Query<Category> query = currentSession.createQuery("from Category c where c.name=:name", Category.class);
//        query.setParameter("name", category.getName());
//        List<Category> list = query.getResultList();
//        currentSession.close();
//        if (list == null) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    @Transactional
//    public void saveCategory(Category category) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        currentSession.save(category);
//        currentSession.getTransaction().commit();
//        currentSession.close();
//    }
//
//    @Override
//    public void persistCategory(Category category) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        currentSession.persist(category);
//        currentSession.close();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Category getCategory(String name) {
//        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery("select from Category c where name = :categoryName");
//        query.setParameter("categoryName", name);
//        List<Category> categoryList = query.getResultList();
//        session.close();
//        return categoryList.get(0);
//    }
//
//}
