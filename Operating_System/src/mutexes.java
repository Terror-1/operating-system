import java.util.LinkedList;
import java.util.Queue;

public class mutexes {
	Queue<Integer> blockedOfUserInput;
	Queue<Integer> blockedOfUserOutput;
	Queue<Integer> blockedOfFile;
	boolean userInput;
	boolean userOutput;
	boolean file;
	int currentRunning[];
	
	public mutexes() {
		this.blockedOfFile= new LinkedList<>();
		this.blockedOfUserInput=new LinkedList<>();
		this.blockedOfUserInput= new LinkedList<>();
		this.userInput=true;
		this.userOutput=true;
		this.file=true;
		currentRunning= new int[3];
		//0 --> process running over userInput
		//1 --> process running over userOutput
		//2 -->process running over File
				
	}
	public void semWait(String arg,int pid) {
		switch (arg) {
		case "userInput":{
			if(!userInput)blockedOfUserInput.add(pid);
			else {
				currentRunning[0]=pid;
				userInput=false;
			}
			
		}
			
		case "userOutput":{
			if(!userOutput)blockedOfUserOutput.add(pid);
			else {
				currentRunning[1]=pid;
				userOutput=false;
			}
			
		}
		case "file":	
			if(!file)blockedOfFile.add(pid);
			else {
				currentRunning[2]=pid;
				file=false;
		}
		}
	}
	public void semSignal(String arg , int pid){
		if(pid==currentRunning[0]&&arg.equals("userInput")) {
			if(!blockedOfUserInput.isEmpty()) currentRunning[0]=pid;
			else userInput=true;
		}
		else if(pid==currentRunning[1]&&arg.equals("userOutput")){
			if(!blockedOfUserOutput.isEmpty()) currentRunning[1]=pid;
			else userOutput=true;
		}
		else if (pid==currentRunning[2]&&arg.equals("file")) {
			if(!blockedOfFile.isEmpty()) currentRunning[2]=pid;
			else file=true;
		}
		
	}
}
