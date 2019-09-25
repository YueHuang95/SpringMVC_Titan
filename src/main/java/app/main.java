package app;


import app.config.AppConfig;
import app.dao.CategoryDAO;
import app.dao.FavoriteDAO;
import app.dao.Item_Category_RelationDAO;
import app.dao.RestaurantDAO;
import app.entity.Category;
import app.entity.Favorite;
import app.entity.Item;
import app.entity.Item_Category_Relation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.ibatis.io.Resources;


public class main {
    private InputStream in;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private Item_Category_RelationDAO item_category_relationDAO;
    @Test
    public void testMybatis() {
        String[] array = "httlkapsodiw;lek;qlkasd".split(",");
        for (String s : array) {
            System.out.println(s);
        }
        System.out.println(array.toString());

    }
    @BeforeEach
    public void init() throws IOException {

            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(in);
            session = sqlSessionFactory.openSession();
            item_category_relationDAO = session.getMapper(Item_Category_RelationDAO.class);
    }
    @AfterEach
    public void destory() throws IOException {
        session.commit();
        session.close();
        in.close();
    }
}
