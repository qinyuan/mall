package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.mall.core.dao.HotSearchWordDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to query hot search word
 * Created by qinyuan on 15-2-26.
 */
@Controller
public class HotSearchWordController extends BaseController {

    @ResponseBody
    @RequestMapping("/json/hotSearchWord.json")
    public String index(@RequestParam(value = "categoryId", required = true) Integer categoryId) {
        HotSearchWordDao dao = new HotSearchWordDao();
        return toJson(dao.getInstances(categoryId));
    }
}
