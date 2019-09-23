package app.controller;

import app.entity.Item;
import app.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;
    @GetMapping("/recommendation")
    public List<Item> recommendation(@RequestParam("user_id") String userId, @RequestParam("lat") String lat, @RequestParam("lon") String lon) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        return recommendationService.recommendItems(userId, latitude, longitude);
    }
}
