import java.util.*;
public class process {
	private int timeOfArrival;
	private boolean addedFlag=false;
	Hashtable<String, String> variables;
	Queue<Stack<String>> instructions;
	private int instCount;
	private PCB pcb;
	
	
	public process(int pid ,int timeOfArival, processStatus e) {
		this.pcb = new PCB(pid,e);
		this.instructions=new LinkedList<>();
		this.timeOfArrival=timeOfArival;
		this.variables=new Hashtable<>();	
	}
	public PCB getPcb() {
		return pcb;
	}
	public void setPcb(PCB pcb) {
		this.pcb = pcb;
	}
	public String toString() {
		return "process "+this.pcb.getId();
	}
	public int getPid() {
		return this.pcb.getId();
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
		return this.pcb.getState();
	}
	public void setCurrentStatus(processStatus currentStatus) {
		this.pcb.setState(currentStatus);
	}
	public int getProcessLengthInMemory() {
		return (3+this.instCount+4);
	}
	public int getInstCount() {
		return instCount;
	}
	public void setInstCount(int instCount) {
		this.instCount = instCount;
	}
	
}
