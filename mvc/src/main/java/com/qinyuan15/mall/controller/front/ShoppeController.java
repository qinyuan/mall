package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.Branch;
import com.qinyuan15.mall.core.dao.BranchDao;
import com.qinyuan15.utils.image.ImageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Branch shoppe page controller
 * Created by qinyuan on 15-2-17.
 */
@Controller
public class ShoppeController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShoppeController.class);

    @RequestMapping("/shoppe")
    public String index(ModelMap model, @RequestParam(value = "id", required = false) Integer id) {

        Branch branch = new BranchDao().getInstance(id);
        int posterHeight;
        try {
            posterHeight = new ImageParser(branch.getPoster()).getHeight();
        } catch (Exception e) {
            posterHeight = 0;
            LOGGER.error("Fail to get height of image {}: {}", branch.getPoster(), e);
        }

        adjustBranch(branch);
        model.addAttribute("branch", branch);
        if (posterHeight > 0) {
            String html = "<div class='poster' style='background-image: url(" + branch.getPoster()
                    + ");height:" + posterHeight + "px;'></div>";
            model.addAttribute("headerAdditions", html);
        }

        addCssAndJs("list-snapshots");
        addHeadJs("image-adjust");
        setTitle("'" + branch.getName() + "'品牌相关商品");
        return "shoppe";
    }
}
