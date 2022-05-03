import java.util.*;

public class process {
    private int pid;
	private int timeOfArrival;
	private processStatus currentStatus;
	Hashtable<String, String> variables;
	Queue<Stack<String>> instructions;
	public process(int pid ,int timeOfArival, processStatus e) {
		this.pid=pid;
		this.instructions=new LinkedList<>();
		this.timeOfArrival=timeOfArival;
		this.currentStatus=e;
		this.variables=new Hashtable<>();
		
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getTimeOfArrival() {
		return timeOfArrival;
	}
	public void setTimeOfArrival(int timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}
	
	public processStatus getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(processStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
}
