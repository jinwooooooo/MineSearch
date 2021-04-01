import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MineSearch extends JFrame{
	private JPanel gameboard;
	private JButton[][] buttonboard;
	private int size_x;
	private int size_y;
	private int total_mine;
	private int size;
	private int[][] searchboard;
	private int restart;
	private int[] dx= {-1,-1,0,1,1,1,0,-1};
	private int[] dy= {0,-1,-1,-1,0,1,1,1};
	private int[][] answerboard;
	private int opencount=0;
	
	private static final int mine=-1;
	private static final int blank=0;
	private static final int unsearched=0;
	private static final int searched=-99;
	
	MineSearch(int size_x, int size_y, int total_mine, int[][] answerboard){
		for(int i=0;i<size_x;i++) {
			for(int j=0;j<size_y;j++) {
				if(answerboard[i][j]==-1) {
					System.out.print(answerboard[i][j]);;
					System.out.print("  ");
				}
				else {
					System.out.print(answerboard[i][j]);;
					System.out.print("   ");
				}
			}
			System.out.println();
		}
		this.size_x=size_x;
		this.size_y=size_y;
		this.total_mine=total_mine;
		this.answerboard=answerboard;
		size=size_x*size_y;
		
		searchboard=new int[size_x][size_y];
		for(int i=0;i<size_x;i++) {
			for(int j=0;j<size_y;j++) {
				searchboard[i][j]=answerboard[i][j];
			}
		}
		
		setTitle("MineSearch");
		setSize(70*size_y, 70*size_x);
		
		//그림판(그리드)과 버튼을 담을 수 있는 그릇 생성
		gameboard=new JPanel(new GridLayout(size_x, size_y));
		buttonboard=new JButton[size_x][size_y];
		
		//각각의 버튼을 그릇에 생성해 패널에 추가
		for(int i=0;i<size_x;i++) {
			for(int j=0;j<size_y;j++) {
				gameboard.add(buttonboard[i][j]=new JButton(" "));
				buttonboard[i][j].setFont(new Font("고딕",Font.BOLD,15));;
			}
		}
		
		//이벤트 등록하기
		//ActionListener 인터페이스 안에 있는 actionPerformed 메소드 재정의
		for(int i=0;i<size_x;i++){
			for(int j=0;j<size_y;j++) {
				buttonboard[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for(int k=0;k<size_x;k++) {
							for(int l=0;l<size_y;l++) {
								if(e.getSource()==buttonboard[k][l]) {
									buttonboard[k][l].setEnabled(false);
									
									//지뢰를 눌렀을 경우 answerboard가 열리고 게임을 restart 할 지 안할지 선택
									if(answerboard[k][l]==mine) {
										for(int m=0;m<size_x;m++) {
											for(int n=0;n<size_y;n++) {
												if(answerboard[m][n]==mine) {
													buttonboard[m][n].setText("폭탄");
													buttonboard[m][n].setBackground(new Color(255,0,0));
												}
												else if(answerboard[m][n]==blank) {
													buttonboard[m][n].setText(" ");
												}
												else {
													buttonboard[m][n].setText(String.valueOf(answerboard[m][n]));
													buttonboard[m][n].setBackground(new Color(220,220,220));
												}
												buttonboard[m][n].setEnabled(false);
											}
										}
										restart=JOptionPane.showConfirmDialog(null, "다시 하시겠습니까?","실패",JOptionPane.YES_NO_OPTION);
										if(restart==JOptionPane.YES_OPTION) {
											dispose();
										}
										else {
											System.exit(0);
										}
									}
									else if(answerboard[k][l]==blank) {
										dfs(k,l);
									}
									else {
										buttonboard[k][l].setText(String.valueOf(answerboard[k][l]));
										opencount++;
										if(total_mine==(size_x*size_y-opencount)) {
											JOptionPane.showMessageDialog(null,"성공!");
										}
									}
								}
							}
						}
					}
				});
			}
		}
		
		add(gameboard);
		
		//창을 보이게 함
		setVisible(true);
		
		//화면 가운데 위치시키기
		setLocationRelativeTo(null);
	}
	
	public void dfs(int x, int y) {
		if(x<0||x>=size_x||y<0||y>=size_y) {
			return;
		}
		if(searchboard[x][y]==unsearched) {
			searchboard[x][y]=searched;
			
			for(int i=0;i<8;i++) {
				dfs(x+dx[i],y+dy[i]);
			}
			
			buttonboard[x][y].setText(" ");
			buttonboard[x][y].setEnabled(false);
			opencount++;
			for(int i=0;i<8;i++) {
				if(((x+dx[i])>-1)&&((x+dx[i])<size_x)&&((y+dy[i])>-1)&&((y+dy[i])<size_y)){
					if(answerboard[x+dx[i]][y+dy[i]]!=blank && buttonboard[x+dx[i]][y+dy[i]].isEnabled()==true) {
						buttonboard[x+dx[i]][y+dy[i]].setText(String.valueOf(answerboard[x+dx[i]][y+dy[i]]));
						buttonboard[x+dx[i]][y+dy[i]].setEnabled(false);
						opencount++;
					}
				}
			}
			if(total_mine==(size_x*size_y-opencount)) {
				JOptionPane.showMessageDialog(null,"성공!");
			}
		}
	}
}
