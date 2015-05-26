package com.qinyuan15.mall.core.dao;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BranchDao
 * Created by qinyuan on 15-2-27.
 */
public class BranchDaoTest {
    private BranchDao dao = new BranchDao();

    @Test
    public void testGetInstancesByCategoryId() throws Exception {
        List<Branch> branches = dao.getInstancesByCategoryId(4);
        System.out.println(branches.size());
        for (Branch branch : branches) {
            System.out.println(branch.getId());
            System.out.println(branch.getName());
        }
    }

    @Test
    public void testGetSubInstances() throws Exception {
        List<Branch> branches = dao.getSubInstances(1);
        for (Branch branch : branches) {
            assertThat(branch.getParentId()).isEqualTo(1);
        }
    }

    @Test
    public void testGetAllSubInstances() throws Exception {
        List<Branch> branches = dao.getAllSubInstances(1);
        Set<Integer> parentIds = Sets.newHashSet(1);
        for (Branch branch : branches) {
            assertThat(branch.getParentId()).isIn(parentIds);
            parentIds.add(branch.getId());
        }
    }

    @Test
    public void testGetAllSubInstancesAndSelfIdsString() throws Exception {
        String idsString = dao.getAllSubInstancesAndSelfIdsString(1);
        System.out.println(idsString);
    }

    @Test
    public void testIsUsed() throws Exception {
        System.out.println(dao.isUsed(6));
        System.out.println(dao.isUsed(7));
        System.out.println(dao.isUsed(8));
    }
}
