package app.service;

import app.entity.Item;

public interface RestaurantService {
    /*
        保存餐厅到数据库
     */
    public void saveRestaurant(Item restaurant);
}
