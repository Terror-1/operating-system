public class PCB {
    private int id ;
    private int pc;
    private processStatus state;
    private int startBound;
    private int endBound;
	public PCB(int id , processStatus state) {
		this.id=id;
		this.pc=1;
		this.state=state;

 	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStartBound() {
		return startBound;
	}
	public void setStartBound(int startBound) {
		this.startBound = startBound;
	}
	public int getEndBound() {
		return endBound;
	}
	public void setEndBound(int endBound) {
		this.endBound = endBound;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public processStatus getState() {
		return state;
	}
	public void setState(processStatus state) {
		this.state = state;
	}
	
	
}
