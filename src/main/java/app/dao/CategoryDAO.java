package app.dao;

import app.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDAO {
    /**
        根据id查询种类
     */
    @Select("select * from categories where category_id = #{id}")
    Category findById(Integer id);

    @Select("select * from categories where name like #{name}")
    List<Category> findByName(String name);


    /**
     * 用于RestaurantDAO里的联合查询
     * @param itemId
     * @return
     */
    @Select("select a.category_id, c.name from categories c, (select category_id from item_category_relation p where p.item_id = #{itemId}) a where c.category_id = a.category_id")
    List<Category> unionSelect(String itemId);
    /**
     * 保存种类
     * @param category
     */
    @Insert("insert into categories values (#{category_id}, #{name})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "category_id", keyColumn = "category_id", before = false, resultType = java.lang.Integer.class)
    void saveCategory(Category category);

    /**
     * 查询全部种类
     * @return
     */
    @Select("select * from categories")
    @Results(value = {
            @Result(property = "items", column = "category_id", javaType = List.class,
                    many = @Many(select = "app.dao.RestaurantDAO.unionSelect", fetchType = FetchType.LAZY))
    })
    List<Category> findAll();

    /**
     * 讲餐厅id和种类id保存进关联表
     * @param category_id
     * @param item_id
     */
    @Insert("insert into item_category_relation (category_id, item_id) values (#{catId}, #{itemId})")
    void saveRelationTable(@Param("catId") Integer category_id, @Param("itemId") String item_id) ;
}
