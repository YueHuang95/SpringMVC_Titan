package app.service;

import app.entity.Category;
import app.entity.Item;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class YelpAPIServiceImpl implements YelpAPIService {

    private final String HOST = "https://api.yelp.com";
    private final String ENDPOINT = "/v3/businesses/search";
    private final String DEFAULT_TERM = "";  //keyword to do specific search
    private final int SEARCH_LIMIT = 20;  // at most how many restaurants to be displayed
    //On yelp.com, it says we need to set Authorization HTTP header value as Bearer + API_KEY
    private final String TOKEN_TYPE = "Bearer";
    //We need to add authorized key in header of the request to Yelp
    private final String API_KEY = "843lMgKUEPG-ji03BNESPeY_qIFh65U0i9sGvxOhGSiuSgyLYpTJsgMgwRu0wPokbdDfvnfEhTTX5OK08ahVVixGIKOX_P-RdXt3def_O588F9YvsMW2GYIr1f2BW3Yx";
    private Logger logger;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;
    public YelpAPIServiceImpl() {
        this.logger = Logger.getLogger(getClass());
    }
    /**
     * 通过传入经纬度参数 调用YELP API接口查询餐厅信息并返回餐厅列表
     * @param lat 经度
     * @param lon 纬度
     * @param term 特殊搜索字符
     * @return
     */
    @Override
    public List<Item> search(double lat, double lon, String term) {
        //if we didnt specify search keyword then just  use default one
        if (term == null || term.isEmpty()) {
            term = DEFAULT_TERM;
        }
        try {
            //Space -> "+" and all characters other than digits, alphabet, ".""-""*""_""-"
            //are converted firstly into one or more bytes then each byte is converted into "%xy" where xy is two digit hexadecimal representation

            term = URLEncoder.encode(term, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 拼接整个URL String.format()就相当于C语言里的printf()
        String query = String.format("term=%s&latitude=%s&longitude=%s&limit=%s", term, lat, lon, SEARCH_LIMIT);
        String url = HOST + ENDPOINT + "?" + query;

        // 新建一个HTTP连接
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            System.out.println("URL:" + new URL(url).getFile());
            connection.setRequestMethod("GET"); //设置请求类型
            connection.setRequestProperty("Authorization", TOKEN_TYPE + " " + API_KEY); // 设置请求头信息
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println("response code is : " + responseCode);
            System.out.println("response message is " + connection.getResponseMessage());
            if (responseCode != 200) {
                return new ArrayList<>();
            }
            // 缓存读取器 比其他的高效因为减少了IO次数 8k默认值先读进来不用一次次开启再读取再转换
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            StringBuilder result = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                result.append(inputLine);
            }
            bufferedReader.close();
            // 调用YELP API返回的是一个JSON OBJECT包含三个属性 business, region, total
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            // business 如果为空意味着没数据
            if (jsonObject.getString("businesses") != null) {
                System.out.println("business JSON array is " + jsonObject.getJSONArray("businesses"));
                return getItemList(jsonObject.getJSONArray("businesses"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 遍历整个返回的餐厅数组，把每个餐厅加入一个集合中并返回
     * @param business
     * @return
     */
    private List<Item> getItemList(JSONArray business) {
        List<Item> restaurantList = new ArrayList<>();
        Item item;

        for (int i = 0; i < business.size(); i++) {
            JSONObject restaurant = (JSONObject) business.get(i);
            item = new Item();
            // containsKey 用的是Hashmap里的containsKey
            if (restaurant.containsKey("id")) {
                item.setItemId(restaurant.getString("id"));
            }
            if (restaurant.containsKey("name")) {
                item.setName(restaurant.getString("name"));
            }
            if (restaurant.containsKey("rating")) {
                item.setRating(restaurant.getDouble("rating"));
            }
            if (restaurant.containsKey("url")) {
                item.setUrl(restaurant.getString("url"));
            }
            if (restaurant.containsKey("image_url")) {
                item.setImageUrl(restaurant.getString("image_url"));
            }
            if (restaurant.containsKey("distance")) {
                item.setDistance(restaurant.getDouble("distance"));
            }
            item.setAddress(getAddress(restaurant));
            saveCategoryRestaurantRelation(item, restaurant);
            restaurantList.add(item);

        }
        return restaurantList;
    }

    /**
     * 根据传入的餐厅信息和其中包含的种类数组，如果数据库中没有餐厅，先存入餐厅信息，否则直接跳出方法；如果数据库中没有种类，先存入种类信息；再把数据存入关联表中
     * @param item
     * @param restaurant
     */
    private void saveCategoryRestaurantRelation(Item item, JSONObject restaurant) {
        // 先看数据库中有没有该餐厅信息
        if (restaurantService.getItemById(item.getItemId()) == null) {
            restaurantService.saveRestaurant(item);
        }
        String restaurantId = item.getItemId();
        if (!restaurant.containsKey("categories")) {
            item.setCategories(null);
            return;
        }
        JSONArray categories = restaurant.getJSONArray("categories");
        // 遍历整个数组并且把每个种类都新建一个种类实体类然后存入数据库，再把每个种类和餐厅的关联信息存入关联表
        List<Category> categoryList = new ArrayList<>();
        for(int i = 0; i < categories.size(); i++) {
            JSONObject category = categories.getJSONObject(i);
            if (category.containsKey("alias")) {
                // 先进数据库查看是否存在同名的种类
                Category cat = new Category(category.getString("alias"));
                // 只有当数据库里没有这个种类的时候，才把它添加进数据库
                if (categoryService.findByName("%" + cat.getName() + "%").size() == 0) {
                    categoryService.saveCategory(cat);
                } else {
                    cat.setCategory_id(categoryService.findByName("%" + cat.getName() + "%").get(0).getCategory_id());
                }
                // 把种类和餐厅的关联信息存入关联表
                try {
                    categoryService.saveRelationTable(cat.getCategory_id(), restaurantId);
                } catch (DataAccessException e) {
                    logger.error("JDBC错误: " + e);
                }
                categoryList.add(cat);
            }
        }
        item.setCategories(categoryList);
    }
    private String getAddress(JSONObject restaurant)  {
        String address = "";
        if (restaurant.containsKey("location")) {
            JSONObject location = restaurant.getJSONObject("location");
            if (location.containsKey("display_address")) {
                JSONArray array = location.getJSONArray("display_address");
                for (int i = 0; i < array.size(); i++) {
                    address = address.concat(array.getString(i));//concatenate strings by a commas between two strings
                    if (i != array.size() - 1) {
                        address = address.concat(", ");
                    }
                }
            }
        }
        return address;

    }

}
