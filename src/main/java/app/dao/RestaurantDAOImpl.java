//package app.dao;
//
//import app.entity.Category;
//import app.entity.Item;
//import app.service.CategoryService;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//@Repository
//public class RestaurantDAOImpl implements  RestaurantDAO {
//    // need to inject the session factory
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Override
//    @Transactional
//    public void saveRestaurant(Item restaurant) {
//        Session currentSession = sessionFactory.getCurrentSession();
//        // 遍历每个种类，如果在种类表里没有的先加入种类表，再保存餐厅，这样hibernate会在保存餐厅的同时保存信息进关联表
////        for (Category category : restaurant.getCategories()) {
////            if (!categoryService.exist(category)) {
////                currentSession.persist(category);
////            }
////        }
//        currentSession.save(restaurant);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Item getItemById(String itemId) {
//         Session session = sessionFactory.getCurrentSession();
//         Item item = session.get(Item.class, itemId);
//         return item;
//    }
//}
