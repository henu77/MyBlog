package cn.malong.blog.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * @author malong
 * @Date 2021-10-29 20:36:22
 */
public interface RouterService {

    /**
     * 进入用户管理首页
     *
     * @param session
     * @return
     */
    String toAdminIndex(HttpSession session);

    /**
     * 进入编辑文章
     *
     * @return
     */
    String toAdminWrite(Model model);

    String toUserEdit(int Id, Model model);

    String toUpdateType(int id, Model model);

    String toReadBolg(int blogId, Model model);

    String toUpdateBlog(int id, Model model);

    String toArticle(Model model);

    String toArticleByType(int typeId, Model model);

    String toWelcome(Model model);

    String toUserIndex(Model model);

    String toSearchArticle(String title, Model model);

    String toLink(Model model);

    String toMessage(Model model);
}
