import java.util.*;
public class interpruter {
	mutexes mut ;
	codeParser parser;
	int[]timeOfArrival;
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	int timeSlice;
	int numberOfProcesses=3; // blabizo
	public interpruter() {
		this.mut = new mutexes(this.blockedQueue);
		this.parser=new codeParser();
		this.timeOfArrival=new int[numberOfProcesses];
		this.readyQueue = new LinkedList<>();
		this.blockedQueue= new LinkedList<>();
		this.timeSlice=2;
		
	}
	public void programToprocess(ArrayList<String> programs) {
		for(int i=0;i<programs.size();i++) {
			process p = new process(i+1,this.timeOfArrival[i], processStatus.READY);
			this.readyQueue.add(p);
			
		}
		this.scheduler();
	}
	public void executeInstruction() {
		
	}
	public void scheduler() {
		System.out.println("OS starts the scheduler");
		while(!this.readyQueue.isEmpty()) {
			process temp = this.readyQueue.poll();
			System.out.println("process"+temp.getPid()+"is chosen");
			System.out.println("process"+temp.getPid()+"is currently executing");
			temp.setCurrentStatus(processStatus.Running);
			
			
		}
		
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
