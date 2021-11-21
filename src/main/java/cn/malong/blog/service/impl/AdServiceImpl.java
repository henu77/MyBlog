package cn.malong.blog.service.impl;

import cn.malong.blog.dao.AdMapper;
import cn.malong.blog.pojo.Advertisement;
import cn.malong.blog.pojo.Comment;
import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.service.AdService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdMapper adMapper;

    @Override
    public String submit(Advertisement advertisement) {
        advertisement.setSubmitTime(new Date());
        ResponseUtil<String> json = new ResponseUtil<>();
        UserInfo userInfoFromSession = UserServiceImpl.getUserInfoFromSession();
        if (null == userInfoFromSession) {
            //用户未登录
            json.setCode(0);
            json.setMsg("请先登录！");
            return json.toString();
        }
        advertisement.setUserId(userInfoFromSession);
        int result = adMapper.insertAnAd(advertisement);
        if (result > 0) {
            //成功
            json.setCode(1);
            json.setMsg("提交成功");
        } else {
            //失败
            json.setCode(0);
            json.setMsg("提交失败");
        }
        return json.toString();
    }

    @Override
    public String dataLimit(int page, int limit) {
        int startIndex = (page - 1) * limit;
        List<Advertisement> advertisementDataByLimit = adMapper.getAdDataByLimit(startIndex, limit);
        ResponseUtil<Map<String, Object>> json = new ResponseUtil<>();
        if (null == advertisementDataByLimit) {
            json.setCode(1);
            json.setMsg("获取评论信息失败");
        } else if (advertisementDataByLimit.isEmpty()) {
            json.setCode(1);
            json.setMsg("用户评论为空");
        } else {
            json.setCode(0);
            json.setCount(adMapper.allAdCount());
            json.setMsg("获取评论信息成功");
            List<Map<String, Object>> jsonMap = new LinkedList<>();
            for (Advertisement advertisement : advertisementDataByLimit) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("nickname", advertisement.getUserId().getNickname());
                temp.put("email", advertisement.getEmail());
                temp.put("linkPath", advertisement.getPath());
                temp.put("des", advertisement.getDes());
                temp.put("nowState", advertisement.isState() == true ? "通过" : "未通过");
                temp.put("submitTime", DateUtils.dateToString(advertisement.getSubmitTime()));
                jsonMap.add(temp);
            }
            json.setData(jsonMap);
        }
        return json.toString();
    }
}
