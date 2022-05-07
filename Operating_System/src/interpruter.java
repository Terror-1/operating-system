import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
public class interpruter {
	mutexes mut ;
	codeParser parser;
	int[]timeOfArrival;
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	ArrayList<process> processes;
	int timeSlice;
	int clk;
	int totaNumOfProcesses=3; // blabizo
	int numOfFinshed=0;
	systemCalls tempCalls;
	Set<String> ourInstruction = new HashSet<String>();
	public interpruter() {
		this.parser=new codeParser();
		this.timeOfArrival=new int[totaNumOfProcesses];
		this.readyQueue = new LinkedList<>();
		this.blockedQueue= new LinkedList<>();
		this.processes=new ArrayList<>();
		this.tempCalls=new systemCalls(blockedQueue , readyQueue);
		this.timeSlice=2;
		this.clk=0;
		this.ourInstruction.add("print");
		this.ourInstruction.add("assign");
		this.ourInstruction.add("printFromTo");
		this.ourInstruction.add("semWait");
		this.ourInstruction.add("semSignal");
		this.ourInstruction.add("readFile");
		this.ourInstruction.add("WriteFile");
		
		
	}
	public void readInstructions(String arg,process p) throws FileNotFoundException {
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;
		while(sc.hasNext()) {
			Stack<String> temp = new Stack<>();
			st= sc.nextLine();
			String stringBuilder[] = st.split(" ");
			for(int i = 0 ; i <stringBuilder.length;i++) {
				temp.push(stringBuilder[i]);
			}
			
			p.instructions.add(temp);	
		}
	}
	public void programToprocess(ArrayList<String> programs) throws IOException {
		for(int i=0;i<programs.size();i++) {
			process p = new process(i,this.timeOfArrival[i], processStatus.NEW);
			this.processes.add(p);
			readInstructions(programs.get(i), p);
			
		}
		this.scheduler();
	}
	public void checkArrival() {
		
		for(int i =0 ; i <this.processes.size();i++) {
			if ((this.processes.get(i).getTimeOfArrival()==clk)&&(this.processes.get(i).getaddedFlag()==false)) {
				this.readyQueue.add(this.processes.get(i));
			    this.processes.get(i).setaddedFlag(true);
			//System.out.println("process "+this.processes.get(i).getPid() + "  moved to ready queue");
			}
		}
	}
	public void scheduler() throws IOException {
		System.out.println("<<<  The scheduler starts  >>>");
		while(this.numOfFinshed!=totaNumOfProcesses) {
		checkArrival();

		if(!readyQueue.isEmpty()) {
		  process temp = this.readyQueue.poll();
		  System.out.print("process "+temp.getPid()+" is chosen from readyQueue by the scheduler |||  ");
		  System.out.println("process "+temp.getPid()+" is currently executing");
		  temp.setCurrentStatus(processStatus.Running);
		  for(int i =0 ; (i<timeSlice) &&(!temp.finshed)&&(!temp.getCurrentStatus().equals(processStatus.BLOCKED)) ;i++) {
			  String temp1 = temp.instructions.peek().pop();
			  if(this.ourInstruction.contains(temp.instructions.peek().peek()))
			  {
				  String temp2 = temp.instructions.peek().pop();
				  if(temp2.equals("readFile")) {
					  if(!temp.instructions.peek().isEmpty())
					  temp.instructions.peek().push(tempCalls.executeSpecialInstruction(temp2, temp1, temp));}
				  
				  else {
				  tempCalls.executeInstruction2(temp2,temp1,temp);
				  if(temp.instructions.peek().isEmpty())
					  temp.instructions.poll();
				  }
			  }else {
				  String temp2 = temp.instructions.peek().pop();
				  String temp3 = temp.instructions.peek().pop();
				  tempCalls.executeInstruction3(temp3,temp2,temp1,temp);
				  if(temp.instructions.peek().isEmpty())
					  temp.instructions.poll();

			  }
			  checkArrival();
			  clk++;
			  if(temp.instructions.isEmpty()) {
				  temp.finshed=true;
				  this.numOfFinshed++;
				  temp.setCurrentStatus(processStatus.FINISHED);
				  System.out.println(" $$$ process " + temp.getPid()+" is Finished $$$");
				  }
		
			  System.out.println("readyQueue > "+this.readyQueue);
			  System.out.println("blocedQueue > "+this.blockedQueue );
			  System.out.println("*** Clock Time is " + (clk-1)+" ***");
			  System.out.println("--------------------------------------------------");
			  
		}
		  if(!temp.getCurrentStatus().equals(processStatus.BLOCKED)&&(!temp.instructions.isEmpty())) {
				  this.readyQueue.add(temp);
				  temp.setCurrentStatus(processStatus.READY);
				  System.out.println("process "+temp.getPid()+" return back from running to ready queue");
			  }
		  
			  
		 	}
		else {
			clk++;
			checkArrival();
			  
		}
		}
		System.out.println("<<<  All the processes have finished  >>>");

		
	}
	
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	
	public static void main(String[] args) throws IOException {
		interpruter inter = new interpruter();
		String program1="src/Program_1.txt";
		String program2="src/Program_2.txt";
		String program3="src/Program_3.txt";
		ArrayList<String> programs = new ArrayList<>();
		inter.timeOfArrival[0]=0;
		inter.timeOfArrival[1]=1;
		inter.timeOfArrival[2]=1;
		programs.add(program1);
		programs.add(program2);
		programs.add(program3);
		inter.programToprocess(programs);
		
}
}
