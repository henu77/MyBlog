package cn.malong.blog.controller;

import cn.malong.blog.service.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 * @Date 2021-10-29 20:05:19
 */
@Controller
public class RouterController {

    @Autowired
    private RouterService routerServiceImpl;

    @RequestMapping("/toAdminIndex")
    public String toAdminIndex(HttpSession session) {
        return routerServiceImpl.toAdminIndex(session);
    }

    @RequestMapping("/admin/write")
    public String toAdminWrite(Model model) {
        return routerServiceImpl.toAdminWrite(model);
    }

    @RequestMapping("/admin/toUserEdit")
    public String toUserEdit(int Id, Model model) {
        return routerServiceImpl.toUserEdit(Id, model);
    }
}
