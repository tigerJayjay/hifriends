package com.easybug.annocation;

import java.lang.annotation.*;

/**
 * 登陆校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}