package nchu.stu.Agasar.Component;

import nchu.stu.Agasar.Entity.User;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class Email {

    private static String changeEncode(String str) {

        try {

            str = MimeUtility.encodeText(new String(str.getBytes(), "UTF-8"), "UTF-8", "B"); // "B"代表Base64

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        return str;

    }



    public static boolean sendHtmlMail(User user) {
        final String smtpServer="smtp.aliyun.com";
        final String port="465";
        final  String  fromUserName="Agasar@aliyun.com";
        final String fromUserPassword="qaz1234567890+";
        final  String url="http://www.agasar.top/Nexcel/sign/active?email="+user.getEmail()+"&code="+user.getCode();

        Properties properties = new Properties();

        properties.put("mail.smtp.host", smtpServer);

        properties.put("mail.transport.protocol", "smtp");

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 使用JSSE的SSL

        properties.put("mail.smtp.socketFactory.fallback", "false"); // 只处理SSL的连接,对于非SSL的连接不做处理

        properties.put("mail.smtp.port", port);

        properties.put("mail.smtp.socketFactory.port",port);

        Session session = Session.getInstance(properties);

        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);



        try {

            // 发件人

            Address address = new InternetAddress(fromUserName);

            message.setFrom(address);

            // 收件人

            Address toAddress = new InternetAddress(user.getEmail());

            message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO



            // 主题message.setSubject(changeEncode(emailInfo.getSubject()));

            message.setSubject("这是一封激活账号的邮件","UTF-8");

            // 时间

            message.setSentDate(new Date());



            Multipart multipart = new MimeMultipart();



            // 创建一个包含HTML内容的MimeBodyPart

            BodyPart html = new MimeBodyPart();

            // 设置HTML内容

            html.setContent("<div>\n" +
                    "    <h1>Welcome<h1>\n" +
                    "    <h2>非常感谢你的加入，还差最后一步，点击下方链接，你就能使用Nexcel!\n"+
                    "    <p>使用中如若遇到未知问题欢迎联系管理员Agasar@aliyun.com</p>\n" +
                    "    <a href="+url+">Click here</a>\n" +
                    "</div>", "text/html;charset=UTF-8");

            multipart.addBodyPart(html);

            // 将MiniMultipart对象设置为邮件内容

            message.setContent(multipart);

            message.saveChanges();

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }



        try {

            Transport transport = session.getTransport("smtp");

            transport.connect(smtpServer, fromUserName, fromUserPassword);

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }



        return true;

    }

}
