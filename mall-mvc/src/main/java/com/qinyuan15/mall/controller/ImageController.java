package com.qinyuan15.mall.controller;

import com.qinyuan15.mall.core.branch.BranchGroup;
import com.qinyuan15.mall.core.branch.BranchUrlAdapter;
import com.qinyuan15.mall.core.category.CategoryPosterUrlAdapter;
import com.qinyuan15.mall.core.commodity.CommodityBranchGroup;
import com.qinyuan15.mall.core.commodity.CommodityPictureUtils;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.CategoryPoster;
import com.qinyuan15.mall.core.dao.CommodityPicture;
import com.qinyuan15.mall.core.dao.IndexLogo;
import com.qinyuan15.mall.core.image.PictureUrlValidator;
import com.qinyuan15.mall.core.index.IndexLogoUrlAdapter;
import com.qinyuan15.utils.image.ImageDownloader;
import com.qinyuan15.utils.image.ImageFilter;
import com.qinyuan15.utils.image.ImageSize;
import com.qinyuan15.utils.image.ThumbnailsBuilder;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller that need to deal with image/picture
 * Created by qinyuan on 15-3-3.
 */
@Component
public class ImageController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    protected ImageDownloader imageDownloader;

    private ImageFilter commodityPictureFilter = new ImageFilter().setFilterSize(ImageSize.VERY_SMALL);

    private List<String> getCommodityPictureUrls(List<CommodityPicture> commodityPictures) {
        List<String> paths = CommodityPictureUtils.getUrls(commodityPictures);
        return commodityPictureFilter.filterSize(paths);
    }

    protected List<String> parseCommodityPictureUrls(List<CommodityPicture> commodityPictures) {
        return pictureUrlConverter.pathsToUrls(getCommodityPictureUrls(commodityPictures));
    }

    protected List<String> parseCommodityPictureMiddleUrls(List<CommodityPicture> commodityPictures) {
        List<String> paths = getCommodityPictureUrls(commodityPictures);
        paths = new ThumbnailsBuilder().getMiddle(paths);
        return pictureUrlConverter.pathsToUrls(paths);
    }

    protected List<String> parseCommodityPictureSmallUrls(List<CommodityPicture> commodityPictures) {
        List<String> paths = getCommodityPictureUrls(commodityPictures);
        paths = new ThumbnailsBuilder().getSmall(paths);
        return pictureUrlConverter.pathsToUrls(paths);
    }

    protected BranchUrlAdapter getBranchUrlAdapter() {
        return new BranchUrlAdapter(pictureUrlConverter);
    }

    protected List<IndexLogo> adjustIndexLogos(List<IndexLogo> indexLogos) {
        return new IndexLogoUrlAdapter(pictureUrlConverter).adjust(indexLogos);
    }

    protected List<CategoryPoster> adjustCategoryPosters(List<CategoryPoster> categoryPosters) {
        return new CategoryPosterUrlAdapter(pictureUrlConverter).adjust(categoryPosters);
    }

    protected List<CommodityBranchGroup> adjustCommodityBranchGroups(List<CommodityBranchGroup> commodityBranchGroups) {
        return getBranchUrlAdapter().adjustCommodityBranchGroups(commodityBranchGroups);
    }

    protected List<BranchGroup> adjustBranchGroups(List<BranchGroup> branchGroups) {
        return getBranchUrlAdapter().adjustBranchGroups(branchGroups);
    }

    protected List<Branch> adjustBranches(List<Branch> branches) {
        return getBranchUrlAdapter().adjustBranches(branches);
    }

    protected Branch adjustBranch(Branch branch) {
        return getBranchUrlAdapter().adjustBranch(branch);
    }

    /**
     * web frontend may post a image url or file, this method validate
     * the  url and upload file and return a local path
     *
     * @param imageUrl       image url posted
     * @param imageFile      image file uploaded
     * @param savePathPrefix save path prefix
     * @return save path
     * @throws IOException
     */
    protected String getSavePath(String imageUrl, MultipartFile imageFile, String savePathPrefix) throws IOException {
        if (validateUploadFile(imageFile)) {
            if (!savePathPrefix.endsWith("/")) {
                savePathPrefix = savePathPrefix + "/";
            }
            String relativePath = savePathPrefix + RandomStringUtils.randomAlphabetic(20)
                    + "_" + imageFile.getOriginalFilename();
            String filePath = imageDownloader.getSaveDir() + "/" + relativePath;
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                LOGGER.error("fail to create directory {}", parent.getAbsolutePath());
            }
            imageFile.transferTo(file);
            LOGGER.info("save upload image to {}", filePath);
            return filePath;
        } else {
            if (new PictureUrlValidator(getLocalAddress()).isLocal(imageUrl)) {
                return pictureUrlConverter.urlToPath(imageUrl);
            } else {
                String filePath = imageDownloader.save(imageUrl);
                LOGGER.info("save upload image to {}", filePath);
                return filePath;
            }
        }
    }

    protected boolean validateUploadFile(MultipartFile file) {
        return file != null && StringUtils.hasText(file.getOriginalFilename())
                && file.getSize() > 0;
    }
}
