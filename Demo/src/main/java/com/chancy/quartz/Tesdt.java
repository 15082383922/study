package com.chancy.quartz;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Tesdt {
    //发送的邮箱 内部bai代码只适用qq邮箱
    private static final String USER = "871396035@qq.com";
    //授权密码du 通过QQ邮箱设置->账户zhi->POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务->开启POP3/SMTP服务获取
    private static final String PWD = "extmkrejddhvbbbg";
    private List<String> to;
    private List<String> cc;//抄送
    private List<String> bcc;//密送
    private String[] fileList;//附件dao
    private String subject;//主题
    private String content;//内容，可以用html语言写

    public void sendMessage(String from, String host, String port, String username, String password, boolean ssl, boolean auth, List<String> too) throws Exception {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        //下面两段代码是设置ssl和端口，不设置发送不出去。
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 表示SMTP发送邮件，需要进行身份验证
        props.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");//QQ邮箱的服务器 如果是企业邮箱或者其他邮箱得更换该服务器地址
        // 发件人的账号
        props.put("mail.user", USER);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", PWD);
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
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);
        //发送
        if (to != null) {
            String toList = getMailList(to);
            InternetAddress[] iaToList = new InternetAddress().parse(toList);
            message.setRecipients(RecipientType.TO, iaToList); // 收件人
        }
        //抄送
        if (cc != null) {
            String toListcc = getMailList(cc);
            InternetAddress[] iaToListcc = new InternetAddress().parse(toListcc);
            message.setRecipients(RecipientType.CC, iaToListcc); // 抄送人
        }
        //密送
        if (bcc != null) {
            String toListbcc = getMailList(bcc);
            InternetAddress[] iaToListbcc = new InternetAddress().parse(toListbcc);
            message.setRecipients(RecipientType.BCC, iaToListbcc); // 密送人
        }
        message.setSentDate(new Date()); // 发送日期 该日期可以随意写,你可以写上昨天的日期（效果很特别,亲测,有兴趣可以试试）,或者抽象出来形成一个参数。
        message.setSubject(subject); // 主题
        message.setText(content); // 内容
        //显示以html格式的文本内容
        messageBodyPart.setContent(content, "text/html;charset=utf-8");
        multipart.addBodyPart(messageBodyPart);
        //保存多个附件
        if (fileList != null) {
            addTach(fileList, multipart);
        }
        message.setContent(multipart);
        // 发送邮件
        Transport.send(message);
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }

    private String getMailList(List<String> mailArray) {
        StringBuffer toList = new StringBuffer();
        int length = mailArray.size();
        if (mailArray != null && length < 2) {
            toList.append(mailArray.get(0));
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray.get(i));
                if (i != (length - 1)) {
                    toList.append(",");
                }
            }
        }
        return toList.toString();
    }

    //添加多个附件
    public void addTach(String fileList[], Multipart multipart) throws Exception {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList[index]);
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
            multipart.addBodyPart(mailArchieve);
        }
    }

    //以下是演示demo
    public static void main(String args[]) {
        Tesdt mail = new Tesdt();

        mail.setSubject("java邮件");
        StringBuilder builder = new StringBuilder();

        //写入内容
        builder.append("<br\\>" + "你好 这是第一个java 程序发送邮件" + "<br\\>" + "你好 这是第二个java 程序发送邮件" + "<br\\>" + "你好 这是第三个java 程序发送邮件");
        mail.setContent(builder.toString());
        //收件人 可以发给其他邮箱(163等) 下同
        List<String> strings = new ArrayList<String>();
        strings.add("changming.zhu@utstar.com");
        mail.setTo(strings);
        //抄送
        // mail.setCc(new String[] {"xxx@qq.com","xxx@qq.com"});
        //密送
        //mail.setBcc(new String[] {"xxx@qq.com","xxx@qq.com"});
        //发送附件列表 可以写绝对路径 也可以写相对路径(起点是项目根目录)
        // mail.setFileList(new String[] {"D:\\aa.txt"});
        //发送邮件
        try {
            mail.sendMessage("","","","","",true,true,new ArrayList<String>());
            System.out.println("发送邮件成功！");
        } catch (Exception e) {
            System.out.println("发送邮件失败！");
            e.printStackTrace();
        }
    }
}