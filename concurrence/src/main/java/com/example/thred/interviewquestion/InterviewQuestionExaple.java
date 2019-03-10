package com.example.thred.interviewquestion;


/**
 * 面试题1:编写程序实现,子线程循环10次,接着主线程循环20次,接着再子线程循环10次,主线程循环20次,如此反复,循环50次.
 */
public class InterviewQuestionExaple{

    public static void main(String[] args) {
        final Function f = new Function();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i< 50;i++){
                      f.subFunction();
                    }
                }
            });
            thread.start();

        for(int j = 0; j<50;j++){
            f.mainFunction();
        }
    }

}



class Function{

    private boolean falg = false;

    public synchronized void subFunction(){
        while(falg){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<10;i++){
            System.out.println("子线程运行i= " +i);
        }
        falg = true;
        this.notify();
    }

    public synchronized void mainFunction(){
        while(!falg){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int j=0;j<20;j++){
            System.out.println("主线程运行j= " +j);
        }
        falg = false;
        this.notify();
    }

}