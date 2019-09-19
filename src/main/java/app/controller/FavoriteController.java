package app.controller;

import app.entity.Favorite;
import app.entity.Item;
import app.entity.pojo.Data;

import app.service.FavoriteService;
import app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    RestaurantService restaurantService;
    @PostMapping("/history")
    public HashMap<String, String> setFavoriteItems(@RequestBody Data request) {
        String userid = request.getUser_id();
        // 拿出当前userid的数据库里所有的喜好餐厅数据
        List<Favorite> list = favoriteService.findByUserId(userid);
        // 单独把喜好餐厅的id拿出来
        List<String> favoriteItems = new ArrayList<>();
        for (Favorite favorite : list) {
            favoriteItems.add(favorite.getItem_id());
       }
        // 遍历一遍传进来的请求的喜好餐厅id，如果id已经在数据库里就不进行操作，如果不在就存入
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String itemid : request.getFavorite()) {
            if (!favoriteItems.contains(itemid)) {
                String date = simpleDateFormat.format(new Date());
                favoriteService.saveFavoriteRestaurant(new Favorite(userid, itemid, date));
            }
        }
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "SUCCESS");
        return resultMap;
    }
    @DeleteMapping("/history")
    public HashMap<String, String> deleteFavoriteItems(@RequestBody Data request) {
        List<String> itemIds = request.getFavorite();
        for (String itemId : itemIds) {
            favoriteService.deleteOne(itemId);
        }
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "SUCCESS");
        return resultMap;
    }
    @GetMapping("/history")
    public List<Item> getFavoriteItems(@RequestParam(name = "user_id") String userid) {
        List<Favorite> list = favoriteService.findByUserId(userid);
        List<String> itemIds = new ArrayList<>();
        for (Favorite favorite : list) {
            itemIds.add(favorite.getItem_id());
        }
        List<Item> items = restaurantService.getItemByIds(itemIds);
        for (Item item : items) {
            item.setFavorite(true);
        }
        return items;
    }
}
