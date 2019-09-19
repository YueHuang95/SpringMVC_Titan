package app.service;

import app.dao.RestaurantDAO;
import app.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    public void saveRestaurant(Item restaurant) {
        restaurantDAO.saveRestaurant(restaurant);
    }

    @Override
    public Item getItemById(String itemId) {
        return restaurantDAO.getItemById(itemId);
    }

    @Override
    public List<Item> unionSelect(Integer categoryId) {
        return restaurantDAO.unionSelect(categoryId);
    }

    @Override
    public List<Item> getItemByIds(List<String> itemIds) {
        return  restaurantDAO.getItemByIds(itemIds);
    }

    @Override
    public List<Item> getItems() {
        return restaurantDAO.getItems();
    }
}

