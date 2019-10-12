package com.mehedi.user.tvshow.Database;

/**
 * Created by User on 12/13/2018.
 */

public class PojoClass {

    private String name ;
    private  int  id ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PojoClass() {

    }

    public PojoClass(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public PojoClass(String name) {
        this.name = name;
    }
}
