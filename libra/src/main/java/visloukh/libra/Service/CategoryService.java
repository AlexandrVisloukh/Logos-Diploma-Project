package visloukh.libra.Service;

import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    boolean existCategory (Integer id);
    boolean existCategory (String categoryName);

    void createCategory (CategoryEntity categoryEntity);

    void createDefaultCategorys();

    CategoryEntity findCategoryById(Integer id);

    List<CategoryEntity> getAllCategory();
}
