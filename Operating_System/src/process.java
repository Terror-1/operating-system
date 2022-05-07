import java.util.*;

public class process {
    private int pid;
	private int timeOfArrival;
	private processStatus currentStatus;
	private boolean addedFlag=false;
	boolean finshed=false;
	Hashtable<String, String> variables;
	Queue<Stack<String>> instructions;
	public process(int pid ,int timeOfArival, processStatus e) {
		this.pid=pid+1;
		this.instructions=new LinkedList<>();
		this.timeOfArrival=timeOfArival;
		this.currentStatus=e;
		this.variables=new Hashtable<>();
		
	}
	public String toString() {
		return "process "+this.pid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public boolean getaddedFlag() {
		return addedFlag;
	}
	public void setaddedFlag(boolean x) {
		this.addedFlag = x;
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
