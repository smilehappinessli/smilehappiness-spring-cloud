package com.smilehappiness.framework.test.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 消息服务类
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/7/6 21:33
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessage {

    /**
     * 消息主键id
     */
    private Long id;
    /**
     * 短信模板key
     */
    private String msgKey;
    /**
     * 短信内容
     */
    private String content;

}
   