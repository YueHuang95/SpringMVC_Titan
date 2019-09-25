package app.service;

import app.entity.Item;

import java.io.IOException;
import java.util.List;

public interface YelpAPIService {
    List<Item> search(double lat, double lon, String term);
    List<Item> search(double lat, double lon, String term, int limit) throws IOException;
}
