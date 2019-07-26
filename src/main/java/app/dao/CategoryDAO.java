package app.dao;

public interface CategoryDAO {
    /*
        判断某个种类名是否存在于数据库中
     */
    public boolean exist(String categoryName);
    /*
       保存种类到数据库，传入种类名参数
    */
    public void saveCategory(String name);
}
