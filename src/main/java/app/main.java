package app;


import app.config.AppConfig;
import app.dao.CategoryDAO;
import app.dao.RestaurantDAO;
import app.entity.Category;
import app.entity.Item;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.apache.ibatis.io.Resources;


public class main {
    private InputStream in;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private CategoryDAO categoryDAO;
    private RestaurantDAO restaurantDAO;
    @Test
    public void testMybatis() {
        List<Category> list = categoryDAO.findByName("%tradamerican%");
        System.out.println(categoryDAO.findByName("%tradamerican%"));
        System.out.println(list.size());
    }
    @BeforeEach
    public void init() throws IOException {

            in = Resources.getResourceAsStream("SqlMapConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(in);
            session = sqlSessionFactory.openSession();
            categoryDAO = session.getMapper(CategoryDAO.class);
            restaurantDAO = session.getMapper(RestaurantDAO.class);
    }
    @AfterEach
    public void destory() throws IOException {
        session.commit();
        session.close();
        in.close();
    }
}
