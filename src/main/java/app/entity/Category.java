package app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category implements Serializable {
    private int category_id;

    private String name;

    private List<Item> items;

    public Category() {}
    public Category(String name) {
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

//    public void addItem(Item item) {
//        if (items == null) {
//            items = new ArrayList<>();
//        }
//        items.add(item);
//    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
