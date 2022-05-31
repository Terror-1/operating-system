public class process {
	private int pid;
	private int timeOfArrival;
	private boolean addedFlag=false;
	private int instCount;
	private int spot;
	PCB pcb;
	
	
	public process(int pid ,int timeOfArival,PCB pcb) {
		this.spot=4;
		this.pid=pid;
		this.pcb =pcb;
		this.timeOfArrival=timeOfArival;
	}
	public int getSpot() {
		return spot;
	}
	public void setSpot(int spot) {
		this.spot = spot;
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
