package app.service;

import app.entity.Item;

import java.io.IOException;
import java.util.List;

public interface YelpAPIService {
    public  List<Item> search(double lat, double lon, String term) throws IOException;
}
