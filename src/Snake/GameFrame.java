package Snake;
import javax.swing.JFrame;
public class GameFrame extends JFrame{
	
	
	GameFrame(){
		this.add(new Game());
		this.setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	   //exits the application is the default Close Operation
		this.setResizable(false);
		this.pack(); //takes the jFrame and sets in all of the components
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
