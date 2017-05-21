package concurrent;

import java.util.concurrent.Exchanger;

public class EDemo {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();
        new A(ex).start();
        new B(ex).start();
    }
}

class A extends Thread {
    private Exchanger<String> ex;
    public A(Exchanger<String> ex) {
        this.ex = ex;
    }

    public void run() {
        String str = null;
        try {
            str = ex.exchange("Hello?");
            System.out.println(str);

            str = ex.exchange("A");
            System.out.println(str);

            str = ex.exchange("B");
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class B extends Thread {
    private Exchanger<String> ex;
    public B(Exchanger<String> ex) {
        this.ex = ex;
    }

    public void run() {
        String str = null;
        try {
            str = ex.exchange("Hi!");
            System.out.println(str);

            str = ex.exchange("1");
            System.out.println(str);

            str = ex.exchange("2");
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}