package nchu.stu.Agasar.Component;

import nchu.stu.Agasar.Entity.User;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
    //发件人地址
    public static String senderAddress = "Agasar@163.com";
    //收件人地址
    public static String recipientAddress = null;
    //发件人账户名
    public static String senderAccount = "Agasar@163.com";
    //发件人账户密码
    public static String senderPassword = "zx,1851501832.yx";

    public static void sendMail(User user) throws Exception {
        recipientAddress=user.getEmail();
        Session session = getSession();
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,user);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());

        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});

        //5、关闭邮件连接
        transport.close();
    }
    public static Session getSession() {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");//指定发送的邮箱的邮箱协议
        props.setProperty("mail.smtp.host","smtp.163.com");//指定SMTP服务器
        props.setProperty("mail.smtp.port", "25");  //smtp是发信邮件服务器,端口是25
        props.setProperty("mail.smtp.auth","true");//指定是否需要SMTP验证

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(senderAddress, "smtp669350");
            }
        });
        return session;
    }
    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session,User user) throws Exception{
        String url="http://www.agasar.top/Nexcel/sign/active?email="+user.getEmail()+"&code="+user.getCode();
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("这是一封激活账号的邮件","UTF-8");
        //设置邮件正文
        msg.setContent("<div>\n" +
                "    <h1>Welcome<h1>\n" +
                "    <h2>非常感谢你的加入，还差最后一步，点击下方链接，你就能使用Nexcel!\n"+
                "    <p>使用中如若遇到未知问题欢迎联系管理员Agasar@163.com</p>\n" +
                "    <a href="+url+">Click here</a>\n" +
                "</div>", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }

}
