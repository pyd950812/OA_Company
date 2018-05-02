import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author pengyd
 * @Date 2018/4/26 10:10
 * @function:
 */
public class Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        java.util.concurrent.Callable callable = new java.util.concurrent.Callable() {
            @Override
            public Object call() throws Exception {
                int i ;
                for( i = 0; i<100 ; i++){
                    System.out.println(i);
                }
                return i;
            }
        };

        FutureTask futureTask = new FutureTask(callable);
        new Thread(futureTask).start();
        try {
            Thread.sleep(5000);
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reflect() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> test = Class.forName("test");
        Object o = test.newInstance();

    }


}
