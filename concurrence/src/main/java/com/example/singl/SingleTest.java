package com.example.singl;

public class SingleTest {


    public static void main(String[] args) {

        for(int i = 0;i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    /*IdlerSingleExaple instance = IdlerSingleExaple.getInstance1();
                    System.out.println(instance.hashCode());*/

                    //System.out.println(BadmashSingleExaple.getInstance().hashCode());

                   // System.out.println(InteriorSingleExpale.getInstance());
                    System.out.println(EnumSigleExaple.getInstance().hashCode());
                }
            }).start();
        }

    }


}
