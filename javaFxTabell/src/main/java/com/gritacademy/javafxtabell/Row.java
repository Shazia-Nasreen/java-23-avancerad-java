package com.gritacademy.javafxtabell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    Row( String f,String s,String age){
        cells.addAll( Arrays.stream(new String[]{f,s,age}).toList());

    }
    public  List<String> cells= new ArrayList<>();
    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }





}
