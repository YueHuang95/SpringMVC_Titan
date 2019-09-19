package app.dao;

import app.entity.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteDAO {
    @Select("select f.user_id, f.item_id,  DATE_FORMAT(f.last_favor_time, '%Y-%m-%d %T') AS lastFavoriteTime from favorite f where user_id = #{userId}")
    List<Favorite> findByUserId(String userId);

    @Delete("delete from favorite where item_id = #{itemId}")
    void deleteOne(String itemId);

    @Insert("insert into favorite values (#{user_id}, #{item_id}, #{lastFavoriteTime})")
    void saveFavoriteRestaurant(Favorite favorite);
}
