package cn.malong.blog.service;

import cn.malong.blog.pojo.Tag;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagsService {
    String  getTagsByLimit(int page,int limit);
}
