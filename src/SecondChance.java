import java.util.LinkedList;
import java.util.Queue;

public class SecondChance {

	static public class nodeSC {
		int ref;
		int chance;
		//ó�� ���Խ� �ʱⰪ 0���� �����ϱ� ���� booleanŸ�� flag����
		nodeSC(int ref, boolean flag ) {
			this.ref = ref;
			if( flag )
				chance = 1;
			else 
				chance = 0;
		}
	}
	static int[] references = { 2, 1, 3, 2, 4, 2, 5, 3 };
	static int ref_count = references.length;
	static int BUFFERMAX = 3;
	static LinkedList<nodeSC> buffer = new LinkedList<nodeSC>();

	public static void SC_Algorithm(int[] references, int refCount) {	
		int count=-1;
		boolean flag = true;
		for (int now_ref_count = 0; now_ref_count < refCount; now_ref_count++) {
			//full�� �ƴ� ���
			if(!(buffer.size()==BUFFERMAX)){
				buffer.add(new nodeSC(references[now_ref_count],false));
			}
			//full�� ���
			else{
				//full�ΰ�쿡 ���ۿ� �ִ� ���۷����� ���� ���
				for(int m=0; m<buffer.size(); m++){
					if( buffer.get(m).ref == references[now_ref_count] ){
						flag = false;
						break; 
					}
				}
				//full�ΰ�쿡 ���ۿ� ���� ���۷����� ���� ���
				while(true && flag){
					count++;
					count%=BUFFERMAX;
					//���ϸ������� ����� chance�� 0�ΰ��
					if( buffer.get(count).chance == 0 ){
						buffer.remove(count);
						buffer.add(count, new nodeSC(references[now_ref_count],true));
						break;
					}
					//���ϸ������³��� ����� ������ 1�ΰ��
					else{
						buffer.get(count).chance--;
					}
				}
				flag = true;
			}
			//��ºκ�
			for(int b=0; b<buffer.size(); b++){
				System.out.printf("%d",buffer.get(b).ref);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SC_Algorithm(references, ref_count);
	}

}
