package com.gritacademy.annotations;

import java.lang.annotation.*;

@Documented //documenterar våra custom annotations
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
public @interface metaInfo {
    String info() default "någon info";

}
