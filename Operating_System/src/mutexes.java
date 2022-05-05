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
		this.blockedOfUserOutput= new LinkedList<>();
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
				System.out.println("process "+p.getPid()+" is blocked over user Input");
				System.out.println("processes that is blocked on userInput >" + this.blockedOfUserInput);
				p.setCurrentStatus(processStatus.BLOCKED);
			}
			else {
				currentOwner[0]=p.getPid();
				userInput=false;
			}
			break;
			
		}
			
		case "userOutput":{
			if(!userOutput) {
				blockedOfUserOutput.add(p);
				this.generalBlocked.add(p);
				System.out.println("process "+p.getPid()+" is blocked over user output");
				System.out.println("processes that is blocked on userOutput >" + this.blockedOfUserOutput);
				p.setCurrentStatus(processStatus.BLOCKED);
				
			}
			else {
				currentOwner[1]=p.getPid();
				userOutput=false;
			}
			break;
			
		}
		case "file":	
			if(!file) {
				blockedOfFile.add(p);
				this.generalBlocked.add(p);
				System.out.println("process "+p.getPid()+" is blocked over file");
				System.out.println("processes that is blocked on File >" + this.blockedOfFile);
				p.setCurrentStatus(processStatus.BLOCKED);
			}
			else {
				currentOwner[2]=p.getPid();
				file=false;
		}
			break;
		
		}
	}
	public void semSignal(String arg , int pid){
		if(pid==currentOwner[0]&&arg.equals("userInput")) {
			if(!blockedOfUserInput.isEmpty()) {
			currentOwner[0]=blockedOfUserInput.peek().getPid();
			RQ.add(blockedOfUserInput.peek());
			System.out.println("process "+blockedOfUserInput.peek().getPid()+" moved from blocled queue >>> ready queue");
			deleteFromQueue(generalBlocked,blockedOfUserInput.poll().getPid() );
			}
			else userInput=true;
		}
		else if(pid==currentOwner[1]&&arg.equals("userOutput")){
			if(!blockedOfUserOutput.isEmpty()) {
				currentOwner[1] =blockedOfUserOutput.peek().getPid();
				RQ.add(blockedOfUserOutput.peek());
				System.out.println("process "+blockedOfUserOutput.peek().getPid()+" moved from blocled queue >>> ready queue");
				deleteFromQueue(generalBlocked,blockedOfUserOutput.poll().getPid() );
			}
			
			else userOutput=true;
		}
		else if (pid==currentOwner[2]&&arg.equals("file")) {
			if(!blockedOfFile.isEmpty()) {
				currentOwner[2]=blockedOfFile.peek().getPid();
				RQ.add(blockedOfFile.peek());
				System.out.println("process "+blockedOfFile.peek().getPid()+" moved from blocled queue >>> ready queue");
				deleteFromQueue(generalBlocked,blockedOfFile.poll().getPid() );
			}
			else file=true;
		}
		
	}
	public static void deleteFromQueue(Queue<process> x,int pid) {
		Queue<process> temp = new LinkedList<>();
		while(!x.isEmpty()) {
			process y = x.poll();
			if(!(y.getPid()==pid))temp.add(y);
		}
		x=temp;
	}
	
}
