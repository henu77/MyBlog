package cn.malong.blog;

import cn.malong.blog.dao.CommentsMapper;
import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.CalendarUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class MyBlogMasterApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    CommentsMapper commentsMapper;

    @Test
    void contextLoads() throws SQLException {
//        System.out.println(this.dataSource.getClass());
//        Connection connection = this.dataSource.getConnection();
//        System.out.println(connection);
//        DruidDataSource druidDataSource = (DruidDataSource)this.dataSource;
//        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
//        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
//        connection.close();
    }

    @Test
    void contextLoads1() throws SQLException {
//        System.out.println(this.dataSource.getClass());
//        Connection connection = this.dataSource.getConnection();
//        System.out.println(connection);
//        DruidDataSource druidDataSource = (DruidDataSource)this.dataSource;
//        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
//        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
//        connection.close();
    }

    private void transformAvatarPath(List<Comment> allComments) {
        String avatar = "";
        String avatar2 = "";
        for (Comment c :
                allComments) {
            avatar = c.getUserId().getAvatar();
            if (!avatar.substring(0, 2).equals("D:")&& !avatar2.substring(0, 6).equals("/linux")) {
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
}
