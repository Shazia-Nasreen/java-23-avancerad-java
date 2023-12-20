package com.gritacademy.annotations;

@metaInfo(info="custom class")
/**
 * @see just a generic human class
 */
public class Human {

    @metaInfo(info="min ålder" )
    /**
     * @apiNote use it instead of getAge()
      */
    public int age = 31;
    private String name;
    @metaInfo(info="construtor")
    /**
     * @param age ålder för människan
     */
    Human( @metaInfo(info=" ålder parameter") int age  ){

    }

    @metaInfo(info="sätter namnet på personen")
    public void setName( @metaInfo(info=" ålder parameter") String name) {
        this.name = name;
    }

    /**
     * @deprecated use the field age
     * @return age, ålder för människan i integer
     * Use {@link String#toLowerCase()} for conversion to lower case alphabets.
     */
    public int getAge(){
        return age;
    }
}
