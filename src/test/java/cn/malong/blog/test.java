package cn.malong.blog;

import cn.malong.blog.dao.ProvinceMapper;
import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

/**
 * @author marlone
 * @Date 2021/11/27 10:09
 */
@Slf4j
@SpringBootTest
public class test {

    @Autowired
    private ProvinceMapper provinceMapper;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void t() {
        addProvinceViewCounts();
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Async
    void addProvinceViewCounts() {
        System.out.println(provinceMapper.addCount(getIPProvince()));
    }

    /**
     * 获取IP所在地（省份）
     */
    public String getIPProvince() {
        String province = "";
        JSONObject json = new JSONObject();
        json.put("ak", StaticVariable.BAIDU_AK);
        json.put("ip", "61.158.148.111");
        JSONObject result =
                restTemplate.getForObject(
                        "http://api.map.baidu.com/location/ip?ak={ak}&ip={ip}&coor=bd09ll",
                        JSONObject.class,
                        json);
        logger.info(result.toString());
        logger.info("61.158.148.111");
        if (result.getInteger("status").equals(0)) {
            province = String.valueOf(result.getJSONObject("content").getJSONObject("address_detail").get("province"));
        }
        System.out.println(province);
        return province;
    }
}
