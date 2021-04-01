import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class gamestart extends JFrame{
	private JPanel levelboard;
	private JButton[] levelbuttonboard;
	private int size_x;
	private int size_y;
	private int total_mine;
	private String[] level= {"초급","중급","고급","게임 종료"};
	private int[][] answerboard;
	
	gamestart(){
		setTitle("MineSearch");
		setSize(300,120);
		
		levelboard=new JPanel();
		levelbuttonboard=new JButton[4];
		
		//빈 버튼 배열에 level 문자열을 넣고 생성해 패널에 추가
		for(int i=0;i<4;i++) {
			levelboard.add(levelbuttonboard[i]=new JButton(level[i]));
			levelbuttonboard[i].setFont(new Font("고딕",Font.BOLD,15));
		}
		
		//클릭 시 이벤트
		for(int i=0;i<4;i++) {
			levelbuttonboard[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int j=0;j<4;j++) {
						if(e.getSource()==levelbuttonboard[j]) {
							if(levelbuttonboard[j].getText().contentEquals("초급")) {
								size_x=9;
								size_y=9;
								total_mine=10;
							}
							else if(levelbuttonboard[j].getText().contentEquals("중급")) {
								size_x=13;
								size_y=13;
								total_mine=25;
							}
							else if(levelbuttonboard[j].getText().contentEquals("고급")) {
								size_x=14;
								size_y=20;
								total_mine=50;
							}
							else {
								dispose();
								return;
							}
							boardmine board=new boardmine(total_mine, size_x, size_y);
							answerboard=board.generateMine();
							answerboard=board.surroundMineCount();
							new MineSearch(size_x, size_y, total_mine, answerboard);
						}
					}
				}
			});
		}
		
		add(levelboard);
		
		//창을 보이게 함
		setVisible(true);
		
		//화면 가운데 위치
		setLocationRelativeTo(null);
	}
}
