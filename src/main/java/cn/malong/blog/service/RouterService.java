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

    String toUserEdit(int Id,Model model);

    String toUpdateType(int id,Model model);
}
