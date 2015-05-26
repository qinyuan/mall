package com.qinyuan15.mall.core.category;

import com.qinyuan15.mall.core.dao.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to wrapper category and other relative information together
 * Created by qinyuan on 15-3-21.
 */
public class RichCategory {
    private Category category;
    private List<HotSearchWord> hotSearchWords;
    private List<Branch> branches;
    private List<CategoryPoster> posters;
    private Integer categoryLevel;

    public void setHotSearchWords(List<HotSearchWord> hotSearchWords) {
        this.hotSearchWords = hotSearchWords;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public void setPosters(List<CategoryPoster> posters) {
        this.posters = posters;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public List<CategoryPoster> getPosters() {
        return posters;
    }

    public List<HotSearchWord> getHotSearchWords() {
        return hotSearchWords;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public static List<RichCategory> getInstances() {
        List<RichCategory> richCategories = new ArrayList<>();

        CategoryDao categoryDao = new CategoryDao();
        List<Category> rootCategories = categoryDao.getRootInstances();
        for (Category category : rootCategories) {
            richCategories.add(getInstance(category, 0));
            for (Category subCategory : categoryDao.getSubInstances(category.getId())) {
                richCategories.add(getInstance(subCategory, 1));
            }
        }

        return richCategories;
    }

    private static RichCategory getInstance(Category category, int categoryLevel) {
        RichCategory richCategory = new RichCategory();

        richCategory.setCategory(category);
        richCategory.setCategoryLevel(categoryLevel);
        richCategory.setHotSearchWords(new HotSearchWordDao().getInstances(category.getId()));
        richCategory.setBranches(new BranchDao().getInstancesByCategoryId(category.getId()));
        richCategory.setPosters(new CategoryPosterDao().getInstances(category.getId()));

        return richCategory;
    }
}
