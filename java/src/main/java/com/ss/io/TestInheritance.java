package com.ss.io;

import java.sql.SQLOutput;

public class TestInheritance {
    public static void main(String[] args) {
        new Child();
    }
}
class Parent{
    public int Pvar;
    Parent(){
        System.out.println("Parent");
        temp();
        System.out.println("Pvar");
    }
    public void temp(){
        Pvar = 1111;
        System.out.println("Parent.temp");
    }
}

class Child extends Parent{
    int Cvar = 2222;
    Child(){
        System.out.println("Child");
        temp();
        System.out.println("Cvar");
    }

    public void temp(){
        System.out.println("Child.temp");
    }
}
