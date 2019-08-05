package com.spring.mail.aop;

import com.google.common.base.Throwables;
import com.spring.mail.bean.MailWarningBean;
import com.spring.mail.event.MyEvent;
import com.spring.mail.event.listener.MailListener;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MailAspect {

    @Autowired
    private MailListener mailListener;

    //定义切面
    @Pointcut("execution(public * com.spring.mail.controller.*.*(..))")
    public void MailAspect(){}


    /**
     * 使用aop的异常增强方式来发送邮件
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "MailAspect()",throwing = "e")
    public void deAfterThrowing(JoinPoint joinPoint,Throwable e){
        Object[] param=joinPoint.getArgs(); //获取参数列表
        StringBuilder sb=new StringBuilder();
        for(Object obj:param){
           sb.append(obj+",");
        }
        MailWarningBean mailWarningBean=new MailWarningBean();
        mailWarningBean.setErrorMsg(Throwables.getStackTraceAsString(e));
        mailWarningBean.setParams(sb.delete(0,sb.length()).toString());
        mailWarningBean.setPath(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()); //获取方法路径
        MyEvent<MailWarningBean> myEvent=new MyEvent<>(this,mailWarningBean);
        mailListener.sendMail(myEvent);
    }
}
