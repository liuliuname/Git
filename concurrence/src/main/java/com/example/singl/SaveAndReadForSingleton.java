package com.example.singl;

import java.io.*;

public class SaveAndReadForSingleton {

    public static void main(String[] args) {
       // InteriorSingleExpale singleton = InteriorSingleExpale.getInstance();
        IdlerSingleExaple singleton = IdlerSingleExaple.getInstance1();

        File file = new File("MySingleton.txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(singleton);
            fos.close();
            oos.close();
            System.out.println(singleton.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            IdlerSingleExaple rSingleton = (IdlerSingleExaple) ois.readObject();
            fis.close();
            ois.close();
            System.out.println(rSingleton.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
