package cn.malong.blog.service;

import cn.malong.blog.pojo.Advertisement;

public interface AdService {

    String submit(Advertisement advertisement);

    String dataLimit(int page, int limit);

    String deleteById(int id);

    String aDBatchDelete(int[] ids);

    String pass(int id);

    String refuse(int id);
}
