package com.learn.spring.custom.spelresolver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CustomFieldKey {
    String value() default "";

    String[] keys()  default  {};

    int fieldIndex() default -1;
}
