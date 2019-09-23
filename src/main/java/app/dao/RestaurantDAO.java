package app.dao;

import app.entity.Category;
import app.entity.Item;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RestaurantDAO {
    /**
        保存餐厅到数据库
     */
    @Insert("insert into items values (#{itemId}, #{name}, #{rating}, #{address}, #{url}, #{imageUrl}, #{distance})")
    void saveRestaurant(Item restaurant);

    /**
     *
     * @return
     */
    @Select("select * from items")
    @Results(value = {
            @Result(id = true, column = "item_id", property = "itemId"),
            @Result(column = "image_url", property = "imageUrl"),
            @Result(property = "categories", column = "item_id", javaType = List.class,
                    many = @Many(select = "app.dao.CategoryDAO.unionSelect", fetchType = FetchType.LAZY))
    })
    List<Item> getItems();

    /**
     * 根据id查询餐厅信息,
     * @param itemId
     * @return
     */
    @Select(value="select * from items where item_id = #{itemid}")
    @Results(value = {
            @Result(id = true, column = "item_id", property = "itemId"),
            @Result(column = "image_url", property = "imageUrl"),
            @Result(property = "categories", column = "itemId", javaType = List.class,
                    many = @Many(select = "app.dao.CategoryDAO.unionSelect", fetchType = FetchType.LAZY))
    })
    Item getItemById(String itemId);

    /**
     * 用于CategoryDAO里的三表联合查询
     * @param categoryId
     * @return
     */
    @Select("select i.* from items i, (select item_id from item_category_relation icr where icr.category_id = #{categoryId}) a where i.item_id = a.item_id")
    List<Item> unionSelect(Integer categoryId);

    @Select("<script>select * from items where item_id in <foreach collection = 'list' item = 'listId' open = '(' separator = ',' close = ')'> #{listId} </foreach> </script>")
    @Results(value = {
            @Result(id = true, column = "item_id", property = "itemId"),
            @Result(column = "image_url", property = "imageUrl"),
            @Result(property = "categories", column = "item_id", javaType = List.class,
                    many = @Many(select = "app.dao.CategoryDAO.unionSelect", fetchType = FetchType.LAZY))
    })
    List<Item> getItemByIds(List<String> itemIds);
}
