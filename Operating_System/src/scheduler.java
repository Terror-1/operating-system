import java.util.*;

public class scheduler {
  Queue<Integer> readyQueue;
  Queue<Integer> blockedQueue; 
  public scheduler() {
	  this.readyQueue = new LinkedList<>();
	  this.blockedQueue = new LinkedList<>();
  }
  public void runScheduler() {
	  System.out.println("OS starts running the Scheduler");
  }
  public static void main(String[] args) {
	scheduler sc = new scheduler();

    System.out.println(sc.blockedQueue.contains(5));
}
}