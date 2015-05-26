package com.qinyuan15.mall.core.image;

import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.image.ThumbnailSuffix;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to validate redundancy of images by database
 * Created by qinyuan on 15-3-18.
 */
public class DatabaseRedundantImageValidator implements RedundantImageValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseRedundantImageValidator.class);
    private List<ImageColumn> columns;
    private List<String> thumbnailSuffixes = ThumbnailSuffix.getSuffixes();

    public void setColumns(List<String> tableColumns) {
        this.columns = new ArrayList<>();
        for (String tableColumn : tableColumns) {
            String[] tableColumnArray = tableColumn.split("\\.");
            if (tableColumnArray.length >= 2) {
                ImageColumn imageColumn = new ImageColumn(tableColumnArray[0], tableColumnArray[1]);
                this.columns.add(imageColumn);
                LOGGER.info("Add {} as validation column.", tableColumn);
            }
        }
    }

    @Override
    public boolean isRedundant(String imagePath) {
        if (!StringUtils.hasText(imagePath)) {
            return false;
        }

        if (this.columns == null || this.columns.size() == 0) {
            return false;
        }

        if (this.thumbnailSuffixes != null) {
            for (String thumbnailSuffix : this.thumbnailSuffixes) {
                imagePath = imagePath.replace(thumbnailSuffix, "");
            }
        }

        for (ImageColumn column : this.columns) {
            long count = HibernateUtils.getCount(column.table,
                    column.column + " LIKE '%" + StringEscapeUtils.escapeSql(imagePath) + "%'");
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    private static class ImageColumn {
        String table;
        String column;

        ImageColumn(String table, String column) {
            this.table = table;
            this.column = column;
        }
    }
}
