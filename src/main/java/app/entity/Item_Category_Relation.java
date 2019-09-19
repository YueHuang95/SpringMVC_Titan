package app.entity;

import java.io.Serializable;

public class Item_Category_Relation implements Serializable {
    private Integer category_id;
    private String item_id;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "Item_Category_Relation{" +
                "category_id=" + category_id +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
