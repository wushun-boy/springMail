package com.spring.mail.bean;

import lombok.Data;
import lombok.ToString;

/**
 * 定义发送信息模板
 */
@Data
@ToString
public class MailWarningBean {

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 参数列表
     */
    private String params;

    /**
     * 类路径
     */
    private String path;
}
