package ru.javawebinar.basejava;

public class DeadLockDemo {
    public static void main(String[] args)  {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Runnable r1 = new MyThread(obj1, obj2);
        new Thread(r1).start();
        Runnable r2 = new MyThread(obj2, obj1);
        new Thread(r2).start();
    }

    public static class MyThread implements Runnable {
        Object objectLock1;
        Object objectLock2;

        public MyThread(Object obj1, Object obj2) {
            objectLock1 = obj1;
            objectLock2 = obj2;
        }

        @Override
        public void run() {
            synchronized (objectLock1) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("Прервано выполнение при блокировке objectLock1");
                }
                synchronized (objectLock2) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Прервано выполнение при блокировке objectLock2");
                    }
                }
            }
        }
    }
}
