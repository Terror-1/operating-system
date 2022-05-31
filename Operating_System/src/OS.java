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
	systemCalls tempCalls;
	private int globalPointer=0;
	private process unloaded=null;
	

	public OS() {
        
		this.parser = new codeParser();
		this.programs = new Hashtable<>();
		this.Counter = 1;
		this.readyQueue = new LinkedList<>();
		this.blockedQueue = new LinkedList<>();
		this.processesInMem = new ArrayList<>();
		this.memory=new memory();
		this.executer = new executer(blockedQueue, readyQueue,memory);
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
		process p = new process(id, this.clk, processStatus.READY);
		int startBound = 0;
		int endBound = 0;
		if (this.memory.getMemory()[0]==null) {
			endBound=19;
			
		}
		else if (this.memory.getMemory()[20]==null){
			startBound=20;
			endBound=39;
		}
		else {
			String toDisk =unloadFromMemory();
			this.tempCalls.writeFile("Disk", toDisk);
			startBound=globalPointer;
			endBound=globalPointer+19;
			
				
		}
		p.getPcb().setStartBound(startBound);
		p.getPcb().setEndBound(endBound);
		p.setFound(true);
		this.readyQueue.add(p);
		this.processesInMem.add(p);
		this.writePcbToMemory(p);
		parser.parseInput(programs.get(clk), p, memory);
		System.out.println("--" + program.substring(4, program.length() - 4) + " becomes a process--");
		System.out.println("************ Process "+p.getPid()+" is loaded to Memory ************ ");
		this.memory.print();
	}

	public void writePcbToMemory(process p) {
		memory.getMemory()[p.getPcb().getStartBound()] = p.getPcb().getId();
		memory.getMemory()[p.getPcb().getStartBound() + 1] = processStatus.READY;
		memory.getMemory()[p.getPcb().getStartBound() + 2] = p.getPcb().getPc();
		memory.getMemory()[p.getPcb().getStartBound() + 3] = "Start Bound is " + p.getPcb().getStartBound()
				+ " End Bound is " + p.getPcb().getEndBound();
		memory.getMemory()[p.getPcb().getStartBound() + 4] = "empty variable space";
		memory.getMemory()[p.getPcb().getStartBound() + 5] = "empty variable space";
		memory.getMemory()[p.getPcb().getStartBound() + 6] = "empty variable spcae";
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
				this.isInMemory(temp);
				System.out.println("process " + temp.getPid() + " is chosen from readyQueue by the scheduler  ");
				temp.setCurrentStatus(processStatus.Running);
				this.memory.getMemory()[temp.getPcb().getStartBound()+1]=processStatus.Running;
				for (int i = 0; (i < timeSlice) && (!temp.getCurrentStatus().equals(processStatus.FINISHED))
						&& (!temp.getCurrentStatus().equals(processStatus.BLOCKED)); i++) {
					this.memory.print(); 
					System.out.println("process " + temp.getPid() + " is currently executing");
					String string = (this.memory.getMemory()[temp.getPcb().getStartBound()+2])+"";
					int pc = Integer.parseInt(string);
					checkArrival();
					Stack<String> current = (Stack<String>) (this.memory.getMemory()[pc+temp.getPcb().getStartBound()+6]);
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
									this.memory.getMemory()[temp.getPcb().getStartBound()+2]=pc;
								}
								
							}
						} else {
							String temp2 = current.pop();
							String temp3 = current.pop();
							executer.executeInstruction3(temp3, temp2, temp1, temp);
							if (current.isEmpty()) {
								pc++;
								temp.getPcb().setPc(pc);
								this.memory.getMemory()[temp.getPcb().getStartBound()+2]=pc;

							}

						}
					}
					clk++;
					if ((temp.getPcb().getPc()+temp.getPcb().getStartBound()+6)>=7+temp.getPcb().getStartBound()+7) {
						this.numOfFinshed++;
						temp.setCurrentStatus(processStatus.FINISHED);
						this.memory.getMemory()[temp.getPcb().getStartBound()+1]=processStatus.FINISHED;
						this.unloadFinished(temp);
						if (unloaded!=null) {
							load(unloaded);
							unloaded=null;
						}
						System.out.println(" $$$ process " + temp.getPid() + " is Finished $$$");
					}

					System.out.println("readyQueue > " + this.readyQueue);
					System.out.println("blocedQueue > " + this.blockedQueue);
					System.out.println("*** Clock Time is " + (clk - 1) + " ***");
					System.out.println("--------------------------------------------------------");

				}
				if (!temp.getCurrentStatus().equals(processStatus.BLOCKED) && (!((temp.getPcb().getPc()+temp.getPcb().getStartBound()+6)>=temp.getPcb().getStartBound()+14))) {
					this.readyQueue.add(temp);
					temp.setCurrentStatus(processStatus.READY);
					this.memory.getMemory()[temp.getPcb().getStartBound()+1]=processStatus.READY;
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

	public void isInMemory(process p) throws IOException {
		if (this.memory.isFull() && !p.isFound()) {
			this.Reload(p);
		}
		else if  (this.memory.isNotFull()&& !p.isFound()) {
			this.load(p);
			unloaded=null;
		}
		
	}
	public void Reload(process p) throws IOException {
		String toDisk = unloadFromMemory();
		load(p);
		this.tempCalls.writeFile("Disk", toDisk);
		System.out.println("************ Process "+toDisk.charAt(0)+" is written on Disk ************ ");
		
	}

	public  void load(process p) throws FileNotFoundException {
		if (this.memory.getMemory()[0]==null) {
			p.getPcb().setEndBound(19);
			p.getPcb().setStartBound(0);
		}
		else if (this.memory.getMemory()[20]==null) {
			p.getPcb().setEndBound(19);
			p.getPcb().setStartBound(0);
		}
		
		this.parser.parseInputString("src/Disk.txt", p, memory);
		this.memory.getMemory()[3+p.getPcb().getStartBound()]="Start Bound is " + p.getPcb().getStartBound()
				+ " End Bound is " + p.getPcb().getEndBound();
		p.setFound(true);
		this.processesInMem.add(p);
		changeFlag(readyQueue, p);
		changeFlag(blockedQueue, p);
		System.out.println("************ Process "+p.getPid()+" is loaded to Memory ************ ");
	}
	public String unloadFromMemory() throws IOException{
		String pushDisk ="";
		for (int i = 0; i < processesInMem.size(); i++) {
			process temp = processesInMem.get(i);
			if ((temp.getCurrentStatus().equals(processStatus.READY))||temp.getCurrentStatus().equals(processStatus.BLOCKED)) {
				System.out.println("************ Process "+temp.getPid()+" is swaped from to Memory ************");
				for (int j = temp.getPcb().getStartBound(); j <= temp.getPcb().getEndBound()&&this.memory.getMemory()[j]!=null; j++) {
					if (j>temp.getPcb().getStartBound()+7) {
						@SuppressWarnings("unchecked")
						Stack<String> y = (Stack<String>)this.memory.getMemory()[j];
						pushDisk+=printStack(y);
					}
					else {
						if (j==temp.getPcb().getStartBound()+1) {
							pushDisk+="STATUS IS READY \n";
						}
						else pushDisk+= this.memory.getMemory()[j]+"\n";
					}
					
					this.memory.getMemory()[j]=null;
				}
				processesInMem.remove(i);
				temp.setFound(false);
				changeFlag(readyQueue, temp);
				changeFlag(blockedQueue, temp);
				this.globalPointer=temp.getPcb().getStartBound();
				unloaded=temp;
				break;
			}
		}
		return pushDisk;
		
	}
	public void unloadFinished(process p) {
		for(int i=p.getPcb().getStartBound();i<=p.getPcb().getEndBound();i++) {
			this.memory.getMemory()[i]=null;
		}
		for(int i=0 ; i <processesInMem.size();i++) {
			process temp = processesInMem.get(i);
			if(p.getPid()==temp.getPid()) {
				processesInMem.remove(i);
				temp.setFound(false);
				changeFlag(readyQueue, temp);
				changeFlag(blockedQueue, temp);
				unloaded=temp;
				this.globalPointer=temp.getPcb().getStartBound();
			}
		}
		
	}
	public void changeFlag(Queue<process> temp , process p) {
		int size = temp.size();
		for(int i=0 ; i <size;i++) {
			if (p.getPid()==temp.peek().getPid()) {
				temp.peek().setFound(!temp.peek().isFound());
			}
			temp.add(temp.poll());
			
		}
	}
	public static String printStack(Stack<String> x) {
		String temp="";
		while (!x.isEmpty()) {
			temp=x.pop()+" "+temp;
		}
		temp+="\n";
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
