import java.util.*;
public class process {
	private int pid;
	private int timeOfArrival;
	private boolean addedFlag=false;
	Hashtable<String, String> variables;
	private int instCount;
	PCB pcb;
	
	
	public process(int pid ,int timeOfArival,PCB pcb) {
		this.pid=pid;
		this.pcb =pcb;
		this.timeOfArrival=timeOfArival;
		this.variables=new Hashtable<>();	
	}
	public int getPid() {
		return this.pid;
	}
	public String toString() {
		return "Process "+this.pid;
	}
	public PCB getPcb() {
		return pcb;
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
	public int getInstCount() {
		return instCount;
	}
	public void setInstCount(int instCount) {
		this.instCount = instCount;
	}
	
}
