public class PCB {
    private int id ;
    private int pc;
    private processStatus state;
    private int [] memBound = new int [2];
	public PCB(int id , int pc , processStatus state ,int startBound , int endBound ) {
		this.id=id;
		this.pc = pc;
		this.state=state;
		this.memBound[0]=startBound;
		this.memBound[1]=endBound;
	}
}
