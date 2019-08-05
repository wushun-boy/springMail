package com.spring.mail.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 定义事件
 * @param <T>
 */
@Data
public class MyEvent<T> extends ApplicationEvent {

    private T t;

    public MyEvent(Object source,T t){
        super(source);
        this.t=t;
    }
}
