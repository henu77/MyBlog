package cn.malong.blog.service;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.StaticString;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author malong
 * @Date 2021-10-29 20:36:44
 */
@Service
public class RouterServiceImpl implements RouterService {
    /**
     * 1.从session中取用户信息
     * 2. 用户信息为空 说明未登录 则返回登录页
     * 3. 用户信息不为空 则判断用户权限，如果用户权限为 admin 或 root 则放行
     * 用户权限为 user（普通用户）则返回到文章页
     *
     * @param session
     * @return
     */
    @Override
    public String toAdminIndex(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (null == userInfo) {
            session.setAttribute("login_msg", "请先登录！");
            System.out.println("toAdminIndex======>请先登录！");
            this.removeAttribute(session,"login_msg");
            return "/user/login";
        } else {
            String role = userInfo.getRole();
            if (StaticString.ROLE_ADMIN.equals(role) || StaticString.ROLE_ROOT.equals(role)) {
                System.out.println("toAdminIndex======>登录成功");
                return "redirect:/admin/index.html";
            } else {
                session.setAttribute("admin_msg", "您没有管理员权限！");
                System.out.println("toAdminIndex======>您没有管理员权限！");
                this.removeAttribute(session,"admin_msg");
                return "/user/article";
            }
        }
    }

    /**
     * 设置5s后删除session中的信息
     * @param session
     * @param attrName
     */
    private void removeAttribute(final HttpSession session, final String attrName) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 删除session中存的验证码
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, 5 * 1000);
    }

}
