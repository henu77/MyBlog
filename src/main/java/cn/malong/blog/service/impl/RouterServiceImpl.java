package cn.malong.blog.service.impl;

import cn.malong.blog.dao.*;
import cn.malong.blog.pojo.*;
import cn.malong.blog.service.RouterService;
import cn.malong.blog.utils.CalendarUtil;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.MarkdownUtils;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author malong
 * @Date 2021-10-29 20:36:44
 */
@Service
public class RouterServiceImpl implements RouterService {

    @Autowired
    private TypesMapper typesMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BlogsMapper blogsMapper;
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private AdMapper adMapper;
    @Autowired
    private MessageMapper messageMapper;

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
            this.removeAttribute(session, "login_msg");
            return "/user/login";
        } else {
            String role = userInfo.getRole();
            if (StaticVariable.ROLE_ADMIN.equals(role) || StaticVariable.ROLE_ROOT.equals(role)) {
                return "redirect:/admin/index.html";
            } else {
                session.setAttribute("admin_msg", "您没有管理员权限！");
                this.removeAttribute(session, "admin_msg");
                return "/user/article";
            }
        }
    }

    @Override
    public String toAdminWrite(Model model) {
        List<Type> allTypes = typesMapper.getAllTypes();
        model.addAttribute("allTypes", allTypes);
        return "/admin/write";
    }

    @Override
    public String toUserEdit(int Id, Model model) {
        UserInfo userInfoById = userInfoMapper.getUserInfoById(Id);
        model.addAttribute("oldUserInfo", userInfoById);
        return "/admin/user-edit";
    }

    @Override
    public String toUpdateType(int id, Model model) {
        Type type = typesMapper.getTypeById(id);
        model.addAttribute("oldType", type);
        return "/admin/type-update";
    }

    @Override
    public String toReadBolg(int blogId, Model model) {
        Blog blogById = blogsMapper.getBlogById(blogId);
        //markdown转换成html
        if (blogById != null) {
            String s = MarkdownUtils.markdownToHtmlExtensions(blogById.getContent());
            blogById.setContent(s);
        }
        model.addAttribute("blogById", blogById);
        Date creatTime = blogById.getCreatTime();
        Map<String, Integer> calendarMap = new LinkedHashMap<>();
        calendarMap.put("year", CalendarUtil.getYear(creatTime));
        calendarMap.put("month", CalendarUtil.getMonth(creatTime));
        calendarMap.put("day", CalendarUtil.getDay(creatTime));
        model.addAttribute("calendar", calendarMap);
        blogsMapper.addViews(blogId);
        return "/user/read";
    }

    @Override
    public String toUpdateBlog(int id, Model model) {
        Blog blogById = blogsMapper.getBlogById(id);
        List<Type> allTypes = typesMapper.getAllTypes();
        model.addAttribute("allTypes", allTypes);
        String imgPath = blogById.getFirstPicture();
        if (!imgPath.startsWith("D:") && !imgPath.startsWith("/linux")) {
            imgPath = "/linux" + imgPath;
            blogById.setFirstPicture(imgPath);
        }
        model.addAttribute("blog", blogById);
        return "/admin/updateBlog";
    }

    @Override
    public String toArticle(Model model) {
        List<Blog> hotBlogs = blogsMapper.getHotBlogs();
        model.addAttribute("hotBlogs", hotBlogs);
        List<Blog> topBlogs = blogsMapper.getTopBlogs();
        model.addAttribute("topBlogs", topBlogs);
        List<Type> allType = typesMapper.getAllTypes();
        model.addAttribute("allType", allType);
        Set<UserInfo> recentViewUsers =
                (Set<UserInfo>) ServletUtil.getRequest().getServletContext().getAttribute("recentViewUser");
        model.addAttribute("recentViewUser", recentViewUsers);
        return "/user/article";
    }

    @Override
    public String toArticleByType(int typeId, Model model) {
        Type typeById = typesMapper.getTypeById(typeId);
        model.addAttribute("type", typeById);
        List<Blog> hotBlogs = blogsMapper.getHotBlogs();
        model.addAttribute("hotBlogs", hotBlogs);
        List<Blog> topBlogs = blogsMapper.getTopBlogs();
        model.addAttribute("topBlogs", topBlogs);
        Set<UserInfo> recentViewUsers =
                (Set<UserInfo>) ServletUtil.getRequest().getServletContext().getAttribute("recentViewUser");
        model.addAttribute("recentViewUser", recentViewUsers);
        return "/user/articleByType";
    }

    @Override
    public String toWelcome(Model model) {
        Integer allUserNum = userInfoMapper.countAll();
        Integer allBlogNum = blogsMapper.countAllBlogs();
        Integer allBlogViews = blogsMapper.countAllBLogViews();
        Integer allBLogComment = commentsMapper.countComment();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("allUserNum", allUserNum==null?0:allUserNum);
        data.put("allBlogNum", allBlogNum==null?0:allBlogNum);
        data.put("allBlogViews", allBlogViews==null?0:allBlogViews);
        data.put("allBLogComment", allBLogComment==null?0:allBLogComment);
        model.addAttribute("dataStatistics", data);
        return "/admin/welcome";
    }

    @Override
    public String toUserIndex(Model model) {
        List<Blog> hotBlogs = blogsMapper.getIndexHotBlogs();
        List<Map<String, Object>> hotData = new ArrayList<>();
        for (Blog blog :
                hotBlogs) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("id", blog.getId());
            temp.put("firstPic", StaticVariable.pathTransform(blog.getFirstPicture()));
            temp.put("title", blog.getTitle());
            temp.put("date", DateUtils.dateToString(blog.getCreatTime()));
            hotData.add(temp);
        }
        model.addAttribute("hotBLogs", hotData);
        return "/user/index";
    }

    @Override
    public String toSearchArticle(String title, Model model) {
        List<Blog> hotBlogs = blogsMapper.getHotBlogs();
        model.addAttribute("hotBlogs", hotBlogs);
        List<Blog> topBlogs = blogsMapper.getTopBlogs();
        model.addAttribute("topBlogs", topBlogs);
        List<UserInfo> recentViewUsers =
                (List<UserInfo>) ServletUtil.getRequest().getServletContext().getAttribute("recentViewUser");
        model.addAttribute("recentViewUser", recentViewUsers);
        model.addAttribute("title", title);
        return "/user/articleSearch";
    }

    @Override
    public String toLink(Model model) {
        List<Advertisement> queryAllAds = adMapper.queryAllAdsIsPass();
        model.addAttribute("allAds", queryAllAds);
        return "/user/link";
    }

    @Override
    public String toMessage(Model model) {
        return "/user/message";
    }

    /**
     * 设置5s后删除session中的信息
     *
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
