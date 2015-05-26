package com.qinyuan15.mall.core.category;

import com.qinyuan15.mall.core.dao.CategoryPoster;
import com.qinyuan15.mall.core.image.PictureUrlConverter;

import java.util.List;

/**
 * Adjust url of category poster
 * Created by qinyuan on 15-2-25.
 */
public class CategoryPosterUrlAdapter {
    private PictureUrlConverter pictureUrlConverter;

    public CategoryPosterUrlAdapter(PictureUrlConverter pictureUrlConverter) {
        this.pictureUrlConverter = pictureUrlConverter;
    }

    public List<CategoryPoster> adjust(List<CategoryPoster> categoryPosters) {
        for (CategoryPoster poster : categoryPosters) {
            adjust(poster);
        }
        return categoryPosters;
    }

    public CategoryPoster adjust(CategoryPoster categoryPoster) {
        categoryPoster.setPath(this.pictureUrlConverter.pathToUrl(categoryPoster.getPath()));
        return categoryPoster;
    }
}
