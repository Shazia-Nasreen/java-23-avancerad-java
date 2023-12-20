package com.gritacademy.annotations;

import java.lang.annotation.*;

@Documented //dokumenterar våra custom annotations i javadocset


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
public @interface metaInfo {
    String info() default "någon info";

}
