package cn.malong.blog.service;

/**
 * @author marlone
 * @Date 2021/11/22 15:51
 */
public interface EmailService {
    String sentVerifyCode(String receiver);

    void sendEmail(String receiver, String verifyCode);
}
