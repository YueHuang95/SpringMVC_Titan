package app.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="items")
public class Item {
    @Id
    @Column(name="item_id")
    private String itemId;

    @Column(name="name")
    private String name;

    @Column(name="rating")
    private double rating;

    @Column(name="address")
    private String address;

    @Column(name="url")
    private String url;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="distance")
    private double distance;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "item_category_relation", joinColumns =
                @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ElementCollection(targetClass=Category.class)
    private List<Category> catergories;

    public Item(){}
    public Item(String itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Category> getCatergories() {
        return catergories;
    }

    public void setCatergories(List<Category> catergories) {
        this.catergories = catergories;
    }
    public void addCategory(Category category) {
        if (this.catergories == null) {
            this.catergories = new ArrayList<>();
        }
        this.catergories.add(category);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", distance=" + distance +
                ", catergories=" + catergories +
                '}';
    }
}
