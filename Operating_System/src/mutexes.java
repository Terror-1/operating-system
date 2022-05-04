import java.util.LinkedList;
import java.util.Queue;

public class mutexes {
	private Queue<process> blockedOfUserInput;
	private Queue<process> blockedOfUserOutput;
	private Queue<process> blockedOfFile;
	private boolean userInput;
	private boolean userOutput;
	private boolean file;
	private int currentOwner[];
	Queue<process> generalBlocked;
	Queue<process> RQ;
	
	public mutexes(Queue<process> blockedQueue ,Queue<process> readyQueue) {
		this.blockedOfFile= new LinkedList<>();
		this.blockedOfUserInput=new LinkedList<>();
		this.blockedOfUserInput= new LinkedList<>();
		this.userInput=true;
		this.userOutput=true;
		this.file=true;
		currentOwner= new int[3];
		this.RQ=readyQueue;
		//0 --> process running over userInput
		//1 --> process running over userOutput
		//2 -->process running over File
		this.generalBlocked=blockedQueue;
				
	}
	public void semWait(String arg, process p) {
		switch (arg) {
		case "userInput":{
			if(!userInput) {
				blockedOfUserInput.add(p);
				this.generalBlocked.add(p);
				System.out.println("process "+p.getPid()+"is blocked over user Input");
				p.setCurrentStatus(processStatus.BLOCKED);
			}
			else {
				currentOwner[0]=p.getPid();
				userInput=false;
			}
			
		}
			
		case "userOutput":{
			if(!userOutput) {
				blockedOfUserOutput.add(p);
				this.generalBlocked.add(p);
				System.out.println("process "+p.getPid()+"is blocked over user output");
				p.setCurrentStatus(processStatus.BLOCKED);
				
			}
			else {
				currentOwner[1]=p.getPid();
				userOutput=false;
			}
			
		}
		case "file":	
			if(!file) {
				blockedOfFile.add(p);
				this.generalBlocked.add(p);
				System.out.println("process "+p.getPid()+"is blocked over file");
				p.setCurrentStatus(processStatus.BLOCKED);
			}
			else {
				currentOwner[2]=p.getPid();
				file=false;
		}
		}
	}
	public void semSignal(String arg , int pid){
		if(pid==currentOwner[0]&&arg.equals("userInput")) {
			if(!blockedOfUserInput.isEmpty()) {
			currentOwner[0]=blockedOfUserInput.peek().getPid();
			RQ.add(blockedOfUserInput.peek());
			System.out.println("process "+blockedOfUserInput.poll().getPid()+"blocled queue >>> ready queue");
			//remove from the general blocked queue
			}
			else userInput=true;
		}
		else if(pid==currentOwner[1]&&arg.equals("userOutput")){
			if(!blockedOfUserOutput.isEmpty()) {
				currentOwner[1] =blockedOfUserOutput.peek().getPid();
				RQ.add(blockedOfUserOutput.peek());
				System.out.println("process "+blockedOfUserOutput.poll().getPid()+"blocled queue >>> ready queue");
				
			}
			
			else userOutput=true;
		}
		else if (pid==currentOwner[2]&&arg.equals("file")) {
			if(!blockedOfFile.isEmpty()) {
				currentOwner[2]=blockedOfFile.peek().getPid();
				RQ.add(blockedOfFile.peek());
				System.out.println("process "+blockedOfFile.poll().getPid()+"blocled queue >>> ready queue");
				
			}
			else file=true;
		}
		
	}
}
