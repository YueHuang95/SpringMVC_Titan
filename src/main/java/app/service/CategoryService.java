package app.service;

public interface CategoryService {
    /*
        判断某个种类名是否存在于数据库中
     */
    boolean exist(String categoryName);
    /*
        保存种类到数据库，传入种类名参数
     */
    void saveCategory(String name);
}
