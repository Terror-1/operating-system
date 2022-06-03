public class process {
	private int pid;
	private int spot;
	private boolean checkFlag;
	private PCB pcb;
	
	public process(int pid ,PCB pcb) {
		this.spot=0;
		this.pid=pid;
		this.pcb =pcb;
		this.checkFlag=true;
	}
	public boolean isCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
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
}
