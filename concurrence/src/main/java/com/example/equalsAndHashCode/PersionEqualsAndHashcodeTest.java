package com.example.equalsAndHashCode;

public class PersionEqualsAndHashcodeTest {

    public static void main(String[] args) {
        Person p1 = new Person("aaa",12);
        Person p2 = new Person("aaa",12);
        System.out.println(p1.equals(p2)+"----hashcode"+p1.hashCode()+"--"+p2.hashCode());
    }

}
