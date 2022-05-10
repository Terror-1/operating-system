import java.io.IOException;
import java.util.Queue;

public class executer {
	systemCalls systemCalls ;
	mutexes mutex ;
	public executer(Queue<process> blockedQueue ,Queue<process> readyQueue) {
		this.mutex=new mutexes(blockedQueue, readyQueue);
		this.systemCalls = new systemCalls();
	}
	  public void executeInstruction2(String arg1,String arg2,process p) {
		  switch(arg1) {
		  case "print":  {
			  System.out.println("process "+p.getPid()+" is performing a printing instruction of "+arg2);
			  systemCalls.print(arg2, p);
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
			  assign(arg2, arg3, p);
			  break;}
		  case "writeFile" : {
		  System.out.println("process "+p.getPid()+" is performing a writeFile instruction ");
		  systemCalls.writeFile(arg2, arg3, p);
		  break;
		  }
		  
		  case "printFromTo" : {
		  System.out.println("process "+p.getPid()+" is printing from "+ arg2 +" to "+arg3);
		  systemCalls.printFromTo(arg2, arg3, p);
		  break;}
		  default : break;
		  }
		    
	  }
	  public String executeSpecialInstruction(String arg1,String arg2,process p) throws IOException {
		 System.out.println("process "+p.getPid()+" reading file "+arg2 );
		 return systemCalls.readFile(arg2, p);
	  }
	  public String callTakeInput() {
		  return systemCalls.takeInput();
	  }
	  public void assign(String arg1 ,String arg2,process p){
			if (p.variables.containsKey(arg2)) {
				arg2 =systemCalls.readFromMemory(arg2, p);
				systemCalls.writeToMemory(arg1, arg2, p);
				System.out.println("assigned "+ arg2 +" to variable "+arg1+" succesfully");

			}
			else {
				systemCalls.writeToMemory(arg1, arg2, p);
				System.out.println("assigned "+ arg2 +" to variable "+arg1+" succesfully");
			}
		}

}
