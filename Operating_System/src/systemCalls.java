import java.io.*;
import java.util.*;

public class systemCalls {

	public systemCalls() {
	}

	public boolean contains(int varIdx, String arg, memory memory) {
		for (int i = varIdx; i < varIdx + 3; i++) {
			String n[] = (memory.getMemory()[i] + "").split(" ");
			Pair temp = new Pair(n[3], n[n.length - 1]);
			if (temp.getX().equals(arg)) {
				return true;
			}
		}

		return false;
	}

	public String readFromMemoryNum(int varIdx, String arg, memory memory) {
		for (int i = varIdx; i < varIdx + 3; i++) {
			String n[] = (memory.getMemory()[i] + "").split(" ");
			String s = "";
			boolean t = false;

			for (int k = 6; k < n.length; k++) {
				s += n[k];
			}
			// System.out.println(Arrays.toString(n));
			Pair temp = new Pair(n[3], s);
			if (temp.getX().equals(arg)) {

				return temp.getY();
			}
		}

		return "";
	}

	public String readFromMemoryStr(int varIdx, String arg, memory memory) {
		for (int i = varIdx; i < varIdx + 3; i++) {
			String n[] = (memory.getMemory()[i] + "").split(" ");
			String s = "";
			boolean t = false;

			for (int k = 6; k < n.length; k++) {
				s += n[k] + " ";
			}
			// System.out.println(Arrays.toString(n));
			Pair temp = new Pair(n[3], s);
			if (temp.getX().equals(arg)) {

				return temp.getY();
			}
		}

		return "";
	}

	public void print(String arg, int varIdx, memory memory) {
		if (this.contains(varIdx, arg, memory)) {
			System.out.println(readFromMemoryStr(varIdx, arg, memory) + "");
		} else {
			System.out.println("printed value : " + arg + "");
		}

	}

	public String readFile(String arg, int varIdx, memory memory) {
		String filepath;

		if (this.contains(varIdx, arg, memory))
			filepath = System.getProperty("user.dir") + "\\Src\\" + readFromMemoryStr(varIdx, arg, memory) + ".txt";
		else
			filepath = arg;
		File file = new File(filepath);
		Scanner sc;
		String st = "";
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				st += sc.nextLine() + "\n";

			}
		} catch (FileNotFoundException e) {
			System.out.println(" Warning!!! File not found 404 ");
			st += "File not Found";
		}
		return st;
	}

	public void writeFile(String arg1, String arg2, int varIdx, memory memory) throws IOException {
		String fileName;
		if (this.contains(varIdx, arg1, memory))
			fileName = readFromMemoryStr(varIdx, arg1, memory);
		else
			fileName = arg1;
		String text;
		if (this.contains(varIdx, arg2, memory))
			text = readFromMemoryStr(varIdx, arg2, memory);
		else
			text = arg2;
		String filePath = System.getProperty("user.dir") + "\\Src\\";
		createFile(filePath, fileName);
		FileWriter myWriter = new FileWriter(filePath + "\\" + fileName + ".txt");
		myWriter.write(text);
		myWriter.close();
		System.out.println("File created and writed on it");

	}

	public void writeFile(String arg1, String arg2) throws IOException {
		String fileName = arg1;
		String text = arg2;
		String filePath = System.getProperty("user.dir") + "\\Src\\";
		createFile(filePath, fileName);
		FileWriter myWriter = new FileWriter(filePath + "\\" + fileName + ".txt");
		myWriter.write(text);
		myWriter.close();

	}

	public void EmptyDisk() throws IOException {
		String fileName = "Disk";
		String Empty = "";
		for (int i = 0; i < 20; i++) {
			Empty += "null" + "\n";
		}
		String text = Empty;
		String filePath = System.getProperty("user.dir") + "\\Src\\";
		createFile(filePath, fileName);
		FileWriter myWriter = new FileWriter(filePath + "\\" + fileName + ".txt");
		myWriter.write(text);
		myWriter.close();

	}

	public static void createFile(String arg, String fileName) throws IOException {
		File file = new File(arg + "\\" + fileName + ".txt");
		file.createNewFile();
	}

	public void printFromTo(String x, String y, int varIdx, memory memory) {
		memory.print();
		int firstNumber;
		int secondNumber;
		if (this.contains(varIdx, x, memory))
			firstNumber = Integer.parseInt(readFromMemoryNum(varIdx, x, memory));
		else
			firstNumber = Integer.parseInt(x);
		if (this.contains(varIdx, y, memory))
			secondNumber = Integer.parseInt(readFromMemoryNum(varIdx, y, memory));
		else
			secondNumber = Integer.parseInt(y);

		if (firstNumber < secondNumber)
			swap(firstNumber, secondNumber);

		System.out.print("values between " + firstNumber + " and " + secondNumber + " >>> ");
		for (int i = firstNumber + 1; i < secondNumber; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public void writeToMemory(Pair arg, int varIdx, memory memory) {
		memory.getMemory()[varIdx] = arg;
	}

	public String takeInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a value");
		return sc.nextLine();
	}

	public static void swap(int x, int y) {
		int temp = x;
		x = y;
		y = temp;
	}
}
