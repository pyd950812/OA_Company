

/**
 * @Author pengyd
 * @Date 2018/1/15 17:13
 * @function:
 */
public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        ru ru = new ru();
        ru2 ru2 = new ru2();
        Thread thread1 = new Thread(ru);
        thread1.start();
//        Thread.sleep(2000);
        thread1.interrupt();
        Thread thread2 = new Thread(ru2);
        thread2.start();
        thread2.interrupt();
    }

}
 class ru2 implements Runnable{

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("2");
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(i);
//            Thread.yield();
        }
    }
}


class ru implements Runnable {
    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("1");
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(i);
//            Thread.yield();
        }
    }
}