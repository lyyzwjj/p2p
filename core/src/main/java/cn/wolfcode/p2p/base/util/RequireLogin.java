package cn.wolfcode.p2p.base.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 当前注解能够贴在方法上
@Retention(RetentionPolicy.RUNTIME) // 当前注解能够保存到运行时期
public @interface RequireLogin {
}
