package app.entity.pojo;

import java.util.List;

public class Data {
    private String user_id;
    private List<String> favorite;
    public Data(){
    }
    public Data(String user_id, List<String> favorite) {
        this.user_id = user_id;
        this.favorite = favorite;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Data{" +
                "user_id='" + user_id + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
