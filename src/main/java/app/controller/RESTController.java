package app.controller;

import app.entity.Category;
import app.entity.Item;
import app.service.CategoryService;
import app.service.RestaurantService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import app.service.YelpAPIService;

import java.io.IOException;
import java.util.List;

@RestController
public class RESTController {
    @Autowired
    private YelpAPIService yelpService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search")
    @ResponseBody
    public List<Item> search(@RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "term", required = false) String term) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
                List<Item> restaurants = null;
        try {
            restaurants = yelpService.search(latitude, longitude, term);
            for (Item restaurant : restaurants) {
                // if it doesn't have categories, categories set would be null
                if (restaurant.getCatergories() != null) {
                    // 遍历每个种类，如果在种类表里没有的先加入种类表，再保存餐厅，这样hibernate会在保存餐厅的同时保存信息进关联表
                    for (Category category : restaurant.getCatergories()) {
                        if (!categoryService.exist(category.getName())) {
                            categoryService.saveCategory(category.getName());
                        }
                    }
                }
                restaurantService.saveRestaurant(restaurant);
            }
//            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(restaurants);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
