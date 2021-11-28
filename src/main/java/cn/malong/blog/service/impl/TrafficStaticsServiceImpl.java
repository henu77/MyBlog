package cn.malong.blog.service.impl;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.dao.TrafficStaticsMapper;
import cn.malong.blog.pojo.Province;
import cn.malong.blog.pojo.TrafficStatics;
import cn.malong.blog.service.TrafficStaticsService;
import cn.malong.blog.utils.DateUtils;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.StaticVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author marlone
 * @Date 2021/11/28 16:55
 */
@Service
public class TrafficStaticsServiceImpl implements TrafficStaticsService {
    @Autowired
    private TrafficStaticsMapper trafficStaticsMapper;
    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public String getNearlyAWeekData() {
        List<TrafficStatics> nearlyAWeekData = trafficStaticsMapper.getNearlyAWeekData();
        ResponseUtil<TrafficStatics> json = new ResponseUtil<>();
        if (nearlyAWeekData.isEmpty()) {
            json.setCode(0);
            json.setMsg("最近一周的访问数据为空！");
        } else {
            json.setCode(1);
            json.setMsg("获取数据成功!");
            json.setData(nearlyAWeekData);
        }
        return json.toString();
    }

    @Override
    public String getUserFromData() {
        List<Province> provinceList = provinceMapper.getAllData();
        ResponseUtil<Map<String, Object>> json = new ResponseUtil<>();
        if (provinceList.isEmpty()) {
            json.setCode(0);
            json.setMsg("用户访问地信息为空！");
        } else {
            json.setCode(1);
            json.setMsg("获取数据成功!");
            List<Map<String, Object>> data = new ArrayList<>();
            for (Province p :
                    provinceList) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("name", StaticVariable.getProvinceName(p.getName()));
                map.put("value", p.getCount());
                data.add(map);
            }
            json.setData(data);
        }
        return json.toString();
    }
}
