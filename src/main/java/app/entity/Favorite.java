package app.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Favorite implements Serializable {
    private String user_id;
    private String item_id;
    private String lastFavoriteTime;
    public Favorite() {
    }
    public Favorite(String user_id, String item_id, String lastFavoriteTime) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.lastFavoriteTime = lastFavoriteTime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getLastFavoriteTime() {
        return lastFavoriteTime;
    }

    public void setLastFavoriteTime(String lastFavoriteTime) {
        this.lastFavoriteTime = lastFavoriteTime;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "user_id='" + user_id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", lastFavoriteTime=" + lastFavoriteTime +
                '}';
    }
}
