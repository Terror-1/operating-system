public class memory {
	private final int memorySize = 40;
	private Object[] memory;

	public memory() {
		this.memory = new Object[memorySize];
	}

	public Object[] getMemory() {
		return memory;
	}

	public void print() {
		System.out.println("****************************** MEMORY ******************************");
		System.out.println("-------------First segmant-------------");
		for(int i=0 ; i<20 ; i++) {
			System.out.print("-> Memory at index "+i +" : ");
			System.out.println(this.memory[i]);
		}
		System.out.println("-------------Second segmant-------------");
		for(int i=20 ; i<memorySize ; i++) {
			System.out.print("-> Memory at index "+i +" : ");
			System.out.println(this.memory[i]);
		}
		System.out.println("-------------END MEMORY-------------");

	}

	public boolean isFull() {
		return this.getMemory()[0] != null && this.getMemory()[20] != null;
	}

	public boolean isNotFull() {
		return this.getMemory()[0] != null || this.getMemory()[20] != null;
	}
}
