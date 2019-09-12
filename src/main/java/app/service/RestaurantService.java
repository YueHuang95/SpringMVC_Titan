package app.service;

import app.entity.Item;

import java.util.List;

public interface RestaurantService {
    /**
     保存餐厅到数据库
     */
    void saveRestaurant(Item restaurant);

    /**
     *
     * @return
     */
    List<Item> getItems();

    /**
     * 根据id查询餐厅信息,
     * @param itemId
     * @return
     */
    Item getItemById(String itemId);

    /**
     * 用于CategoryDAO里的三表联合查询
     * @param categoryId
     * @return
     */
    List<Item> unionSelect(Integer categoryId);
}
