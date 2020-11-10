import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * created by xiayiyang on 2020/11/9
 * 01 futureTask
 */
public class ReturnValueToMain01 {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        FutureTask futureTask = new FutureTask<CallableThread>(new CallableThread());
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            Object result = futureTask.get();
            System.out.println("异步计算结果为："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }


    static class CallableThread implements Callable {

        public Integer call() throws Exception {
            System.out.println("task started!");
            int sum = fibo(36);
            System.out.println("task end!");
            return sum;
        }



        private  int fibo(int a) {
            if ( a < 2) {
                return 1;
            }
            return fibo(a-1) + fibo(a-2);
        }
    }
}
