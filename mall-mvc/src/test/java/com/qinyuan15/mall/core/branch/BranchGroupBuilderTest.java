package com.qinyuan15.mall.core.branch;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BranchGroup
 * Created by qinyuan on 15-2-25.
 */
public class BranchGroupBuilderTest {
    private void print(BranchGroup branchGroup) {
        System.out.print(branchGroup.getLetter() + " ");
        System.out.println(branchGroup.getBranches().size());
    }

    @Test
    public void testBuild() throws Exception {
        BranchGroupBuilder builder = new BranchGroupBuilder();
        List<BranchGroup> branchGroups = builder.build();
        assertThat(branchGroups).hasSize(27);
        for (BranchGroup branchGroup : branchGroups) {
            print(branchGroup);
        }

        System.out.println("====================================");

        branchGroups = builder.setGroupSize(5).build();
        assertThat(branchGroups).hasSize(6);
        for (BranchGroup branchGroup : branchGroups) {
            print(branchGroup);
        }
    }
}
