import java.io.*;
import java.util.*;

public class OS {
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	process[] processesInMem;
	codeParser parser;
	executer executer;
	private int Counter;
	private Hashtable<Integer, String> programs; // integer >> time of arrival -- string >> program src
	private Set<String> ourInstruction = new HashSet<String>();
	private int timeSlice;
	private int clk;
	private int totaNumOfProcesses = 3;
	private int numOfFinshed = 0;
	private Set<Integer> inMem = new HashSet<Integer>();
	memory memory;
	systemCalls tempCalls;
	private int globalPointer = 0;
	private boolean EmptyDisk=false;
	private final int id_offset = 0,pc_offset = 2,state_offset = 1,mem_bound_offset = 3,var_offset = 4,inst_offset = 7;

	public OS() {

		this.parser = new codeParser();
		this.programs = new Hashtable<>();
		this.Counter = 1;
		this.readyQueue = new LinkedList<>();
		this.blockedQueue = new LinkedList<>();
		this.processesInMem = new process[2];
		this.memory = new memory();
		this.executer = new executer(blockedQueue, readyQueue, memory);
		this.tempCalls = new systemCalls();
		this.timeSlice = 2;
		this.clk = 0;
		this.ourInstruction.add("print");
		this.ourInstruction.add("assign");
		this.ourInstruction.add("printFromTo");
		this.ourInstruction.add("semWait");
		this.ourInstruction.add("semSignal");
		this.ourInstruction.add("readFile");
		this.ourInstruction.add("WriteFile");

	}

	public void partions(String program, int id) throws IOException {
		PCB pcb = new PCB(id, processStatus.READY);
		process p = new process(id, this.clk, pcb);
		int startBound = 0;
		int endBound = 0;
		// check for place to insert in
		if (this.memory.getMemory()[0] == null) {
			endBound = 19;
			globalPointer=20;

		} else if (this.memory.getMemory()[20] == null) {
			startBound = 20;
			endBound = 39;
			globalPointer=Integer.MAX_VALUE;
		}
		// swap one of the processes
		else {
			String toDisk = unloadFromMemory();
			this.tempCalls.writeFile("Disk", toDisk);
			this.EmptyDisk=false;
			startBound = globalPointer;
			endBound = globalPointer + 19;
			globalPointer=Integer.MAX_VALUE;

		}
		pcb.setStartBound(startBound);
		pcb.setEndBound(endBound);
		inMem.add(pcb.getId());
		this.readyQueue.add(p);
		// writing process to memory
		// 1 )pcb an var space
		this.writePcbToMemory(pcb);
		// 2)instruction
		parser.parseInput(programs.get(clk), pcb, memory);
		// printing
		System.out.println("--" + program.substring(4, program.length() - 4) + " becomes a process--");
		System.out.println("************ Process " + p.getPid() + " is loaded to Memory ************ ");
	}

	public void writePcbToMemory(PCB pcb) {
		int StartBound = pcb.getStartBound();
		memory.getMemory()[StartBound] = pcb.getId();
		memory.getMemory()[StartBound + state_offset] = processStatus.READY;
		memory.getMemory()[StartBound + pc_offset] = pcb.getPc();
		memory.getMemory()[StartBound + mem_bound_offset] = "Start Bound is " + pcb.getStartBound() + " End Bound is "
				+ pcb.getEndBound();
		memory.getMemory()[StartBound + var_offset] = "empty variable space";
		memory.getMemory()[StartBound + var_offset + 1] = "empty variable space";
		memory.getMemory()[StartBound + var_offset + 2] = "empty variable spcae";
	}

	public void checkArrival() throws IOException {
		if (programs.containsKey(clk)) {
			partions(programs.get(clk), Counter);
			programs.remove(clk);
			Counter++;
		}
	}

	public void scheduler() throws IOException {
		System.out.println("<<<  The scheduler starts  >>>");
		while (this.numOfFinshed != totaNumOfProcesses) {
			checkArrival();
			if (!readyQueue.isEmpty()) {
				process temp = this.readyQueue.poll();
				boolean found = this.isInMemory(temp);
				if(!found) swap();
				System.out.println("process " + temp.getPid() + " is chosen from readyQueue by the scheduler  ");
				int pos = this.getPos(temp);
				temp.pcb.setState(processStatus.Running);
				this.memory.getMemory()[pos+state_offset] = processStatus.Running;
				int pc=0;
				for (int i = 0; (i < timeSlice) && (!temp.pcb.getState().equals(processStatus.FINISHED))&& (!temp.pcb.getState().equals(processStatus.BLOCKED)); i++) {
					System.out.println("process " + temp.getPid() + " is currently executing");
					pc = Integer.parseInt(this.memory.getMemory()[pos+pc_offset]+"");
					checkArrival();
					//this.memory.print();
					Stack<String> current = (Stack<String>) (this.memory.getMemory()[pc + inst_offset+pos]);
					String temp1 = current.pop();
					if (temp1.equals("input")) {
						System.out.println("Process " + temp.getPid() + " is taking input ");
						current.push(executer.callTakeInput());
					} else {
						if (this.ourInstruction.contains(current.peek())) {
							String temp2 = current.pop();
							if (temp2.equals("readFile")) {
								if (!current.peek().isEmpty())
									current.push(executer.executeSpecialInstruction(temp2, temp1, temp));
							}

							else {
								executer.executeInstruction2(temp2, temp1, temp);
								if (current.isEmpty()) {
									pc++;
									temp.getPcb().setPc(pc);
									this.memory.getMemory()[pos+pc_offset] = pc;
								}

							}
						} else {
							String temp2 = current.pop();
							String temp3 = current.pop();
							executer.executeInstruction3(temp3, temp2, temp1, temp);
							if (current.isEmpty()) {
								pc++;
								temp.getPcb().setPc(pc);
								this.memory.getMemory()[pos+pc_offset] = pc;

							}

						}
					}
					//this.memory.print();
					clk++;
					if (this.memory.getMemory()[pc+inst_offset+pos]==null) {
						pc = Integer.MAX_VALUE;
					}
					if (pc==Integer.MAX_VALUE) {
						this.numOfFinshed++;
						temp.getPcb().setState(processStatus.FINISHED);
						this.unloadFinished(temp);
						this.swap();
						System.out.println(" $$$ process " + temp.getPid() + " is Finished $$$");
					}

					System.out.println("readyQueue > " + this.readyQueue);
					System.out.println("blocedQueue > " + this.blockedQueue);
					this.memory.print();
					System.out.println("*** Clock Time is " + (clk - 1) + " ***");
					System.out.println("--------------------------------------------------------");

				}
				if (!temp.getPcb().getState().equals(processStatus.BLOCKED) &&!(pc==Integer.MAX_VALUE) ) {
					this.readyQueue.add(temp);
					temp.getPcb().setState(processStatus.READY);
					this.memory.getMemory()[pos+state_offset] = processStatus.READY;
					System.out.println("process " + temp.getPid() + " return back from running to ready queue");
				}
			}

			else {
				checkArrival();
				clk++;
				System.out.println("readyQueue > " + this.readyQueue);
				System.out.println("blocedQueue > " + this.blockedQueue);
				System.out.println("*** Clock Time is " + (clk - 1) + " ***");
				System.out.println("--------------------------------------------------------");

			}
		}
		System.out.println("<<<  All the processes have finished  >>>");
		this.memory.print();
	}
	
    public boolean isInMemory(process p) {
    	return inMem.contains(p.getPid());
    }
	public void swap() throws IOException {
		if (this.memory.isFull()) {
			this.Reload();
		} else if (this.memory.isNotFull()) {
			this.load(globalPointer);
		}

	}

	public void Reload() throws IOException {
		String toDisk = unloadFromMemory();
		load(globalPointer);
		this.tempCalls.writeFile("Disk", toDisk);
		this.EmptyDisk=false;
		System.out.println("************ Process " + toDisk.charAt(0) + " is written on Disk ************ ");

	}

	public void load(int loadPos) throws IOException {
		int endBound = loadPos+19;
		if(!EmptyDisk) {
		this.parser.parseInputString("src/Disk.txt", loadPos, memory);
		this.memory.getMemory()[loadPos+3] = "Start Bound is " + loadPos
				+ " End Bound is " + endBound;
		inMem.add(Integer.parseInt(this.memory.getMemory()[loadPos]+""));
	
		
		System.out.println("************ Process " + Integer.parseInt(this.memory.getMemory()[loadPos]+"") + " is loaded to Memory ************ ");
		this.globalPointer=Integer.MAX_VALUE;
		this.tempCalls.writeFile("Disk", "EMPTY");
		this.EmptyDisk=true;
		}
	}

	public String unloadFromMemory() throws IOException{
		String pushDisk ="";
		int startBound=0;
		if (!this.memory.getMemory()[state_offset].equals(processStatus.Running))  startBound=0;
		else startBound = 20;
		inMem.remove(Integer.parseInt(this.memory.getMemory()[startBound]+""));
		System.out.println("************ Process "+this.memory.getMemory()[startBound+id_offset]+" is swaped from  Memory ************");
		for (int i = startBound; i<= startBound+19&&this.memory.getMemory()[i]!=null; i++) {
					if (i>=startBound+inst_offset) {
						@SuppressWarnings("unchecked")
						Stack<String> y = (Stack<String>)this.memory.getMemory()[i];
						pushDisk+=printStack(y);
					}
					else {
						if (i==startBound+state_offset) {
							if(this.memory.getMemory()[i].equals(processStatus.READY))pushDisk+="STATUS IS READY"+"\n";
							else pushDisk+="STATUS IS BLOCKED"+"\n";
						}
						else pushDisk+= this.memory.getMemory()[i]+"\n";
					}
					
					this.memory.getMemory()[i]=null;
				}
                this.globalPointer=startBound;	
			

	   return pushDisk;

	}

	public void unloadFinished(process p) throws IOException {
		int startBound=0;
		if (this.memory.getMemory()[id_offset]!=null&&p.getPid()==Integer.parseInt(this.memory.getMemory()[id_offset]+"")) startBound=0;
		else startBound=20;
		inMem.remove(Integer.parseInt(this.memory.getMemory()[startBound]+""));
		for (int i = startBound; i <=startBound+19; i++) {
			this.memory.getMemory()[i] = null;
		}
		this.globalPointer=startBound;
	}
	public int getPos(process p) {
		
		if (this.memory.getMemory()[id_offset]!=null&&p.getPid()==Integer.parseInt(this.memory.getMemory()[id_offset]+"")) return 0;
		else return 20;
		
	}
	public static String printStack(Stack<String> x) {
		String temp = "";
		while (!x.isEmpty()) {
			temp = x.pop() + " " + temp;
		}
		temp += "\n";
		return temp;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public int getClk() {
		return clk;
	}

	public void setClk(int clk) {
		this.clk = clk;
	}

	public int getTotaNumOfProcesses() {
		return totaNumOfProcesses;
	}

	public void setTotaNumOfProcesses(int totaNumOfProcesses) {
		this.totaNumOfProcesses = totaNumOfProcesses;
	}

	public int getNumOfFinshed() {
		return numOfFinshed;
	}

	public void setNumOfFinshed(int numOfFinshed) {
		this.numOfFinshed = numOfFinshed;
	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		OS operatingSystem = new OS();
		String program1 = "src/Program_1.txt";
		String program2 = "src/Program_2.txt";
		String program3 = "src/Program_3.txt";
		System.out.println("please enter time slice value");
		operatingSystem.setTimeSlice(sc.nextInt());
		System.out.println("please enter time arrival of first program");
		operatingSystem.programs.put(sc.nextInt(), program1);
		System.out.println("please enter time arrival of second program");
		operatingSystem.programs.put(sc.nextInt(), program2);
		System.out.println("please enter time arrival of third program");
		operatingSystem.programs.put(sc.nextInt(), program3);
		operatingSystem.scheduler();
	}
}
