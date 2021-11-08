package cn.malong.blog;

import cn.malong.blog.dao.UserInfoMapper;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.CalendarUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class MyBlogMasterApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserInfoMapper userInfoMapper;

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

    @Test
    void test() {
        Date date = new Date();
        System.out.println(CalendarUtil.getDay(date));
        System.out.println(CalendarUtil.getMonth(date)+1);
        System.out.println(CalendarUtil.getYear(date));
        System.out.println(CalendarUtil.getDay(date));
        System.out.println(CalendarUtil.getMonth(date)+1);
        System.out.println(CalendarUtil.getYear(date));
        System.out.println(CalendarUtil.getDay(date));
        System.out.println(CalendarUtil.getMonth(date)+1);
        System.out.println(CalendarUtil.getYear(date));
    }
}
