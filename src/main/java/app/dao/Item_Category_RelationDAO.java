package app.dao;

import app.entity.Item_Category_Relation;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Item_Category_RelationDAO {
    @Select("select * from item_category_relation where category_id = #{category_id}")
    List<Item_Category_Relation> findByCategoryId(Integer category_id);

    @Select("select * from item_category_relation where item_id = #{item_id}")
    List<Item_Category_Relation> findByItemId(String item_id);
}
