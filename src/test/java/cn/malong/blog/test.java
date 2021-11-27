package cn.malong.blog;

import cn.malong.blog.utils.StaticVariable;
import cn.malong.blog.utils.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author marlone
 * @Date 2021/11/27 10:09
 */
public class test {


    private RestTemplate restTemplate = new RestTemplate();
    @Test
    public void t(){
        String province = "";
        JSONObject json = new JSONObject();
        json.put("ak", StaticVariable.BAIDU_AK);
        json.put("ip", "10.37.77.60");
        JSONObject result =
                restTemplate.getForObject(
                        "http://api.map.baidu.com/location/ip?ak={ak}&ip={ip}&coor=bd09ll",
                        JSONObject.class,
                        json);
        System.out.println(result);
        if (result.getInteger("status").equals(0)) {
            province = String.valueOf(result.getJSONObject("content").getJSONObject("address_detail").get("province"));
        }
        System.out.println(province);
    }
}
