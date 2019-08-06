package app.dao;

import app.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RestaurantDAOImpl implements  RestaurantDAO {
    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveRestaurant(Item restaurant) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(restaurant);
    }
}
