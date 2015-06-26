package com.qinyuan15.mall.controller.admin;

import com.qinyuan15.mall.controller.ImageController;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import com.qinyuan15.utils.hibernate.HibernateUtils;
import com.qinyuan15.utils.http.ProxyActivator;
import com.qinyuan15.utils.http.ProxyDao;
import com.qinyuan15.utils.http.ProxyRejection;
import com.qinyuan15.utils.mvc.PaginationAttributeAdder;
import com.qinyuan15.utils.mvc.PaginationItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Page to edit branch
 * Created by qinyuan on 15-2-19.
 */
@Controller
public class AdminProxyController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminProxyController.class);

    @RequestMapping("/admin-proxy")
    public String index(ModelMap model) {
        /*
        ProxyRejectionDao.Factory factory = ProxyRejectionDao.factory();
        new PaginationAttributeAdder(factory, request).setRowItemsName("rejections").add();
        */
        ProxyInfoFactory factory = new ProxyInfoFactory();
        new PaginationAttributeAdder(factory, request).setRowItemsName("rejections").add();

        ProxyDao proxyDao = new ProxyDao();
        model.addAttribute("proxyCount", proxyDao.getCount());
        model.addAttribute("fastProxyCount", proxyDao.getFastCount());

        addCssAndJs("admin-normal-edit-page");
        setTitle("代理拦截记录");
        return "admin-proxy";
    }

    @RequestMapping("/admin-proxy-reactivate.json")
    @ResponseBody
    public Map<String, Object> reactivate(@RequestParam(value = "proxyRejectionId", required = true) Integer proxyRejectionId) {
        if (isPositive(proxyRejectionId)) {
            try {
                new ProxyActivator().activateByProxyRejectionId(proxyRejectionId);
                return SUCCESS;
            } catch (Exception e) {
                LOGGER.error("fail to reactivate proxy, id: {}, info: {}", proxyRejectionId, e);
                return createFailResult("数据库操作失败");
            }
        } else {
            return createFailResult("数据格式错误！");
        }
    }

    private static class ProxyInfoFactory implements PaginationItemFactory<RejectionInfo> {
        @Override
        public long getCount() {
            return HibernateUtils.getCount(ProxyRejection.class);
        }

        @Override
        public List<RejectionInfo> getInstances(int firstResult, int maxResults) {
            String sql = "SELECT pj.id,p.type,p.host,p.port,pj.speed,pj.url,pj.reject_time,p.speed AS ps " +
                    "FROM proxy_rejection AS pj LEFT JOIN proxy AS p ON pj.proxy_id=p.id";
            List<Object[]> list = new HibernateListBuilder()
                    .addOrder("pj.reject_time", false)
                    .addOrder("pj.id", false)
                    .limit(firstResult, maxResults)
                    .buildBySQL(sql);

            List<RejectionInfo> rejectionInfos = new ArrayList<>();
            for (Object[] objects : list) {
                RejectionInfo info = new RejectionInfo();
                info.id = (Integer) objects[0];
                info.proxy = (objects[1] == null || objects[2] == null || objects[3] == null) ?
                        "" : objects[1] + "://" + objects[2] + ":" + objects[3];
                info.speed = (Integer) objects[4];
                info.url = (String) objects[5];
                info.rejectTime = DateUtils.adjustDateStringFromDB(objects[6].toString());
                info.active = (objects[7] != null) && ((Integer) objects[7] != Integer.MAX_VALUE);
                rejectionInfos.add(info);
            }
            return rejectionInfos;
        }
    }

    public static class RejectionInfo {
        private Integer id;
        private String proxy;
        private Integer speed;
        private String url;
        private String rejectTime;
        private Boolean active;

        public Integer getId() {
            return id;
        }

        public String getProxy() {
            return proxy;
        }

        public Integer getSpeed() {
            return speed;
        }

        public String getUrl() {
            return url;
        }

        public String getRejectTime() {
            return rejectTime;
        }

        public Boolean getActive() {
            return active;
        }
    }
}
