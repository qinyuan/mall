package com.qinyuan15.mall.controller.json;

import com.qinyuan15.mall.controller.BaseController;
import com.qinyuan15.utils.ChineseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Calculate first letter of Chinese string
 * Created by qinyuan on 15-2-25.
 */
@Controller
public class ChineseLetterController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/chinese-letter.json")
    public String index(@RequestParam(value = "string", required = true) String string) {
        if (!StringUtils.hasText(string)) {
            return "{}";
        }

        String firstChar = string.trim().substring(0, 1);
        String firstLetter = ChineseUtils.getPhoneticLetter(firstChar);
        return toJson(createSimpleMap(RESULT, firstLetter));
    }
}
