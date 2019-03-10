package com.ss.io;

import java.io.*;

/**
 *
 *测试 测试GIt 提交
 * 字符流和字节流
 * 字符流的由来： 因为数据编码的不同，而有了对字符进行高效操作的流对象。
 * 本质其实就是基于字节流读取时，去查了指定的码表。 字节流和字符流的区别：
 *
 * 读写单位不同：字节流以字节（8bit）为单位，字符流以字符为单位，根据码表映射字符，一次可能读多个字节。
 * 处理对象不同：字节流能处理所有类型的数据（如图片、avi等），而字符流只能处理字符类型的数据。
 * 结论：只要是处理纯文本数据，就优先考虑使用字符流。 除此之外都使用字节流。
 *
 * 输入流和输出流
 * 对输入流只能进行读操作，对输出流只能进行写操作，程序中需要根据待传输数据的不同特性而使用不同的流。
 *
 * IO四大父类  字节流/字符流
 * InputStream
 *    -- FileInputStream
 *    -- ByteArrayInputStream
 *    -- ObjectInputStream
 *    --
 * OutputStream
 *   -- FileOutputStream
 *   -- ByteArrayOutputStream
 *   -- ObjectOutputStream
 *
 *
 * Reader
 *   -- CharArrayReader
 *   -- StringReader
 *   -- InputStreamReader
 *   -- BufferedReader
 * Writer
 *   -- CharArrayWriter
 *   -- StringWriter
 *   -- OutputStreamWriter
 *   -- BufferedWriter
 *
 *Java IO体系中有太多的对象，到底用哪个呢？
 * 明确操作的数据设备。
 * 数据source对应的设备：硬盘(File)，内存(数组)，键盘(System.in)
 * 数据destination对应的设备：硬盘(File)，内存(数组)，控制台(System.out)。
 *
 * 记住，只要一读取键盘录入，就用这句话。
 * BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
 * BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
 */
public class IOExample {


    public static void main(String[] args) {
       int num = 100;
       String str = new String("str1");
       TestParam tp = new TestParam();
       tp.str = new String("class.str1");
        System.out.println(num + "," + str + "," + tp.str);
       test(num,str,tp);
        System.out.println(num + "," + str + "," + tp.str);
    }



    public static void test(int num,String str,TestParam tp){
        num = 200;
        str = "str2";
        tp.str = "class.str2";
    }



    public static void systemin(){
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        int sum = 0;
        while(true){
            try {
                System.out.println("1");
                String s = bufr.readLine();
                System.out.println("2");
                String ss = bufr.readLine();
                boolean flag = s.matches("[0-9]+");
                boolean flag2 = ss.matches("\\d+");
                if(flag && flag2){
                    sum = Integer.parseInt(s) + Integer.parseInt(ss);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bufr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sum);
    }

    public static void writeFile(){
        String path = "d:/0404/a.txt";




        File file = new File(path);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            try {
                outputStream.write("aaaa".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    //递归读取文件夹的文件
    public static void readFile(String path){

        File file = new File(path);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isFile()){
                try {
                    FileInputStream inputStream = new FileInputStream(f);
                    try {
                        int read = inputStream.read();
                        System.out.println(read);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                readFile(f.getPath());
            }
        }
    }


    //拷贝一张图片，从一个目录到另外一个目录下(PS:是拷贝是不是移动)
    public static void copyImg(){

        File file = new File("d:/1.png");
        File toFile = new File("d:/img/1.png");

        try {
            if(toFile.createNewFile()){
                System.out.println("成功");
            }else{
                System.out.println("失败");
            }

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(toFile);
            int len = 0;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) != -1){
                fos.write(buf,0,len);
            }

            fos.flush();
            fos.close();
            fis.close();

        }catch (IOException e){
            e.printStackTrace();
        }


    }


}

class TestParam {
    /**
     *
     */
    String str;
}
