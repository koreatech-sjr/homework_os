import java.util.LinkedList;

public class AC {
	static public class nodeSC {
		int ref;
		int chance;
		//처음 삽입시 초기값 0으로 세팅하기 위해 boolean타입 flag설정
		nodeSC(int ref, boolean flag ) {
			this.ref = ref;
			if( flag )
				chance = 1;
			else 
				chance = 0;
		}
	}
	static int[] references = { 2, 1, 3, 2, 4, 2, 2,3,4,1,7,8,9,5,3,2,4,5,7,8,2,3,4 };
	static int ref_count = references.length;
	static int BUFFERMAX = 3;
	static LinkedList<nodeSC> buffer = new LinkedList<nodeSC>();

	public static void clockSC(int[] references, int refCount) {	
		//순서대로 집어넣자
		int count=-1;
		boolean flag = true;
		for (int now_ref_count = 0; now_ref_count < refCount; now_ref_count++) {
			//full이 아닌 경우
			if(!(buffer.size()==BUFFERMAX)){
				buffer.add(new nodeSC(references[now_ref_count],false));
			}
			//full인 경우
			else{
				//full인경우에 버퍼에 있는 레퍼런스가 들어올 경우
				for(int m=0; m<buffer.size(); m++){
					if(buffer.get(m).ref == references[now_ref_count] ){
						flag = false;
						buffer.get(m).chance++;
						break; 
					}
				}
				//full인경우에 버퍼에 없는 레퍼런스가 들어올 경우
				while(true && flag){
					count++;
					count%=3;
					//제일먼저들어온 노드의 chance가 0인경우
					if( buffer.get(count).chance == 0 ){
						buffer.remove(count);
						buffer.add(count, new nodeSC(references[now_ref_count],false));
						break;
					}
					//제일먼저들어온놈의 노드의 찬스가 1인경우
					else{
						buffer.get(count).chance--;
					}
				}
				flag = true;
			}
			//출력부분
			for(int b=0; b<buffer.size(); b++){
				System.out.printf("%d",buffer.get(b).ref);
			}
			System.out.println();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		clockSC(references, ref_count);
	}
}
