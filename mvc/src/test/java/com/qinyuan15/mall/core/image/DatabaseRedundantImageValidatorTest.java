package com.qinyuan15.mall.core.image;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

/**
 * Test DatabaseRedundantImageValidator
 * Created by qinyuan on 15-3-18.
 */
public class DatabaseRedundantImageValidatorTest {

    private DatabaseRedundantImageValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new DatabaseRedundantImageValidator();
        validator.setColumns(Lists.newArrayList("Branch.logo"));
    }

    @Test
    public void testIsRedundant() throws Exception {
        String testPath = "/var/ftp/mall/branch/logo/SgktfeqFDrllgIdjnyrU_make.png";
        System.out.println(validator.isRedundant(testPath));

        testPath = "/var/ftp/mall/branch/logo/SgktfeqFDrllgIdjnyrU_make_thumbnail_middle.png";
        System.out.println(validator.isRedundant(testPath));

        testPath = "/var/ftp/mall/branch/logo/SgktfeqFDrllgIdjnyrU_make_thumbnail_middle_.png";
        System.out.println(validator.isRedundant(testPath));

        testPath = "/var/ftp/mall/branch/logo/SgktfeqFDrllgIdjnyrU_make_.png";
        System.out.println(validator.isRedundant(testPath));

        testPath = "/var/ftp/img01.taobaocdn.com/imgextra/i1/761679524/TB2reV4bVXXXXcFXXXXXXXXXXXX-761679524.jpg";
        System.out.println(validator.isRedundant(testPath));

        testPath = "/var/ftp/img01.taobaocdn.com/imgextra/i1/761679524/TB2reV4bVXXXXcFXXXXXXXXXXXX-761679524_thumbnail_small.jpg";
        System.out.println(validator.isRedundant(testPath));
    }
}
