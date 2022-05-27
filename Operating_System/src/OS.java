import java.io.*;
import java.util.*;

public class OS {
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	ArrayList<process> processesInMem;
	codeParser parser;
	executer executer;
	private int Counter;
	private Hashtable<Integer, String> programs; // integer >> time of arrival -- string >> program src
	private Set<String> ourInstruction = new HashSet<String>();
	private int timeSlice;
	private int clk;
	private int totaNumOfProcesses = 3;
	private int numOfFinshed = 0;
	memory memory ;
	disk disk ;

	public OS() {
        
		this.parser = new codeParser();
		this.programs = new Hashtable<>();
		this.Counter = 1;
		this.readyQueue = new LinkedList<>();
		this.blockedQueue = new LinkedList<>();
		this.processesInMem = new ArrayList<>();
		this.executer = new executer(blockedQueue, readyQueue);
		this.memory=new memory();
		this.disk=new disk();
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

	public void programToprocess(String program, int id) throws IOException {
		process p = new process(id, this.clk, processStatus.READY);
		int startBound = 0;
		int endBound = 0;
		/*
		 * if(p.getProcessLengthInMemory()<=memory.getFreeSize()) {
		 * memory.setFreeSize(memory.getFreeSize()-p.getProcessLengthInMemory());
		 * for(int i = 0 ; i<memory.getFreeSpaces().size(); i++) {
		 * if(memory.getFreeSpaces().get(i).getLength()>=p.getProcessLengthInMemory()) {
		 * endBound =memory.getFreeSpaces().get(i).getX()+p.getProcessLengthInMemory();
		 * startBound =memory.getFreeSpaces().get(i).getX();
		 * 
		 * Pair temp = new Pair(endBound+1,memory.getFreeSpaces().get(i).getY());
		 * memory.getFreeSpaces().remove(i); memory.getFreeSpaces().add(i,temp); break;
		 * } } }else {
		 * 
		 * ////////////////////UNLOAD PART////////////////////
		 * 
		 * }
		 */
		p.getPcb().setStartBound(startBound);
		p.getPcb().setEndBound(endBound);
		this.readyQueue.add(p);
		this.processesInMem.add(p);
		this.writePcbToMemory(p);

		parser.parseInput(programs.get(clk), p, memory);
		System.out.println("--" + program.substring(4, program.length() - 4) + " becomes a process--");
	}

	public void writePcbToMemory(process p) {
		memory.getMemory()[p.getPcb().getStartBound()] = p.getPcb().getId();
		memory.getMemory()[p.getPcb().getStartBound() + 1] = processStatus.READY;
		memory.getMemory()[p.getPcb().getStartBound() + 2] = p.getPcb().getPc();
		memory.getMemory()[p.getPcb().getStartBound() + 3] = "Start Bound is " + p.getPcb().getStartBound()
				+ " End Bound is " + p.getPcb().getEndBound();
	}

	public void checkArrival() throws IOException {
		if (programs.containsKey(clk)) {
			programToprocess(programs.get(clk), Counter);
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
				this.isInMemory(temp);
				System.out.println("process " + temp.getPid() + " is chosen from readyQueue by the scheduler  ");
				temp.setCurrentStatus(processStatus.Running);
				for (int i = 0; (i < timeSlice) && (!temp.getCurrentStatus().equals(processStatus.FINISHED))
						&& (!temp.getCurrentStatus().equals(processStatus.BLOCKED)); i++) {
					System.out.println("process " + temp.getPid() + " is currently executing");
					checkArrival();
					String temp1 = temp.instructions.peek().pop();
					if (temp1.equals("input")) {
						System.out.println("Process " + temp.getPid() + " is taking input ");
						temp.instructions.peek().push(executer.callTakeInput());
					} else {
						if (this.ourInstruction.contains(temp.instructions.peek().peek())) {
							String temp2 = temp.instructions.peek().pop();
							if (temp2.equals("readFile")) {
								if (!temp.instructions.peek().isEmpty())
									temp.instructions.peek()
											.push(executer.executeSpecialInstruction(temp2, temp1, temp));
							}

							else {
								executer.executeInstruction2(temp2, temp1, temp);
								if (temp.instructions.peek().isEmpty())
									temp.instructions.poll();
							}
						} else {
							String temp2 = temp.instructions.peek().pop();
							String temp3 = temp.instructions.peek().pop();
							executer.executeInstruction3(temp3, temp2, temp1, temp);
							if (temp.instructions.peek().isEmpty())
								temp.instructions.poll();

						}
					}
					clk++;
					if (temp.instructions.isEmpty()) {
						this.numOfFinshed++;
						temp.setCurrentStatus(processStatus.FINISHED);
						System.out.println(" $$$ process " + temp.getPid() + " is Finished $$$");
					}

					System.out.println("readyQueue > " + this.readyQueue);
					System.out.println("blocedQueue > " + this.blockedQueue);
					System.out.println("*** Clock Time is " + (clk - 1) + " ***");
					System.out.println("--------------------------------------------------------");

				}
				if (!temp.getCurrentStatus().equals(processStatus.BLOCKED) && (!temp.instructions.isEmpty())) {
					this.readyQueue.add(temp);
					temp.setCurrentStatus(processStatus.READY);
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

	}

	public void isInMemory(process p) {
		if (!((int) memory.getMemory()[p.getPcb().getStartBound()] == p.getPcb().getId())) {
			unloadFromMemory(p);
		}
	}

	public void unloadFromMemory(process p) {
		for (int i = 0; i < processesInMem.size(); i++) {
			process temp = processesInMem.get(i);
			if ((temp.getCurrentStatus().equals(processStatus.READY))
					&& (temp.getProcessLengthInMemory() >= p.getProcessLengthInMemory())) {
				ArrayList<Object> pushDisk = new ArrayList<>();
				for (int j = temp.getPcb().getStartBound(); j <= temp.getPcb().getEndBound(); j++) {
					pushDisk.add(i, memory.getMemory()[j]);
				}
				disk.getDisk().add(pushDisk);
				///////////////////////////// check for errors/////////////////////////
				processesInMem.remove(i);
				break;
			}
		}

	}

	public void load(process p) {

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
