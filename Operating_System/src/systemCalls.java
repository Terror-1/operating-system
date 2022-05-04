import java.io.IOException;
import java.util.*;


public class systemCalls {
	codeParser cp;
	mutexes mutex;
	Queue<process> RQ ;
	Queue<process> generalBlocked;
	public systemCalls(Queue<process> blockedQueue ,Queue<process> readyQueue) {
	this.cp=new codeParser();
	generalBlocked = blockedQueue;
	this.RQ=readyQueue;
	this.mutex = new mutexes(generalBlocked , RQ);
	}
	
	
  public void executeInstruction2(String arg1,String arg2,process p) {
	  switch(arg1) {
	  case "print":  cp.print(arg2, p);break;
	  case "semWait": mutex.semWait(arg2, p);break;
	  case "semSignal" : mutex.semSignal(arg2, p.getPid());break;
	  default :break;
	  } 
  }
  public void executeInstruction3(String arg1,String arg2,String arg3,process p) {
	  switch(arg1) {
	  case "assign" : cp.assign(arg2, arg3, p);break;
	  case "writeFile" : cp.writeFile(arg2, arg3, p);break;
	  case "printFromTo" : cp.printFromTo(arg2, arg3, p);break;
	  default : break;
	  }
	    
  }
  public String executeSpecialInstruction(String arg1,String arg2,process p) throws IOException {
	 return cp.readFile(arg2, p);
  }
}
