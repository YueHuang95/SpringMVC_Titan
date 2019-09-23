package app.service;

import app.dao.FavoriteDAO;
import app.dao.RestaurantDAO;
import app.entity.Category;
import app.entity.Favorite;
import app.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    FavoriteDAO favoriteDAO;
    @Autowired
    RestaurantDAO restaurantDAO;
    @Autowired
    YelpAPIService yelpAPIService;

    @Override
    public List<Item> recommendItems(String userId, Double lat, Double lon) {
        // 创建最终要返回的数组
        List<Item> resultList = new ArrayList<>();
        // 得到该用户的喜好餐厅item数组
        List<Favorite> favoriteList = favoriteDAO.findByUserId(userId);
        // 设置一个用来判断这个餐厅是否已经给访问过,用于后面从Yelp网站搜索过滤
        Set<Item> visitedItems = new HashSet<>();
        // 遍历拿到每个餐厅id 用餐厅id通过关联表关联到种类表去拿该餐厅对应的种类名
        HashMap<String, Integer> categoryMap = new HashMap<>();
        for (Favorite fav : favoriteList) {
            Item item = restaurantDAO.getItemById(fav.getItem_id());
            visitedItems.add(item);
            for (Category category : item.getCategories()) {
                categoryMap.put(category.getName(), categoryMap.getOrDefault(category.getName(), 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> categoryList = new ArrayList<>(categoryMap.entrySet());
        Collections.sort(categoryList, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
            return Integer.compare(o2.getValue(), o1.getValue());
        });
        // 根据每个种类名来调用Yelp搜索餐厅，遍历每个餐厅来加入到最终的集合，如果是已经喜好过的或者搜索到的就过滤掉
        for (Map.Entry<String, Integer> entry : categoryList) {
            try {
                List<Item> items = yelpAPIService.search(lat, lon, entry.getKey());
                List<Item> filteredItems = new ArrayList<>();
                for (Item item : items) {
                    if (!visitedItems.contains(item)) {
                        filteredItems.add(item);
                    }
                }
                visitedItems.addAll(filteredItems);
                resultList.addAll(filteredItems);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
