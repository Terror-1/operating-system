import java.io.*;
import java.util.*;
public class OS {
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	ArrayList<process> processes;
	codeParser parser;
	executer executer;
	private int Counter ;
	private Hashtable<Integer, String> programs; //integer >> time of arrival -- string >> program src
	private Set<String> ourInstruction = new HashSet<String>();
	private int timeSlice;
	private int clk;
	private int totaNumOfProcesses=3;
	private int numOfFinshed=0;
	public OS() {

		this.parser=new codeParser();
		this.programs=new Hashtable<>(); 
		this.Counter=1;
		this.readyQueue = new LinkedList<>();
		this.blockedQueue= new LinkedList<>();
		this.processes=new ArrayList<>();
		this.executer= new executer(blockedQueue, readyQueue);
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
	public void programToprocess(String program,int id) throws IOException {
		process p = new process(id,this.clk, processStatus.READY);
		this.readyQueue.add(p);
		this.processes.add(p);
		parser.parseInput(programs.get(clk), p);
		System.out.println("--"+program.substring(4,program.length()-4) + " becomes a process--");
}
	public void checkArrival() throws IOException {	
		if(programs.containsKey(clk)) {
			programToprocess(programs.get(clk),Counter);
			programs.remove(clk);
			Counter++;
		}
	}
	public void scheduler() throws IOException {
		System.out.println("<<<  The scheduler starts  >>>");
		while(this.numOfFinshed!=totaNumOfProcesses) {
		checkArrival();
		if(!readyQueue.isEmpty()) {
		  process temp = this.readyQueue.poll();
		  System.out.println("process "+temp.getPid()+" is chosen from readyQueue by the scheduler  ");
		  temp.setCurrentStatus(processStatus.Running);
		  for(int i =0 ; (i<timeSlice) &&(!temp.getCurrentStatus().equals(processStatus.FINISHED))&&(!temp.getCurrentStatus().equals(processStatus.BLOCKED)) ;i++) {
			  System.out.println("process "+temp.getPid()+" is currently executing");
			  checkArrival();
			  String temp1 = temp.instructions.peek().pop();
			  if(temp1.equals("input")) {
				  System.out.println("Process "+temp.getPid()+ " is taking input ");
				  temp.instructions.peek().push(executer.callTakeInput())	;	 
				  }
			  else {
			  if(this.ourInstruction.contains(temp.instructions.peek().peek()))
			  {
				  String temp2 = temp.instructions.peek().pop();
				  if(temp2.equals("readFile")) {
					  if(!temp.instructions.peek().isEmpty())
					  temp.instructions.peek().push(executer.executeSpecialInstruction(temp2, temp1, temp));}
				  
				  else {
				  executer.executeInstruction2(temp2,temp1,temp);
				  if(temp.instructions.peek().isEmpty())
					  temp.instructions.poll();
				  }
			  }else {
				  String temp2 = temp.instructions.peek().pop();
				  String temp3 = temp.instructions.peek().pop();
				  executer.executeInstruction3(temp3,temp2,temp1,temp);
				  if(temp.instructions.peek().isEmpty())
					  temp.instructions.poll();

			  }
			  }
			  clk++;
			  if(temp.instructions.isEmpty()) {
				  this.numOfFinshed++;
				  temp.setCurrentStatus(processStatus.FINISHED);
				  System.out.println(" $$$ process " + temp.getPid()+" is Finished $$$");
				  }
		
			  System.out.println("readyQueue > "+this.readyQueue);
			  System.out.println("blocedQueue > "+this.blockedQueue );
			  System.out.println("*** Clock Time is " + (clk-1)+" ***");
			  System.out.println("--------------------------------------------------------");
			  
		}
		  if(!temp.getCurrentStatus().equals(processStatus.BLOCKED)&&(!temp.instructions.isEmpty())) {
				  this.readyQueue.add(temp);
				  temp.setCurrentStatus(processStatus.READY);
				  System.out.println("process "+temp.getPid()+" return back from running to ready queue");
			  } 
		 	}
		  
		else {
			checkArrival();
			clk++;
			System.out.println("readyQueue > "+this.readyQueue);
			System.out.println("blocedQueue > "+this.blockedQueue );
			System.out.println("*** Clock Time is " + (clk-1)+" ***");
			System.out.println("--------------------------------------------------------");
			  
		}
		}
		System.out.println("<<<  All the processes have finished  >>>");

		
	}
	
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	public int getClk() {
		return clk;
	}
	public void setClk(int clk) {
		this.clk = clk;
	}
	public int getTotaNumOfProcesses() {
		return totaNumOfProcesses;
	}
	public void setTotaNumOfProcesses(int totaNumOfProcesses) {
		this.totaNumOfProcesses = totaNumOfProcesses;
	}
	public int getNumOfFinshed() {
		return numOfFinshed;
	}
	public void setNumOfFinshed(int numOfFinshed) {
		this.numOfFinshed = numOfFinshed;
	}
	public int getTimeSlice() {
		return timeSlice;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		OS operatingSystem = new OS();
		String program1="src/Program_1.txt";
		String program2="src/Program_2.txt";
		String program3="src/Program_3.txt";
		System.out.println("please enter time slice value");
		operatingSystem.setTimeSlice(sc.nextInt());
		System.out.println("please enter time arrival of first program");
		operatingSystem.programs.put(sc.nextInt(), program1);
		System.out.println("please enter time arrival of second program");
		operatingSystem.programs.put(sc.nextInt(), program2);	
		System.out.println("please enter time arrival of third program");
		operatingSystem.programs.put(sc.nextInt(), program3);		
		operatingSystem.scheduler();
	}
}
