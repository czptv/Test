/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.event.*;

public class GraphicsContest extends GraphicsProgram {

	/**
	 * constants
	 */
	private static final int DELAY=1;
	
	/**
	 * instant variables
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double newGameWidth;
	private double newGameAscent;
	private GImage c1;
	private GImage c2;
	private GImage c3;
	private GImage c4;
	private GImage c5;
	private GImage c6;
	private GImage c7;
	private GImage c8;
	private GImage c9;
	private GImage c10;
	
	public void run() {
		//the image at the first display
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.43,0.43);
		add(face,-300,0);
		
		//new game
		GLabel newGame= new GLabel("New Game");
		newGame.setFont("Times-20");
		newGameWidth=newGame.getWidth();
		newGameAscent=newGame.getAscent();
		add(newGame,(getWidth()-newGame.getWidth())/2.0,getHeight()-2*newGame.getAscent());
		
		//special effects
		createEffect(newGame);
		
		//start game
		playGame();
	}

	/**
	 * make certain image move randomly on the screen
	 */
	private void createEffect(GLabel newGame) {
		//draw circles on the screen
		c1=drawCircle();
		c2=drawCircle();
		c3=drawCircle();
		c4=drawCircle();
		c5=drawCircle();
		c6=drawCircle();
		c7=drawCircle();
		c8=drawCircle();
		c9=drawCircle();
		c10=drawCircle();
		//get velocity and move
		while(c1!=null || c2!=null || c3!=null || c4!=null || c5!=null || c6!=null || c7!=null || c8!=null || c9!=null || c10!=null) {
			c1=moveCircle(c1);
			c2=moveCircle(c2);
			c3=moveCircle(c3);
			c4=moveCircle(c4);
			c5=moveCircle(c5);
			c6=moveCircle(c6);
			c7=moveCircle(c7);
			c8=moveCircle(c8);
			c9=moveCircle(c9);
			c10=moveCircle(c10);
			addMouseListeners();
			boolean a=c1==null;
			boolean f=c2==null;
			boolean d=c3==null;
			boolean s=c4==null;
			boolean q=c5==null;
			boolean e=c6==null;
			boolean r=c7==null;
			boolean t=c8==null;
			boolean y=c9==null;
			boolean u=c10==null;
			int x;
		}
	}
	
	/**
	 * draw one circle on the screen
	 * @return circle
	 */
	private GImage drawCircle() {
		GImage circle=new GImage("circle.png");
		double ratio=rgen.nextDouble();
		circle.scale(ratio,ratio);
		double x;
		double y;
		boolean side=rgen.nextBoolean();
		if(side) {
			x=rgen.nextDouble(-circle.getWidth(),getWidth()-circle.getWidth());
			y=0;
		} else {
			x=0;
			y=rgen.nextDouble(-circle.getHeight(),getHeight()-circle.getHeight());
		}
		add(circle,x,y);
		return circle;
	}
	/**
	 * get velocity and move
	 * @param circle
	 */
	private GImage moveCircle(GImage circle) {
		if (circle!=null) {
			double vx=rgen.nextDouble();
			double vy=rgen.nextDouble();
			circle.move(vx, vy);
			pause(DELAY);
			if (circle.getX()>getWidth() || circle.getY()>getHeight()) {
				remove(circle);
				circle=drawCircle();
			}		
		}
		return circle;
	}
	
	/**
	 * click to start the game 
	 */
	public void mouseClicked(MouseEvent e) {
		double x=(getWidth()-newGameWidth)/2.0;
		double y=getHeight()-3*newGameAscent;
		boolean xContain=e.getX()>x && e.getX()<x+newGameWidth;
		boolean yContain=e.getY()>y && e.getY()<y+2*newGameAscent;
		if(xContain && yContain) {
			removeAll();
			c1=null;
			c2=null;
			c3=null;
			c4=null;
			c5=null;
			c6=null;
			c7=null;
			c8=null;
			c9=null;
			c10=null;
			int h;
		}
	}
	
	/**
	 * play the game
	 */
	private void playGame() {
		goFirstTask();
		//goSecondTask();
		//goThirdTask();
	}
	
	/**
	 * get and finish the first task
	 */
	private void goFirstTask() {
		getFirstTask();
		//finishFirstTask();
	}
	
	private void getFirstTask() {
		GImage bg=new GImage("RenWu2.jpg");
		bg.scale(0.63,0.63);
		add(bg,0,0);
	}
}


