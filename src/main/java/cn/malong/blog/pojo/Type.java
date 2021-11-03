package cn.malong.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author malong
 * @Date 2021-10-30 20:28:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private int id;
    private String name;

    public Type(int id) {
        this.id = id;
    }
}
