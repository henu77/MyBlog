package cn.malong.blog;

import cn.malong.blog.dao.AdMapper;
import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.dao.TrafficStaticsMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.Advertisement;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.TrafficStatics;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.CalendarUtil;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

@SpringBootTest
class MyBlogMasterApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CommentsMapper commentsMapper;

    @Test
    void contextLoads() throws SQLException, FileNotFoundException {
//        System.out.println(this.dataSource.getClass());
//        Connection connection = this.dataSource.getConnection();
//        System.out.println(connection);
//        DruidDataSource druidDataSource = (DruidDataSource)this.dataSource;
//        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
//        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
//        connection.close();
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            System.out.println(random.nextInt(5)+1);
//        }
//        File file = ResourceUtils.getFile("src/main/resources/public/defaultIcon1.png");
//        System.out.println(file.getAbsolutePath());
//        System.out.println(ServletUtil.getRequest().getServletContext().getRealPath("src/main/resources/public/defaultIcon1.png"));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        list.remove(0);
        System.out.println("移除以后");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        list.add(10);
        list.add(11);
        System.out.println("添加以后");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Autowired
    private AdMapper adMapper;

    @Test
    void contextLoads1() throws SQLException {
//        Advertisement advertisement = new Advertisement();
        System.out.println("拒绝通过".substring(0,2));
//        advertisement.setTitle("广告1");
//        advertisement.setPath("123");
//        advertisement.setDes("!!!");
//        advertisement.setMiniDes("...");
//        advertisement.setIcon("000");
//        advertisement.setEmail("@qq.com");
//        advertisement.setState("未审核");
//        advertisement.setSubmitTime(new Date());
//        System.out.println(advertisement.getSubmitTime());
//        advertisement.setUserId(userInfoMapper.getUserInfoById(2));
//        int i = adMapper.insertAnAd(advertisement);
//        System.out.println(i);
//        List<Advertisement> adDataByLimit = adMapper.getAdDataByLimit(0, 10);
//        for (Advertisement a :
//                adDataByLimit) {
//            System.out.println(a);
//        }
//        System.out.println(DateUtils.dateToString(new Date()));
    }

    private void transformAvatarPath(List<Comment> allComments) {
        String avatar = "";
        String avatar2 = "";
        for (Comment c :
                allComments) {
            avatar = c.getUserId().getAvatar();
            if (!avatar.substring(0, 2).equals("D:") && !avatar2.substring(0, 6).equals("/linux")) {
                avatar = "/linux" + avatar;
                c.getUserId().setAvatar(avatar);
            }
            for (Comment childComment :
                    c.getChildComments()) {
                avatar2 = childComment.getUserId().getAvatar();
                if (!avatar2.substring(0, 2).equals("D:") && !avatar2.substring(0, 6).equals("/linux")) {
                    avatar2 = "/linux" + avatar2;
                    childComment.getUserId().setAvatar(avatar2);
                }
            }
        }
    }

    @Test
    void test() {
//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
//        DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
//        String format = dateFormat.format(date);
//        String time = timeInstance.format(date);
//        System.out.println(format + time);
//        System.out.println(CalendarUtil.getDay(date));
//        System.out.println(CalendarUtil.getMonth(date)+1);
//        System.out.println(CalendarUtil.getYear(date));
//        System.out.println(CalendarUtil.getDay(date));
//        System.out.println(CalendarUtil.getMonth(date)+1);
//        System.out.println(CalendarUtil.getYear(date));
//        System.out.println(CalendarUtil.getDay(date));
//        System.out.println(CalendarUtil.getMonth(date)+1);
//        System.out.println(CalendarUtil.getYear(date));
        List<Comment> allCommentsByBlogId = commentsMapper.getAllCommentsByBlogId(40);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("前");
        for (Comment c :
                allCommentsByBlogId) {
            System.out.println(c.toString());
            System.out.println("下面是子评论");
            for (Comment cc :
                    c.getChildComments()) {
                System.out.println(cc);
            }
            System.out.println("子评论结束");
        }
        transformAvatarPath(allCommentsByBlogId);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("后");
        for (Comment c :
                allCommentsByBlogId) {
            System.out.println(c.toString());
            System.out.println("下面是子评论");
            for (Comment cc :
                    c.getChildComments()) {
                System.out.println(cc);
            }
            System.out.println("子评论结束");
        }
    }

    @Autowired
    private TrafficStaticsMapper trafficStaticsMapper;

    @Test
    public void tt() {
//        TrafficStatics trafficStatics = new TrafficStatics(0);
//        trafficStatics.setViews(1999);
//        System.out.println(trafficStaticsMapper.updateTodayViews(trafficStatics));
        String today = (DateUtils.getToDayYY_MM_DD());
        System.out.println(today);
        System.out.println(trafficStaticsMapper.getTodayViews(today));
    }
}
