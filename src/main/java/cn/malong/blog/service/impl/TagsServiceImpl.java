package cn.malong.blog.service.impl;

import cn.malong.blog.dao.TagsMapper;
import cn.malong.blog.pojo.Tag;
import cn.malong.blog.service.TagsService;
import cn.malong.blog.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Csy
 * @Classname TagsServiceImpl
 * @date 2021-10-31 22:52
 * @Description TODO
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public String getTagsByLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Tag> tags = tagsMapper.getTagsByLimit(startIndex,limit);
        return tagDataToJson(tags);
    }

    private String tagDataToJson(List<Tag> commentData) {
        ResponseUtil<Tag> json = new ResponseUtil<>();
        if (null == commentData) {
            json.setCode(1);
            json.setMsg("获取用户信息失败");
        } else if (commentData.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户信息为空");
        } else {
            json.setCode(0);
            json.setCount(commentData.size());
            json.setMsg("获取用户信息成功");
            json.setData(commentData);
        }
        return json.toString();
    }
}
