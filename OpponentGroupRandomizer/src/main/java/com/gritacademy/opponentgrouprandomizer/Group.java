package com.gritacademy.opponentgrouprandomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {

    List<String> members= new ArrayList<>();

    Group(String ...members){
        this.members.addAll(Arrays.asList(members));
    }
    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }


}
