package app.dao;

import app.entity.Item;

public interface RestaurantDAO {
    /*
        保存餐厅到数据库
     */
    public void saveRestaurant(Item restaurant);
}
