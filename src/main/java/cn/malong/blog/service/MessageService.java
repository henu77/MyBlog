package cn.malong.blog.service;

/**
 * @author marlone
 * @Date 2021/12/8 17:49
 */
public interface MessageService {
    String leaveAMessage(String content);

    String more(int page);
}
