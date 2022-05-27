import java.util.ArrayList;

public class memory {
	private final int memorySize =40;
	private  Object[] memory;
	ArrayList<Pair> freeSpaces = new ArrayList<>();
	private int freeSize =40;

	public memory() {
		this.memory = new String[40];
		freeSpaces.add(new Pair(0, 40));
	}

	public ArrayList<Pair> getFreeSpaces() {
		return freeSpaces;
	}

	public void setFreeSpaces(ArrayList<Pair> freeSpaces) {
		this.freeSpaces = freeSpaces;
	}

	public Object[] getMemory() {
		return memory;
	}
	public int getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(int size) {
		this.freeSize = size;
	}

}
