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
	public interpruter() {
		this.mut = new mutexes(this.blockedQueue);
		this.parser=new codeParser();
		this.timeOfArrival=new int[totaNumOfProcesses];
		this.readyQueue = new LinkedList<>();
		this.blockedQueue= new LinkedList<>();
		this.processes=new ArrayList<>();
		this.timeSlice=2;
		this.clk=0;
		
	}
	public void programToprocess(ArrayList<String> programs) {
		for(int i=0;i<programs.size();i++) {
			process p = new process(i,this.timeOfArrival[i], processStatus.NEW);
			this.processes.add(p);
			
		}
		this.scheduler();
	}
	public void checkArrival() {
		for(int i =0 ; i <this.processes.size();i++) {
			if (this.processes.get(i).getTimeOfArrival()==clk)this.readyQueue.add(this.processes.get(i));
		}
	}
	public void scheduler() {
		System.out.println("OS starts the scheduler");
		checkArrival();
		process temp = this.readyQueue.poll();
		System.out.println("process"+temp.getPid()+"is chosen");
		System.out.println("process"+temp.getPid()+"is currently executing");
		temp.setCurrentStatus(processStatus.Running);
			
			
		
		
	}
	
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	public static void main(String[] args) {
		interpruter inter = new interpruter();
		String program1="src/Program_1.txt";
		String program2="src/Program_2.txt";
		String program3="src/Program_3.txt";
		ArrayList<String> programs = new ArrayList<>();
		inter.timeOfArrival[0]=0;
		inter.timeOfArrival[1]=2;
		inter.timeOfArrival[2]=4;
		programs.add(program1);
		programs.add(program2);
		programs.add(program3);
		inter.programToprocess(programs);

		
	}

}
