package app.service;

import app.entity.Item;

import java.util.List;

public interface RecommendationService {
    List<Item> recommendItems(String userId, Double lat, Double lon);
}
