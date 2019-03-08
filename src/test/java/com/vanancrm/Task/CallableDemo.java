package com.vanancrm.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class CallableDemo {
   public static void main(String[] args) throws InterruptedException, ExecutionException {
	   ExecutorService service =  Executors.newSingleThreadExecutor();
	   SumTask sumTask = new SumTask(20);
	   Future<Integer> future = service.submit(sumTask);
	   Integer result = future.get();
	   System.out.println(result);
   }
}
class SumTask implements Callable<Integer> {
	private int num = 0;
        public SumTask(int num){
   	        this.num = num;
        }
	@Override
	public Integer call() throws Exception {
		int result = 0;
		for(int i=1;i<=num;i++){
			result+=i;
		}
		return result;
	}
} 