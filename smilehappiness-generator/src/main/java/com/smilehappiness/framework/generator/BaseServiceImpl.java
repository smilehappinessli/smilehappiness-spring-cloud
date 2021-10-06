package com.smilehappiness.framework.generator;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 公用service基类
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/9/27 10:35
 */
//@Service
public class BaseServiceImpl<M extends BasicMapper<T>, T extends BaseVo> extends ServiceImpl<M, T> {


}
