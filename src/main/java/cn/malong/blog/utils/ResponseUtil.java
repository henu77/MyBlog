package cn.malong.blog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author malong
 */
public class ResponseUtil<T> {

    /**
     * 示例：
     * {
     * "code": 0,
     * "msg": "",
     * "count": 1000,
     * "data": [{}, {}]
     * }
     */
    private int code;
    private String msg;
    private int count;
    private List<T> data;
    private int pages;


    public ResponseUtil() {
        code = -1;
        msg = "";
        count = 0;
        data = new LinkedList<>();
    }

    public ResponseUtil(int code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", data.toArray());
        map.put("pages", pages);
        String result2 = "{\"" + code + "\"" + ":-1}";
        try {
            result2 = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result2;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
