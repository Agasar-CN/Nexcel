package nchu.stu.Agasar.Component;
import nchu.stu.Agasar.Entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
public class SampleMail {
   private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT = "80";//或"80"

    public static void sendMail(User user) {
        String url="http://www.agasar.top/Nexcel/sign/active?email="+user.getEmail()+"&code="+user.getCode();
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
       props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
       props.put("mail.smtp.port", ALIDM_SMTP_PORT);
        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
        // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       //  props.put("mail.smtp.socketFactory.port", "465");
        // props.put("mail.smtp.port", "465");


        // 发件人的账号
        props.put("mail.user", "agasar@aliyun.com");

        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "qaz1234567890+");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        mailSession.setDebug(true);
//        mailSession.setDebug(true);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        try {

            // 设置发件人
            InternetAddress from = new InternetAddress("agasar@aliyun.com");
            message.setFrom(from);
            Address[] a = new Address[1];
            a[0] = new InternetAddress("agasar@aliyun.com");
            message.setReplyTo(a);



            // 设置收件人
            InternetAddress to = new InternetAddress(user.getEmail());
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject("这是一封激活账号的邮件","UTF-8");
            // 设置邮件的内容体
            message.setContent("<div>\n" +
                    "    <h1>Welcome<h1>\n" +
                    "    <h2>非常感谢你的加入，还差最后一步，点击下方链接，你就能使用Nexcel!\n"+
                    "    <p>使用中如若遇到未知问题欢迎联系管理员Agasar@163.com</p>\n" +
                    "    <a href="+url+">Click here</a>\n" +
                    "</div>", "text/html;charset=UTF-8");
            //设置邮件的发送时间,默认立即发送
            message.setSentDate(new Date());


            // 发送邮件
            Transport.send(message);
        }
        catch (MessagingException e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }
    }
}
