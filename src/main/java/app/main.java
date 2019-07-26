package app;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main( String args[]) {
        JSONArray array = new JSONArray();
        array.add("asdasd");
        array.add("1231223");
        String a = "------";
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.getString(i));
            a = a.concat(array.getString(i));
            if (i != array .size() - 1) {
                a = a.concat(",");
            }
        }
        System.out.println("a is " + a);
    }
}
