package app.service;

import app.entity.Category;
import app.entity.Item;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
     * 通过传入经纬度参数 调用YELP API接口查询餐厅信息并返回餐厅列表
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
    /*
     * 遍历整个返回的餐厅数组，把每个餐厅加入一个集合中并返回
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
            getCategories(item, restaurant);
            item.setAddress(getAddress(restaurant));
            restaurantList.add(item);
        }

        return restaurantList;
    }
    /*
     *  getItemList 里所需要用到的方法，根据传入的餐厅和实体类的引用
     *  如果查询到没有种类这个数组的话就把实体类里的categories设为空值
     *  如果查询到有种类数组就遍历数组然后把每一个种类添加进实体类item的categories这个Set里
     */
    private void getCategories(Item item, JSONObject restaurant) {
        if (!restaurant.containsKey("categories")) {
            item.setCatergories(null);
            return;
        }
        JSONArray categories = restaurant.getJSONArray("categories");
        // 遍历整个数组并且把每个种类都新建一个种类实体类然后加入到item里
        for(int i = 0; i < categories.size(); i++) {
            JSONObject category = categories.getJSONObject(i);
            if (category.containsKey("alias")) {
                Category cat = new Category(category.getString("alias"));
                item.addCategory(cat);
            }
        }
    }
    private String getAddress(JSONObject restaurant)  {
        String address = "";
        if (!restaurant.containsKey("location")) {
            JSONObject location = restaurant.getJSONObject("location");
            if (!location.containsKey("display_address")) {
                JSONArray array = location.getJSONArray("display_address");
                for (int i = 0; i < array.size(); i++) {
                    address = address.concat(array.getString(i));//concatenate strings by a commas between two strings
                    if (i != array.size() - 1) {
                        address = address.concat(",");
                    }
                }
            }
        }
        return address;

    }

}
