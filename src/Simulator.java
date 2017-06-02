import java.util.*;
import java.io.*;

public class Simulator {
	public static void main(String[] args) throws IOException {
		ArrayList<Integer> refList = new ArrayList<Integer>();

		try {
			Scanner filein = new Scanner(new File("./data/text.txt"));

			while (filein.hasNext()) {
				String refString = filein.next();
				int ref = Integer.parseInt(refString);
				refList.add(ref); // add the integers to ArrayList
			}

			filein.close();

		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
			System.exit(0);
		} catch (NullPointerException e) {
			System.out.println("Dann halt nicht ;) - RESTART");
			System.exit(0);
		}

		Integer references[] = new Integer[refList.size()];
		references = refList.toArray(references);

		// Start time
		long startTime = System.nanoTime();

		lru_stack(references, refList.size());
		fifo(references, refList.size());
		// op
		// lru
		second_chance(references, refList.size());
		etc_algorithm(references, refList.size());

		// End time
		long endTime = System.nanoTime();

		// Total time
		long lTime = endTime - startTime;
		System.out.println("TIME : " + lTime / 1000000.0 + "(ms)");
	}

	public static void lru_stack(Integer[] references, int refCount) {
		Stack<Integer> myStack = new Stack();
		Stack<Integer> temp = new Stack();
		Integer valTemp;

		for (int i = 0; i < refCount; i++) {
			if (myStack.search(references[i]) == -1) {
				myStack.push(references[i]);
			} else {
				while (myStack.peek() != references[i]) {
					valTemp = myStack.pop();
					temp.push(valTemp);
				}
				myStack.pop();
				while (!temp.empty()) {
					valTemp = temp.pop();
					myStack.push(valTemp);
				}
				myStack.push(references[i]);
			}
		}

		// TEST
		Iterator<Integer> iter = myStack.iterator();
		while (iter.hasNext())
			System.out.print(iter.next() + " ");
		System.out.println();
	}

	public static void fifo(Integer[] references, int refCount) {
		int BUFFERMAX = 3;
		LinkedList<Integer> buffer = new LinkedList<Integer>();
	}

	public static void lru(Integer[] references, int refCount) {

	}

	public static void second_chance(Integer[] references, int refCount) {
		int BUFFERMAX = 3;
		LinkedList<nodeSC> buffer = new LinkedList<nodeSC>();
		int count = -1;
		boolean flag = true;
		System.out.println("---------- SC algorithm -------");
		for (int now_ref_count = 0; now_ref_count < refCount; now_ref_count++) {
			// full�� �ƴ� ���
			if (buffer.size() != BUFFERMAX) {
				buffer.add(new nodeSC(references[now_ref_count], false));
			}
			// full�� ���
			else {
				// full�ΰ�쿡 ���ۿ� �ִ� ���۷����� ���� ���
				for (int m=0; m<buffer.size(); m++) {
					if (buffer.get(m).ref == references[now_ref_count]) {
						flag = false;
						break;
					}
				}
				// full�ΰ�쿡 ���ۿ� ���� ���۷����� ���� ���
				while (true && flag) {
					count++;
					count %= BUFFERMAX;
					// ���ϸ������� ����� chance�� 0�ΰ��
					if (buffer.get(count).chance == 0) {
						buffer.remove(count);
						buffer.add(count, new nodeSC(references[now_ref_count], true));
						break;
					}
					// ���ϸ������³��� ����� ������ 1�ΰ��
					else {
						buffer.get(count).chance--;
					}
				}
				flag = true;
			}
			// ��ºκ�
			System.out.printf("%3d�ܰ� buffer: ", now_ref_count+1);
			for (int b = 0; b < buffer.size(); b++) {
				System.out.printf("%d", buffer.get(b).ref);
			}
			System.out.println();
		}
	}

	public static void optimal(Integer[] references, int refCount) {

	}

	public static void etc_algorithm(Integer[] references, int refCount) {
		// ������� �������
		LinkedList<nodeSC> buffer = new LinkedList<nodeSC>();
		int BUFFERMAX = 3;
		int count = -1;
		boolean flag = true;
		System.out.println("---------- etc algorithm -------");
		for (int now_ref_count = 0; now_ref_count < refCount; now_ref_count++) {
			// full�� �ƴ� ���
			if (buffer.size() != BUFFERMAX) {
				buffer.add(new nodeSC(references[now_ref_count], false));
			}
			// full�� ���
			else {
				// full�ΰ�쿡 ���ۿ� �ִ� ���۷����� ���� ���
				for (int m=0; m<buffer.size(); m++) {
					if (buffer.get(m).ref == references[now_ref_count]) {
						flag = false;
						buffer.get(m).chance++;
						break;
					}
				}
				// full�ΰ�쿡 ���ۿ� ���� ���۷����� ���� ���
				while (true && flag) {
					count++;
					count %= 3;
					// ���ϸ������� ����� chance�� 0�ΰ��
					if (buffer.get(count).chance == 0) {
						buffer.remove(count);
						buffer.add(count, new nodeSC(references[now_ref_count], false));
						break;
					}
					// ���ϸ������³��� ����� ������ 1�ΰ��
					else {
						buffer.get(count).chance--;
					}
				}
				flag = true;
			}
			// ��ºκ�
			System.out.printf("%3d�ܰ� buffer: ", now_ref_count+1);
			for (int b = 0; b < buffer.size(); b++) {
				System.out.printf("%d", buffer.get(b).ref);
			}
			System.out.println();
		}
	}
}

class nodeSC {
	int ref;
	int chance;

	// ó�� ���Խ� �ʱⰪ 0���� �����ϱ� ���� booleanŸ�� flag����
	nodeSC(int ref, boolean flag) {
		this.ref = ref;
		if (flag)
			chance = 1;
		else
			chance = 0;
	}
}