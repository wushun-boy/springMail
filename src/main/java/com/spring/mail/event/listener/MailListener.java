package com.spring.mail.event.listener;

import com.google.common.base.Throwables;
import com.spring.mail.bean.MailWarningBean;
import com.spring.mail.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class MailListener {

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 定义事件、开启异步
     * @param myEvent
     */
    @EventListener
    @Async
    public void sendMail(MyEvent<MailWarningBean> myEvent){
        MimeMessage mimeMessage=null;
        try{
            MailWarningBean mailWarningBean=myEvent.getT();
            mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper msgHelper=new MimeMessageHelper(mimeMessage,true);
            //发送人邮箱
            msgHelper.setFrom("XXXXXX");
            //收件人邮箱(单个)
            msgHelper.setTo("XXXXXXX");
            //主题
            msgHelper.setSubject("服务器系统传错误，请尽快处理！");
            //拼接好发送的内容
            StringBuffer sb=new StringBuffer();
            sb.append("<p><h2>您的类方法路径"+mailWarningBean.getPath()+"出现错误！</h2></p>");
            sb.append("<p>参数列表："+mailWarningBean.getParams()+"</p>");
            sb.append("<p>错误信息：<p style='color:red;'>"+mailWarningBean.getErrorMsg()+"。</p>");
            sb.append("请尽快处理。</p><p>出现错误服务器ip地址：<strong>XXX.XXX.XX</strong></p>");
            msgHelper.setText(sb.toString(),true);
            javaMailSender.send(mimeMessage);   //进行发送
            log.info("发送邮件成功");
        }catch(Exception e){
            log.error("发送告警邮件出现错误，错误原因："+ Throwables.getStackTraceAsString(e));
        }
    }

}
