package visloukh.libra.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import visloukh.libra.Entity.BookEntity;
import visloukh.libra.Entity.CategoryEntity;
import visloukh.libra.Repository.CategoryRepository;
import visloukh.libra.Service.CategoryService;
import visloukh.libra.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryEntity categoryEntity) {

        categoryEntity.setStatus("created");
        categoryEntity.setCreateDate(new Date());
        categoryRepository.save(categoryEntity);
        //System.out.println("Saved to base: "+ categoryEntity.toString());
    }
    @Override
    public void createDefaultCategorys() {
        CategoryEntity mythstick = new CategoryEntity();
        CategoryEntity fantasy = new CategoryEntity();
        CategoryEntity fantastic = new CategoryEntity();
        CategoryEntity roman = new CategoryEntity();
        CategoryEntity litRPG = new CategoryEntity();
        mythstick.setName("mythstick");
        mythstick.setDescription("some description to mythstick");
        fantasy.setName("fantasy");
        fantasy.setDescription("some description to fantasy");
        fantastic.setName("fantastic");
        fantastic.setDescription("some description to fantastic");
        roman.setName("roman");
        roman.setDescription("some description to roman");
        litRPG.setName("litRPG");
        litRPG.setDescription("some description to  litRPG");
        createCategory(mythstick);
        createCategory(fantasy);
        createCategory(fantastic);
        createCategory(roman);
        createCategory(litRPG);

    }

    @Override
    public boolean existCategory(Integer id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean existCategory(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }

    @Override
    public CategoryEntity findCategoryById(Integer id) {
        if (existCategory(id)) {
            return categoryRepository.findById(id).get();
        } else throw new NotFoundException("category with id="+ id+ " not found");

    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }
}
