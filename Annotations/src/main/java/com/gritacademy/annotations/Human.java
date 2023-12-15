package com.gritacademy.annotations;

@metaInfo(info="custom class")
public class Human {

    @metaInfo(info="min ålder" )
    private int age = 31;
    private String name;
    @metaInfo(info="construtor")
    Human( @metaInfo(info=" ålder parameter") int age  ){

    }

    @metaInfo(info="sätter namnet på personen")
    public void setName( @metaInfo(info=" ålder parameter") String name) {
        this.name = name;
    }
}
