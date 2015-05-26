package com.qinyuan15.mall.core.branch;

import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.BranchDao;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to build BranchGroup
 * Created by qinyuan on 15-4-6.
 */
public class BranchGroupBuilder {

    private final static String NUMBER_LETTER = "0-9";
    private int groupSize;

    public BranchGroupBuilder setGroupSize(int groupSize) {
        this.groupSize = groupSize;
        return this;
    }

    public List<BranchGroup> build(List<Branch> branches) {
        List<BranchGroup> branchGroups = buildSimpleGroup(branches);
        if (groupSize <= 1) {
            return branchGroups;
        } else {
            return this.buildMultiGroup(branchGroups);
        }
    }

    public List<BranchGroup> build() {
        return build(new BranchDao().getInstances());
    }

    private List<BranchGroup> buildMultiGroup(List<BranchGroup> branchGroups) {
        List<BranchGroup> multiBranchGroups = new ArrayList<>();
        int alphabeticGroupCount = branchGroups.size() - 1;
        int groupCount = alphabeticGroupCount / groupSize;
        for (int i = 0; i < groupCount; i++) {
            int startIndex = i * groupSize;
            int endIndex = i == groupCount - 1 ? alphabeticGroupCount : (i + 1) * groupSize;
            multiBranchGroups.add(combineBranchGroup(branchGroups.subList(startIndex, endIndex)));
        }
        multiBranchGroups.add(branchGroups.get(alphabeticGroupCount));
        return multiBranchGroups;
    }

    private BranchGroup combineBranchGroup(List<BranchGroup> branchGroups) {
        String letter = null;
        List<Branch> branches = new ArrayList<>();
        for (int i = 0; i < branchGroups.size(); i++) {
            BranchGroup branchGroup = branchGroups.get(i);
            if (i == 0) {
                letter = branchGroup.getLetter();
            }

            branches.addAll(branchGroup.getBranches());

            if (i == branchGroups.size() - 1) {
                letter += "-" + branchGroup.getLetter();
            }
        }
        return new BranchGroup(letter, branches);
    }

    private List<BranchGroup> buildSimpleGroup(List<Branch> branches) {
        Map<String, List<Branch>> map = groupByLetter(branches);
        List<BranchGroup> branchGroups = new ArrayList<>();

        for (String letter : getLetters()) {
            List<Branch> branchesOfLetter = map.get(letter);
            if (branchesOfLetter == null) {
                branchesOfLetter = new ArrayList<>();
            }
            branchGroups.add(new BranchGroup(letter, branchesOfLetter));
        }

        return branchGroups;
    }

    private Map<String, List<Branch>> groupByLetter(List<Branch> branches) {
        Map<String, List<Branch>> map = new HashMap<>();

        for (Branch branch : branches) {
            String firstLetter = branch.getFirstLetter();
            if (!StringUtils.hasText(firstLetter)) {
                continue;
            }
            if (firstLetter.matches("^\\d.*$")) {
                firstLetter = NUMBER_LETTER;
            }

            if (!map.containsKey(firstLetter)) {
                map.put(firstLetter, new ArrayList<Branch>());
            }
            map.get(firstLetter).add(branch);
        }

        return map;
    }

    private List<String> getLetters() {
        List<String> letters = new ArrayList<>();
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            letters.add(String.valueOf(ch));
        }
        letters.add(NUMBER_LETTER);
        return letters;
    }
}
