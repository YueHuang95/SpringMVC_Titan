package app.service;

import app.dao.RestaurantDAO;
import app.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantDAO restaurantDAO;
    @Override
    public void saveRestaurant(Item restaurant) {
        restaurantDAO.saveRestaurant(restaurant);
    }
}

