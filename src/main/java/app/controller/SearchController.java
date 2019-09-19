package app.controller;

import app.entity.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import app.service.YelpAPIService;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private YelpAPIService yelpService;

    @GetMapping("/search")
    @ResponseBody
    public List<Item> search(@RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam(value = "user_id", required = false) String userId, @RequestParam(value = "term", required = false) String term) throws IOException {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
                List<Item> restaurants = null;
        restaurants = yelpService.search(latitude, longitude, term);

        return restaurants.size() == 0 ? yelpService.search(32.71, -117.16, term) : restaurants;
    }

}
