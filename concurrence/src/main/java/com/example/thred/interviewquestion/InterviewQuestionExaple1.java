package com.example.thred.interviewquestion;


/**
 * 面试题2：设计四个线程,其中两个线程每次对变量i加1,另外两个线程每次对i减1.
 */
public class InterviewQuestionExaple1{

    public static void main(String[] args) {
        InterviewQuestionExaple1 interviewQuestionExaple1 = new InterviewQuestionExaple1();

        for(int i = 0;i < 2;i++){
           new Thread(interviewQuestionExaple1.new add()).start();
          new Thread(interviewQuestionExaple1.new sub()).start();
        }
    }


    class add implements Runnable{
        CountFunction countFunction = new CountFunction();
        @Override
        public void run() {
            for(int i =0;i < 10;i++){
                countFunction.addCount();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class sub implements Runnable{
        CountFunction countFunction = new CountFunction();
        @Override
        public void run() {
            for(int i =0;i < 10;i++){
                countFunction.removeCount();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Integer count = 0;
    class CountFunction{

        public synchronized void addCount(){
            count = count+1;
            System.out.println("addCount count值为" + count);
        }

        public synchronized void removeCount(){
            count = count-1;
            System.out.println("removeCount count值为" + count);
        }
    }


}


