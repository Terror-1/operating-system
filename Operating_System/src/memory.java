import java.util.Arrays;

public class memory {
	private final int memorySize =40;
	private  Object[] memory;

	public memory() {
		this.memory = new Object[memorySize];
	}
	public Object[] getMemory() {
		return memory;
	}
	public void print() {
		System.out.println(Arrays.toString(memory));
	}
    public boolean isFull() {
    	return this.getMemory()[0]!=null && this.getMemory()[20]!=null ;
	}
    public boolean isNotFull() {
    	return this.getMemory()[0]!=null ||this.getMemory()[20]!=null ;
	}
}
