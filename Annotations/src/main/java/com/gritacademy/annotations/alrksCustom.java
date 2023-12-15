package com.gritacademy.annotations;

public @interface alrksCustom {

    int age();
    String name() default "Alrik";

}
