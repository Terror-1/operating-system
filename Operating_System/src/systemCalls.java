import java.io.*;
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
	  case "print":  {
		  System.out.println("process "+p.getPid()+" is performing a printing instruction of "+arg2);
		  cp.print(arg2, p);
		  break;}
	  case "semWait": {
		  System.out.println("process "+p.getPid()+" is performing a semWait instruction over "+arg2 );
		  mutex.semWait(arg2, p);
		  break;}
	  case "semSignal" :{ 
		  System.out.println("process "+p.getPid()+" is performing a semSignal instruction over "+arg2 );
		  mutex.semSignal(arg2, p.getPid());
		  break;}
	  default :break;
	  } 
  }
  public void executeInstruction3(String arg1,String arg2,String arg3,process p) throws IOException {
	  switch(arg1) {
	  case "assign" : {
		  System.out.println("process "+p.getPid()+" is performing an assign instruction"  );
		  cp.assign(arg2, arg3, p);
		  break;}
	  case "writeFile" : {
	  System.out.println("process "+p.getPid()+" is performing a writeFile instruction ");
	  cp.writeFile(arg2, arg3, p);
	  break;
	  }
	  
	  case "printFromTo" : {
	  System.out.println("process "+p.getPid()+" is printing from "+ arg2 +" to "+arg3);
	  cp.printFromTo(arg2, arg3, p);
	  break;}
	  default : break;
	  }
	    
  }
  public String executeSpecialInstruction(String arg1,String arg2,process p) throws IOException {
	 System.out.println("process "+p.getPid()+" reading file "+arg2 );
	 return cp.readFile(arg2, p);
  }
  public String takeInput() {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Please enter a value");
	  return sc.next();
  }
  public void writeToMemory(String arg1, String arg2,process p) {
	  p.variables.put(arg1, arg2);
  }
  public String readFromMemory(String key,process p) {
	  return p.variables.getOrDefault(key,"Key not Found");
  }
}
