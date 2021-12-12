1. 基于Spring Boot的个人博客系统

   #### 介绍

   本项目采用Spring Boot+MyBatis+Thymelef搭建而成。使用Maven对项目进行管理，前端使用到的技术\框架有Thymelef、Ajax、Jquery、Layui、Editor.js、Tocbot.js等，后端使用主流开发框架Spring Boot，持久层设计采用MyBatis。具体功能有后台管理、发布文章、评论文章、留言、友链、第三方登录、数据统计等。

   #### 软件架构

   JDK：1.8

   MySql：8.0.25

   Spring Boot：2.5.6


#### 安装教程

1. 下载项目
2. 在您的数据库运行sql文件下的myblog.sql文件，导入数据。
3. 将项目根目录下的home文件夹(上传的图片)复制到D盘
4. 修改application.yml配置文件中的邮箱用户名、第三方授权码为您的邮箱（需开启SMTP服务）
5. 修改application.yml配置文件中的==<font style="background:#ff0">数据库用户名密码配置</font>==
6. 运行项目

#### 使用说明

1. home文件夹要在<font style="background:#ff0">D盘的根目录下。</font>

2. myblog.sql文件已经包含建database的语句，如果在运行前，<font style="background:#ff0">您的数据库中已经存在名为myblog的database请备份您的数据。</font>

3. <font>超级管理员 用户名为：root，密码：root。</font>

4. 若项目在局域网中运行，则用户登陆地统计功能无效。

5. <font style="background:#ff0">若使用第三方登录功能则需要自自行创建第三方授权应用并填写相应信息。</font>

    - gitee平台申请参考：https://justauth.wiki/guide/oauth/gitee/
    - 阿里云平台申请参考：https://justauth.wiki/guide/oauth/aliyun/

   请注意回调地址一定要填写正确。本项目的参考格式http://localhost:8080/oauth/callback/gitee

   <img src="https://gitee.com/henu77/blogimg/raw/master/img/image-20211212231751135.png" alt="image-20211212231751135" style="zoom:67%;" />
申请成功后，请修改service/impl包下的<font style="background:#ff0">RestAuthServiceImpl类</font>中的<font style='background:#ff0'>getAuthRequest()</font>函数

<img src="https://gitee.com/henu77/blogimg/raw/master/img/image-20211212232152410.png" alt="image-20211212232152410" style="zoom:50%;" />

红框中的内容修改为你的即可。