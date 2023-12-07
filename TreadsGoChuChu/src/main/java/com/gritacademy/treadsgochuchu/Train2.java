package com.gritacademy.treadsgochuchu;

public class Train2 extends Thread {

    Train2() {
        System.out.println("Train created");
    }

    @Override
    public void start() {
        super.start();
    }


    @Override
    public void run() {
        //  super.run();
        try {
            System.out.println("Sleep!!!");
            Thread.sleep(5000);
            System.out.println("GO!!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        System.out.println("STOPP");
    }


}
