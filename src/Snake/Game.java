package Snake;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;



public class Game extends JPanel implements ActionListener {

	static int b_width=600;//width of the board
	static int b_height=600;//height of the board
	static int unit_size=25;//size of the apple in the game
	static  int game_units=(b_width*b_height)/unit_size;//defines the maximum number of possible dots on the board (900 = (300*300)/(10*10))
	static  int random_pos=29;//calculate random position for an apple
	static int delay=75;//determines speed of the game
	 int x[]=new int[game_units];//x-coordinate of all joints of a snake
	 int y[]=new int[game_units];//y-coordinate of all joints of a snake
	int bodyParts=6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	Game(){
		random=new Random();
		this.setPreferredSize(new Dimension(b_width, b_height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running= true;
		timer= new Timer(delay,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			/*for(int i=0;i<b_height/unit_size;i++) {
				//grid boxes
				g.drawLine(i*unit_size, 0, i*unit_size, b_height);
				g.drawLine(0, i*unit_size, b_width, i*unit_size);
			}
			*/
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, unit_size, unit_size);
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], unit_size, unit_size);
				}
				else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255)));
					g.fillRect(x[i], y[i], unit_size, unit_size);
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (b_width-metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			
		}
		else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(b_width/unit_size))*unit_size;
		appleY = random.nextInt((int)(b_height/unit_size))*unit_size;
		
	}
	
	
	public void move() {
		for(int i= bodyParts; i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
			case 'U':
				y[0]=y[0]-unit_size;
				break;
			
			case 'D':
				y[0]=y[0]+unit_size;
				break;
			
			case 'L':
				x[0]=x[0]-unit_size;
				break;
			
			case 'R':
				x[0]=x[0]+unit_size;
				break;
			}
	}
	
	public void checkApple() {
		if((x[0]==appleX) && (y[0]==appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollision() {
		//This checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {  
            if ((x[0]==x[i])&&(y[0]==y[i])) {
                running=false;
            }
        }

		//This checks if head touches with one of the walls
		if (x[0] < 0) {       //The game is finished if the snake hits the right wall of the board.
            running = false;
        }
		
		if (x[0] > b_width) {      //The game is finished if the snake hits the left wall of the board.
       	 running = false;
       }
		
        if (y[0] < 0) {      //The game is finished if the snake hits the top of the board.
        	 running = false;
        }

        if (y[0] > b_height) {    //The game is finished if the snake hits the bottom of the board.
			 running = false;
       }   
        
        if (!running) {
            timer.stop();
        }
	}
	
	public void gameOver(Graphics g) {
		//Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (b_width-metrics1.stringWidth("Game Over"))/2, b_height/2);
		//Score Text
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (b_width-metrics2.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Restart Code
		g.setColor(Color.white);
		/*g.setFont(new Font("Ink Free", Font.BOLD, 40));
		g.drawString("Enter to Restart", (b_width-metrics2.stringWidth("Enter to Restart"))/2, (int) (b_height/1.5));*/
		
	 }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
				break;
					
			}
		}
	}
}
