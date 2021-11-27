package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author marlone
 * @Date 2021/11/26 22:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    private int id;
    private String name;
    private int count;
}
