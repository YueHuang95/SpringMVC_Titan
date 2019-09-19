package app.service;

import app.entity.Favorite;


import java.util.List;

public interface FavoriteService {
    List<Favorite> findByUserId(String userId);

    void deleteOne(String itemId);

    void saveFavoriteRestaurant(Favorite favorite);
}
