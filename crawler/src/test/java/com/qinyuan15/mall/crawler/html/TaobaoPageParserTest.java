package com.qinyuan15.mall.crawler.html;

import com.qinyuan15.utils.test.TestFileUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test TaobaoPageParser
 * Created by qinyuan on 15-5-4.
 */
public class TaobaoPageParserTest {
    @Test
    public void testGetItemId() throws Exception {
        String html = TestFileUtils.read("tmall.html");
        TaobaoPageParser parser = new TaobaoPageParser(html);
        assertThat(parser.getItemId()).isEqualTo("40780735321");
    }

    @Test
    public void testParseParameters() throws Exception {
        String html = TestFileUtils.read("tmall.html");
        TaobaoPageParser parser = new TaobaoPageParser(html);
        assertThat(parser.getParameters()).contains("品牌: Marc＆Janie\n")
                .contains("货号: 15075\n").contains("适用性别: 男\n").contains("颜色分类: 迷彩\n")
                .contains("参考身高: 18M 时尚卡通迷彩设计 24M 高档进口面料 3T 手感温和舒适 4T 内里采用95%弹性棉 5T 更加细腻柔软\n")
                .contains("模特实拍: 实拍无模特\n").contains("是否带帽子: 有帽不可拆\n")
                .contains("衣门襟: 拉链衫\n").contains("领型: 其他\n").contains("面料: 其他\n")
                .contains("适用季节: 春秋\n").contains("风格: 欧美\n").contains("图案: 其他\n")
                .contains("厚薄: 常规\n");
    }
}
