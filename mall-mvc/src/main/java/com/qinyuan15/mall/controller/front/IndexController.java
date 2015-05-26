package com.qinyuan15.mall.controller.front;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.mall.core.dao.IndexLogo;
import com.qinyuan15.mall.core.dao.IndexLogoDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Index page controller
 * Created by qinyuan on 15-2-14.
 */
@Controller
public class IndexController extends ImageController {

    @RequestMapping("/index")
    public String index(ModelMap model) {
        setTitle("淘宝宝-首页");
        List<IndexLogo> indexLogos = new IndexLogoDao().getInstances();
        model.addAttribute("indexLogos", adjustIndexLogos(indexLogos));
        addIEJs("index-ie-patch");
        return "index";
    }
}
