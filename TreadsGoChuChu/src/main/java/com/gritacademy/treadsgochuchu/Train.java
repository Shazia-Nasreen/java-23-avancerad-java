package com.gritacademy.treadsgochuchu;

import javafx.scene.control.ProgressBar;

import java.util.Random;

import static com.gritacademy.treadsgochuchu.TreadsApplication.order;

public class Train extends Thread{

    private final Random r;
    ProgressBar pb;
    private int id;
    private float bar=0.0f,speed;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Train(ProgressBar pb) {
        super();
        id = order++;
        r=new Random();
        speed= r.nextFloat(0.001f,0.1f);
        pb.setProgress(0.0);
        this.pb = pb;

    }

    @Override
    public void start() {
        super.start();
        System.out.println("gooo "+id);
    }

    @Override
    public void run() {
        super.run();
        while (bar<1 && !this.isInterrupted()){
            //pb.setProgress(bar+=0.1f);
            pb.setProgress(bar+=speed);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(r.nextInt(100) <5){
                this.interrupt();
                System.out.printf("%d CRASHED!!! at %.2f%c\n",id,bar*100,'%');
                System.out.println();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
