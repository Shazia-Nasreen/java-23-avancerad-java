package com.gritacademy.annotations;

public @interface AlriksCustom {

    int age();
    String name() default "Alrik";

}
