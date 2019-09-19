package app.service;

import app.dao.FavoriteDAO;
import app.entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteDAO favoriteDAO;
    @Override
    public List<Favorite> findByUserId(String userId) {
        return favoriteDAO.findByUserId(userId);
    }

    @Override
    public void deleteOne(String itemId) {
        favoriteDAO.deleteOne(itemId);
    }

    @Override
    public void saveFavoriteRestaurant(Favorite favorite) {
        favoriteDAO.saveFavoriteRestaurant(favorite);
    }
}
