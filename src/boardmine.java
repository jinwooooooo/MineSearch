
public class boardmine {
	
	private int total_mine;
	private int size_x;
	private int size_y;
	private int[][] answerboard;
	private int minelocation_x;
	private int minelocation_y;
	private int[] dx= {-1,-1,0,1,1,1,0,-1};
	private int[] dy= {0,-1,-1,-1,0,1,1,1};
	private static final int mine=-1;
	
	boardmine(int total_mine, int size_x, int size_y){
		this.total_mine=total_mine;
		this.size_x=size_x;
		this.size_y=size_y;
		
		answerboard=new int[size_x][size_y];
	}
	
	//랜덤으로 지뢰 발생
	public int[][] generateMine() {
		for(int i=0;i<total_mine;i++) {
			while(true) {
				minelocation_x=(int)(Math.random()*size_x);
				minelocation_y=(int)(Math.random()*size_y);
				
				if(answerboard[minelocation_x][minelocation_y]==0) {
					answerboard[minelocation_x][minelocation_y]=mine;
					break;
				}
			}
		}
		return answerboard;
	}
	
	//주변 지뢰 개수 세기
	public int[][] surroundMineCount(){
		for(int i=0;i<size_x;i++) {
			for(int j=0;j<size_y;j++) {
				if(answerboard[i][j]==mine) {
					for(int k=0;k<8;k++) {
						if(((i+dx[k])>-1)&&((i+dx[k])<size_x)&&((j+dy[k])>-1)&&((j+dy[k])<size_y)){
							if(answerboard[i+dx[k]][j+dy[k]]!=mine) {
								answerboard[i+dx[k]][j+dy[k]]++;
							}
						}
					}
				}
			}
		}
		return answerboard;
	}
}
