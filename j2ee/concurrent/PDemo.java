package concurrent;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;

import java.util.concurrent.Phaser;

public class PDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);

        System.out.println(" starting ....");

        new Worker(phaser, "Fuwuyuan").start();
        new Worker(phaser, "Chushi").start();
        new Worker(phaser, "Shangcaiyuan").start();

        for (int i = 1; i <= 3; i++) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("Order " + i + " finished!");
        }

        phaser.arriveAndDeregister();
        System.out.println("All done!");
    }
}

class Worker extends Thread {
    private Phaser phaser;
    public Worker (Phaser phaser, String name) {
        this.setName(name);
        this.phaser = phaser;
        phaser.register();
    }

    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println("current order is: " + i + " : " + getName());
            if (i == 3) {
                phaser.arriveAndDeregister();
            } else {
                phaser.arriveAndAwaitAdvance();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
