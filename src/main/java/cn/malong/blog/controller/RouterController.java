package cn.malong.blog.controller;

import cn.malong.blog.service.RouterService;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
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

    @RequestMapping("/admin/toUpdateType")
    public String tpUpdateType(int id, Model model) {
        return routerServiceImpl.toUpdateType(id, model);
    }

    @RequestMapping("/user/toReadBlog/{blogId}")
    public String toReadBolg(@PathVariable("blogId") int blogId, Model model) {
        return routerServiceImpl.toReadBolg(blogId, model);
    }

    @RequestMapping("/admin/toUpdateBlog")
    public String toUpdateBlog(int id, Model model) {
        return routerServiceImpl.toUpdateBlog(id, model);
    }

    @RequestMapping("/admin/toWelcome")
    public String toWelcome(Model model) {
        return routerServiceImpl.toWelcome(model);
    }

    @RequestMapping("/user/toArticle")
    public String toArticle(Model model) {
        return routerServiceImpl.toArticle(model);
    }

    @RequestMapping("/user/toArticleByType/{typeId}")
    public String toArticleByType(@PathVariable("typeId") int typeId, Model model) {
        return routerServiceImpl.toArticleByType(typeId, model);
    }

    @RequestMapping({"/", "", "/user/toIndex"})
    public String toIndex(Model model) {
        return routerServiceImpl.toUserIndex(model);
    }
}
