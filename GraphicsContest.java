/*
 * File: GraphicsContest.java
 * --------------------------
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.applet.AudioClip;
import java.awt.event.*;
import java.lang.Math;

public class GraphicsContest extends GraphicsProgram {

	/**
	 * constants
	 */
	private static final int DELAY=1;
	private static final int PLAYER_LIFE=100;
	private static final int NPC_LIFE=300;
	private static final int BIAO_DAMAGE=30;
	private static final int AIRFLOW_DAMAGE=20;
	private static final int NPC_ATTACK=25;
	
	/**
	 * instant variables
	 */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	//beginning interface
	/**
	 * the width of the label "New Game" at the beginning interface
	 */
	private double newGameWidth;
	/**
	 * the ascent of the label "New Game" at the beginning interface
	 */
	private double newGameAscent;
	/**
	 * whether the player has clicked the "New Game" button
	 */
	private boolean firstClicked=false;
	/**
	 * whether player is still at the beginning interface
	 */
	private boolean firstStageME;
	/**
	 * the ten flowing snows
	 */
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
	
	//task 1
	/**
	 * the player in task 1
	 */
	private GImage plT1;
	/**
	 * whether player is pressing the up key
	 */
	private boolean up1;
	/**
	 * whether player is pressing the down key
	 */
	private boolean down1;
	/**
	 * whether player is pressing the left key
	 */
	private boolean left1;
	/**
	 * whether player is pressing the right key
	 */
	private boolean right1;
	/**
	 * x parameter of the player in task1
	 */
	private double plX;
	/**
	 * y parameter of the player in task1
	 */
	private double plY;
	/**
	 * whether player pass the task 1
	 */
	private boolean task1=true;
	/**
	 * whether player is still on the task 1
	 */
	private boolean task1GoingOn;
	
	//task 2
	/**
	 * whether player pass task 2
	 */
	private boolean task2=true;
	/**
	 * npc in task2
	 */
	private GImage npc;
	/**
	 * player in task2
	 */
	private GImage plT2;
	/**
	 * x parameter of npc in task 2
	 */
	private double npcX;
	/**
	 * x parameter of npc in task 2
	 */
	private double npcY;
	/**
	 * x parameter of the player in task 2
	 */
	private double plT2X;
	/**
	 * y parameter of the player in task 2
	 */
	private double plT2Y;
	/**
	 * the hidden weapon (biao)
	 */
	private GImage biao;
	/**
	 * the image of airflow
	 */
	private GImage airflow;
	/**
	 * the life value of player in task 2
	 */
	private int plLife;
	/**
	 * the life value npc in task 2
	 */
	private int npcLife;
	/**
	 * is it turn for player to move
	 */
	private boolean task2GoingOn;
	/**
	 * the facing direction of player
	 */
	private String playerDirection;
	/**
	 * is player moving to the left in task 2
	 */
	private boolean left2;
	/**
	 * is player moving to the right in task 2
	 */
	private boolean right2;
	/**
	 * is player pressing the key "A" in task 2
	 */
	private boolean pressA;
	/**
	 * is player pressing the key "S" in task 2
	 */
	private boolean pressS;
	
	public void run() {
		//the image at the first display
		GImage face=new GImage("FengMian.jpg");
		face.scale(0.43,0.43);
		add(face,-300,0);
		firstStageME=true;
		
		//new game
		GLabel newGame= new GLabel("New Game");
		newGame.setFont("Times-20");
		newGameWidth=newGame.getWidth();
		newGameAscent=newGame.getAscent();
		add(newGame,(getWidth()-newGame.getWidth())/2.0,getHeight()-2*newGame.getAscent());
		
		AudioClip softSound = MediaTools.loadAudioClip("soft.wav");
		softSound.play();
		//special effects
		createEffect();
		
		//start game
		playGame();
	}

	/**
	 * make certain image move randomly on the screen
	 */
	private void createEffect() {
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
		addMouseListeners();
		while(!firstClicked) {
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
			y=-circle.getHeight();
		} else {
			x=-circle.getWidth();
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
		if (firstStageME==true) {
			double x=(getWidth()-newGameWidth)/2.0;
			double y=getHeight()-3*newGameAscent;
			boolean xContain=e.getX()>x && e.getX()<x+newGameWidth;
			boolean yContain=e.getY()>y && e.getY()<y+2*newGameAscent;
			if(xContain && yContain) {
				removeAll();
				firstClicked=true;
				firstStageME=false;
			}
		}
	}
	
	/**
	 * play the game
	 */
	private void playGame() {
		//introduceTheGame();
		goFirstTask();
		if (task1) {
			goSecondTask();
		}
		if (task1 && task2) {
			goThirdScene();
		}
		if(!task1 || !task2) {
			goDie();
		}
		winGame();
	}
	
	/**
	 * introduce the time background and personal background
	 */
	private void introduceTheGame() {
		introTime();
		introPlayer();
	}
	
	/**
	 * display the introduction of time background of the game
	 */
	private void introTime() {
		removeAll();
		//add bg
		GImage bg=new GImage("LuoYang.jpg");
		bg.scale(0.6,0.6);
		add(bg);
		//add intro of time
		GImage time=new GImage("IntroduceTime.png");
		time.scale(0.2,0.2);
		add(time,(getWidth()-time.getWidth())/2.0,getHeight());
		//move intro of time
		while(time.getY()+time.getHeight()>getHeight()) {
			time.move(0, -1);
			pause(50);
		}
		waitForClick();
		removeAll();
	}
	
	/**
	 * display the introduction of player background and the image of player of the game
	 */
	private void introPlayer() {
		//add bg
		GImage bg=new GImage("BianHua1.jpg");
		bg.scale(0.6,0.6);
		add(bg);
		//add intro
		GImage introPlayer=new GImage("IntroducePlayer.png");
		introPlayer.scale(0.15,0.15);
		add(introPlayer,getWidth()/2-50,0);
		//add player
		GImage player=new GImage("CharacterFront.png");
		player.scale(0.4,0.4);
		add(player,getWidth()/8,getHeight()/10);
		waitForClick();
		removeAll();
	}
	
	/**
	 * get and finish the first task
	 */
	private void goFirstTask() {
		task1GoingOn=true;
		/*getFirstTask();
		arriveAtMansion();
		if (task1) {*/
			meetMaid();
		//}
		task2GoingOn=false;
	}
	
	/**
	 * get the first task on the street
	 */
	private void getFirstTask() {
		meetGirl();
		talkToGirl();
	}
	
	/**
	 * the girl who hand over the first task appear
	 */
	private void meetGirl() {
		//add background
		addBgT1();
		//add NPC
		GImage npc1=new GImage("ChunYangBack.png");
		npc1.scale(0.2,0.2);
		add(npc1,getWidth()/2+50,getHeight()/2);
		waitForClick();
		//add scroll for prompt
		GImage scroll=new GImage("scroll.png");
		scroll.scale(0.3,0.3);
		add(scroll,50,100);
		//add prompt
		double x=scroll.getX()+35;
		double yInit=scroll.getY()+70;
		double lineHeight=30;
		createPrompt("There is a girl on",x,yInit);
		createPrompt("the street. Go and",x,yInit+lineHeight);
		createPrompt("ask her if she needs",x,yInit+2*lineHeight); 
		createPrompt("some help.",x,yInit+3*lineHeight);
		createPrompt("Click to continue.",x,yInit+5*lineHeight);
		
		waitForClick();
		removeAll();		
	}
	
	/**
	 * talk to the girl on the street to get task 1
	 */
	private void talkToGirl() {
		//add bg
		addBgT1();
		//add NPC
		GImage npc1=new GImage("NPCEastFront.png");
		npc1.scale(0.6,0.6);
		add(npc1,getWidth()/2,100);
		//add scroll
		GImage scroll=new GImage("scroll.png");
		scroll.scale(0.47,0.52);
		add(scroll,20,15);
		//add prompt
		double x=scroll.getX()+50;
		double yInit=scroll.getY()+50;
		double lineHeight=30;
		createPrompt("I am the emissary of ChunYang",x,yInit+lineHeight);
		createPrompt("Branch. We're now plotting the",x,yInit+2*lineHeight);
		createPrompt("death of LuShan's military",x,yInit+3*lineHeight);
		createPrompt("minister, Yuan. Can you help",x,yInit+4*lineHeight);
		createPrompt("deliver the poison to Yuan's",x,yInit+5*lineHeight);
		createPrompt("maid? She is one of our spies",x,yInit+6*lineHeight);
		createPrompt("and she will wait for you in",x,yInit+7*lineHeight);
		createPrompt("Yuan's mansion. To arrive at",x,yInit+8*lineHeight);
		createPrompt("Yuan's mantion, be careful not",x,yInit+9*lineHeight);
		createPrompt("to fall off the suspension",x,yInit+10*lineHeight);
		createPrompt("bridge or the cliff.",x,yInit+11*lineHeight);
		
		waitForClick();
		removeAll();
	}
	
	/**
	 * add the background for getting task 1
	 */
	private void addBgT1() {
		GImage bg=new GImage("RenWu2.jpg");
		bg.scale(0.75,0.75);
		add(bg,0,0);		
	}
	
	/**
	 * create prompt for any words appearing on the scroll
	 * @param ppt: the string in the prompt
	 * @param x: x parameter of prompt
	 * @param y: y parameter of prompt
	 */
	
	private void createPrompt(String ppt, double x, double y) {
		GLabel prompt=new GLabel(ppt);
		prompt.setFont("Times-16-Bold");
		add(prompt,x,y);
	}
	
	/**
	 * do the task 1
	 */
	private void arriveAtMansion() {
		task1SetUp();
		//make sure the player doesn't fall off the bridge
		double footX=plX+plT1.getWidth()/2.0;
		double footY=plY+plT1.getHeight();
		
		//divide the area on the background to examine whether player has fall off the bridge
		GPolygon land=drawLand();
		GPolygon upBri=drawUpBridge();
		GPolygon midBri=drawMidBridge();
		GPolygon downBri=drawDownBridge();
		GPolygon arrival=arrive();
		
		//move player
		addKeyListeners();
		while(true){
			pause(DELAY*10);
			if(land.contains(footX,footY)) {
				moveOnLand();
			} else if(upBri.contains(footX,footY)) {
				moveOnUpBri();
			} else if(midBri.contains(footX,footY)) {
				moveOnMidBri();
			} else if(downBri.contains(footX,footY)) {
				moveOnDownBri();
			} else if(arrival.contains(footX,footY)) {
				removeAll();
				break;
			} else {
				task1=false;
				removeAll();
				break;
			}
			footX=plX+plT1.getWidth()/2.0;
			footY=plY+plT1.getHeight();
		}
		removeAll();
	}
	
	/**
	 * setup the background and player for doing task 1
	 */
	private void task1SetUp() {
		//add bg
		GImage bg=new GImage("Task1_1.jpg");
		bg.scale(0.5,0.5);
		add(bg,0,0);
		//zoom bg
		for(int i=0;i<80;i++) {
			remove(bg);
			bg=new GImage("Task1_1.jpg");
			double scale=0.5*Math.pow(1.01, i);
			bg.scale(scale,scale);
			add(bg,-10*i,-5.5*i);
			pause(DELAY*20);
		}
		//add player
		plX=600;
		plY=420;
		plT1=new GImage("CharacterLeft.png");
		plT1.scale(0.05,0.05);
		add(plT1,plX,plY);
	}
	
	/**
	 * the way player looks like and moves on the land
	 */
	private void moveOnLand() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plY-=0.5;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plY+=0.5;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plX-=1;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.05,0.05);
				add(plT1,plX,plY);
				plX+=1;
			}			
		}
	}
	
	/**
	 * the way player looks like and moves going up the bridge
	 */
	private void moveOnUpBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plY-=0.3;
				plX+=0.6;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plY+=0.3;
				plX-=0.6;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plX-=0.8;
				plY-=0.6;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.047,0.047);
				add(plT1,plX,plY);
				plX+=0.8;
				plY+=0.6;
			}			
		}
	}
	
	/**
	 * the way player looks like and moves walking on the middle part of the bridge
	 */
	private void moveOnMidBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plY-=0.3;
				plX+=0.8;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plY+=0.3;
				plX-=0.8;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plX-=0.8;
				plY-=0.2;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.044,0.044);
				add(plT1,plX,plY);
				plX+=0.8;
				plY+=0.2;
			}			
		}
	}
	
	/**
	 * the way player looks like and moves going down the bridge
	 */
	private void moveOnDownBri() {
		if(up1 || down1 || left1 || right1) {
			remove(plT1);
			if(up1) {
				plT1=new GImage("CharacterBack.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plY-=0.2;
				plX+=0.5;
			} else if(down1){
				plT1=new GImage("CharacterFront.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plY+=0.2;
				plX-=0.5;
			} else if(left1) {
				plT1=new GImage("CharacterLeft.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plX-=0.7;
				plY+=0.07;
			} else if(right1) {
				plT1=new GImage("CharacterRight.png");
				plT1.scale(0.041,0.041);
				add(plT1,plX,plY);
				plX+=0.7;
				plY-=0.07;
			}			
		}
	}
	
	/**
	 * the area of land in task 1
	 * @return land
	 */
	private GPolygon drawLand() {
		GPolygon land=new GPolygon();
		land.addVertex(getWidth(),320);
		land.addVertex(getWidth(), getHeight());
		land.addVertex(435, getHeight());
		land.addVertex(365, getHeight()-60);
		land.addVertex(435, getHeight()-55);
		land.addVertex(480, getHeight()-90);
		land.addVertex(getWidth()-80, 320);
		return land;
	}
	
	/**
	 * the area of up bridge in task 1
	 * @return up bridge
	 */
	private GPolygon drawUpBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(435, getHeight()-55);
		brd.addVertex(480, getHeight()-90);
		brd.addVertex(370, getHeight()-155);
		brd.addVertex(320, getHeight()-150);
		brd.addVertex(360, getHeight()-120);
		return brd;
	}
	
	/**
	 * the area of middle bridge in task 1
	 * @return middle bridge
	 */
	private GPolygon drawMidBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(370, getHeight()-155);
		brd.addVertex(320, getHeight()-150);
		brd.addVertex(220, getHeight()-165);
		brd.addVertex(260, getHeight()-185);
		brd.addVertex(320, getHeight()-175);
		return brd;
	}
	
	/**
	 * the area of down bridge in task 1
	 * @return down bridge
	 */
	private GPolygon drawDownBridge() {
		GPolygon brd=new GPolygon();
		brd.addVertex(220, getHeight()-165);
		brd.addVertex(260, getHeight()-185);
		brd.addVertex(180, getHeight()-180);
		brd.addVertex(110, getHeight()-160);
		return brd;
	}
	
	/**
	 * examine that the player has arrive at the mansion without falling off the bridge or the cliff
	 * @return arrival area
	 */
	private GPolygon arrive() {
		GPolygon arrive=new GPolygon();
		arrive.addVertex(180, getHeight()-180);
		arrive.addVertex(110, getHeight()-160);		
		arrive.addVertex(50, getHeight()-160);
		arrive.addVertex(110, getHeight()-220);
		return arrive;
	}
	
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		//stage one: in task 1
		if(task1GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_UP:
				up1=true;
				break;
			case KeyEvent.VK_DOWN:
				down1=true;
				break;
			case KeyEvent.VK_LEFT:
				left1=true;
				break;
			case KeyEvent.VK_RIGHT :
				right1=true;
				break;
			}
		} else if(task2GoingOn) {
			//stage 2: in task 2
			if(keyCode==KeyEvent.VK_A) {
				pressA=true;
			}
			if(keyCode==KeyEvent.VK_S) {
				pressS=true;
			}
			if(keyCode==KeyEvent.VK_LEFT) {
				left2=true;
				playerDirection="Left";
			}
			if(keyCode==KeyEvent.VK_RIGHT) {
				right2=true;
				playerDirection="Right";
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		//stage 1: in task 1
		if(task1GoingOn) {
			switch(keyCode) { 
			case KeyEvent.VK_UP:
				up1=false;
				break;
			case KeyEvent.VK_DOWN:
				down1=false;
				break;
			case KeyEvent.VK_LEFT:
				left1=false;
				break;
			case KeyEvent.VK_RIGHT :
				right1=false;
				break;
			}
		} else if(task2GoingOn) {
			//stage 2: in task 2
			if(keyCode==KeyEvent.VK_A) {
				pressA=true;
			}
			if(keyCode==KeyEvent.VK_S) {
				pressS=true;
			}
			if(keyCode==KeyEvent.VK_LEFT) {
				left2=false;
			}
			if(keyCode==KeyEvent.VK_RIGHT) {
				right2=false;
			}
		}
	}
	
	/**
	 * meet and talk with the maid that the player need to deliver the poison to
	 */
	private void meetMaid() {
		//draw bg
		GImage bg=new GImage("Task1_2.jpg");
		bg.scale(0.6,0.6);
		add(bg);
		//draw character
		GImage npc=new GImage("NanZhaoCe.png");
		npc.scale(0.65,0.65);
		add(npc,getWidth()/2-50,getHeight()/4+50);
		GImage pl=new GImage("CharacterLeft.png");
		pl.scale(0.35,0.35);
		add(pl,getWidth()/2+150,getHeight()/4+40);
		waitForClick();
		//add scroll
		GImage scr=new GImage("Task1Scroll.png");
		scr.scale(0.15,0.15);
		add(scr,50,0);
		
		waitForClick();
		removeAll();
	}
	
	/**
	 * go to the second task
	 */
	private void goSecondTask() {
		playerDirection="Left";
		plLife=PLAYER_LIFE;
		npcLife=NPC_LIFE;
		changeBg1();
		changeBg2();
		changeBg3();
		fight();
		if (plLife>0) {
			task2=true;
		} else if(npcLife>0) {
			task2=false;
		}
	}
	
	/**
	 * the first background of task 2
	 */
	private void changeBg1() {
		//bg
		GImage bg1=new GImage("Task2_1.jpg");
		bg1.scale(0.8,0.8);
		add(bg1);
		//rain
		GImage rain=new GImage("Rain.png");
		rain.scale(0.5,0.5);
		add(rain,1,-920);
		//wind
		GImage wind=new GImage("Wind.png");
		wind.scale(0.4,0.4);
		add(wind,-wind.getWidth()+getWidth(),0);
		//rain sound
		AudioClip rainSound = MediaTools.loadAudioClip("rain.wav");
		rainSound.play();
		for(int i=0;i<330;i++) {
			bg1.move(-0.8, 0);
			rain.move(0,3);
			wind.move(2,0);
			pause(DELAY*20);
		}
		removeAll();
		
	}
	
	/**
	 * the second background of task 2
	 */
	private void changeBg2() {
		GImage bg2=new GImage("Task2_2Zoom.jpg");
		bg2.scale(0.85,0.85);
		add(bg2);
		GImage rain=new GImage("Rain.png");
		rain.scale(0.45,0.45);
		add(rain,0,-800);
		GImage wind=new GImage("Wind.png");
		wind.scale(0.4,0.4);
		add(wind,-wind.getWidth()+getWidth()+660,0);
		for(int i=0;i<200;i++) {
			bg2.scale(1.002,1.002);
			bg2.move(-1*0.3,0);
			rain.move(0,4);
			wind.move(2, 0);
			pause(DELAY*30);
		}
		removeAll();
	}
	
	/**
	 * the third background and setup of task 2
	 * [when player is talking to the guard]
	 */
	private void changeBg3() {
		addBg3();
		waitForClick();
		drawNPCPlayer2_1();
		drawScroll2_1();
		addPrompt2_1();
		waitForClick();
		removeAll();
		addBg3();
		drawNPCPlayer2_1();
		drawScroll2_2();
		addPrompt2_2();
		waitForClick();
		removeAll();
	}
	
	/**
	 * add the third background 
	 */
	private void addBg3() {
		GImage bg=new GImage("Task2_3.jpg");
		bg.scale(0.78,0.78);
		add(bg);
	}
	
	/**
	 * draw NPC and player the first dialogue of task 2
	 */
	private void drawNPCPlayer2_1() {
		GImage npcInit=new GImage("TianCeRight.png");
		npcInit.scale(0.2,0.2);
		add(npcInit,getWidth()/2,getWidth()/3);
		GImage pl2=new GImage("CharacterLeft.png");
		pl2.scale(0.2,0.2);
		add(pl2,getWidth()/2+150,getWidth()/3);
	}
	
	/**
	 * draw the first scroll in task 2
	 */
	private void drawScroll2_1() {
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.2);
		add(scroll,40,180);
	}
	
	/**
	 * add words on the 1st scroll in task 2
	 */
	private void addPrompt2_1() {
		double x=90;
		double yInit=230;
		double lineHeight=30;
		createPrompt("I'm a guard here. You",x,yInit);
		createPrompt("can never get the scroll",x,yInit+lineHeight);
		createPrompt("unless you beat me.",x,yInit+2*lineHeight);
		createPrompt("Come and fight with me!",x,yInit+3*lineHeight);
	}
	
	/**
	 * draw the 2nd scroll in task 2
	 */
	private void drawScroll2_2() {
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.5,0.52);
		add(scroll,30,10);
	}
	
	/**
	 * add words on the 2nd scroll in task 2
	 */
	private void addPrompt2_2() {
		double x=105;
		double yInit=100;
		double lineHeight=30;
		createPrompt("Hint: Try the keys \"Left\",",x,yInit);
		createPrompt("and \"Right\" to move. Try",x,yInit+lineHeight);
		createPrompt("\"S\" to throw the hidden",x,yInit+2*lineHeight);
		createPrompt("weapon at your enemy and",x,yInit+3*lineHeight);
		createPrompt("\"A\" to use airflow against",x,yInit+4*lineHeight);
		createPrompt("him. The hidden weapon can",x,yInit+5*lineHeight);
		createPrompt("cause larger damage. The",x,yInit+6*lineHeight);
		createPrompt("guard can both attack and",x,yInit+7*lineHeight);
		createPrompt("defend against you attack.",x,yInit+8*lineHeight);
		createPrompt("Your attack will not work",x,yInit+9*lineHeight);
		createPrompt("when he is defending.",x,yInit+10*lineHeight);

	}
	
	/**
	 * the guard and player starts to fight
	 */
	private void fight() {
		addBg3() ;
		drawNPC2_2();
		drawPlayer2_2();
		fightNPC2();
	}
	
	/**
	 * draw the initial NPC for fighting
	 */
	private void drawNPC2_2() {
		npc=new GImage("TianCeRight.png");
		npc.scale(0.2,0.2);
		add(npc,getWidth()/3,getWidth()/3);
		npcX=npc.getX();
		npcY=npc.getY();
	}
	
	/**
	 * draw the initial player for fighting
	 */
	private void drawPlayer2_2() {
		plT2=new GImage("CharacterLeft.png");
		plT2.scale(0.2,0.2);
		add(plT2,getWidth()/2+50,getWidth()/3);
		plT2X=plT2.getX();
		plT2Y=plT2.getY();
	}
	
	/**
	 * the fight between the player and npc
	 */
	private void fightNPC2() {
		addKeyListeners();
		while (plLife>0 && npcLife>0) {
			int step=getStep();
			String direction=moveNPC(step);
			//randomly decide whether npc is going to defend or attack
			boolean attack=rgen.nextBoolean();
			if (attack) {
				fightNPCXiaoZhao(direction);
			} else {
				defendNPC(direction);
			}
			for(int i=0;i<1000;i++) {
				task2GoingOn=true;
				pause(1);
				controlPlayer();
				task2GoingOn=false;
			}
		}
	}
	
	/**
	 * control the gesture and position of the player according to the key player presses
	 */
	private void controlPlayer() {
		if(left2) {
			movePlayer();
		}
		if(right2) {
			movePlayer();
		}
		if(pressA) {
			plDaZhao(playerDirection);
			pressA=false;
		}
		if (pressS) {
			plXiaoZhao(playerDirection);
			pressS=false;
		}
	}
	
	private String getNPCDirection() {
		String direction;
		if(npc.getX()>plT2.getX()) {
			direction="Left";
		} else {
			direction="Right";
		}
		return direction;
	}
	
	/**
	 * how many steps the npc is going to move
	 * @return
	 */
	private int getStep() {
		//first have this boolean because I find the rgen.nextInt(-80,80)
		//mostly generate positive number
		boolean left=rgen.nextBoolean();
		int stepAbs=rgen.nextInt(0,80);
		int step;
		if(left) {
			step=-stepAbs;
		} else {
			step=stepAbs;
		}
		return step;
	}
	
	/**
	 * NPC move
	 * @param step
	 */
	private String moveNPC(int step) {
		String direction=getNPCDirection();
		//draw NPC
		drawNPCOriginalPosition(direction);
		boolean hitWall=false;
		int oneStep=decideDirection(step);
		for(int i=0;i<Math.abs(step);i++) {
			if(!hitWall) {
				npc.move(oneStep,0);
				npcX+=oneStep;
			} else {
				npc.move(-oneStep,0);
				npcX-=oneStep;
			}
			if(!hitWall) {
				hitWall=checkHitWall(npc);
			}
			pause(10);
		}
		return direction;
	}
	
	/**
	 * decide the direction of one step of npc's movement according to whether 
	 * the step is positive
	 * @param step
	 * @return the direction of one step 
	 */
	private int decideDirection(int step) {
		boolean moveToRight=(step>=0);
		int oneStep;
		if(moveToRight) {
			oneStep=1;
		} else {
			oneStep=-1;
		}
		return oneStep;
	}
	
	/**
	 * check if NPC hit wall or is too far away from the player
	 * @param role
	 * @return oppositeDirection
	 */
	private boolean checkHitWall(GImage role) {
		boolean oppositeDirection=false;
		boolean hitWall=role.getX()<=0 || role.getX()+role.getWidth()>=getWidth();
		boolean tooFarFromPlayer=npc.getX()<plT2.getX()-350 || npc.getX()>plT2.getX()+350;
		if(hitWall || tooFarFromPlayer) {
			oppositeDirection=true;
		}
		return oppositeDirection;
	}
	
	/**
	 * NPC attacks the player
	 * @param direction
	 */
	private void fightNPCXiaoZhao(String direction) {
		pause(100);
		fightNPCPrepare(direction);
		fightNPC(direction);
		fightNPCPrepare(direction);
		drawNPCOriginalPosition(direction);
	}
	
	/**
	 * the gesture of NPC preparing to attack
	 * @param direction
	 */
	private void fightNPCPrepare(String direction) {
		remove(npc);
		npc=new GImage("TianCeZhaoShi"+direction+".png");
		npc.scale(0.18,0.18);
		if(direction.equals("Left")) {
			add(npc,npcX-55,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		pause(100);
	}
	
	/**
	 * gesture of NPC when attacking
	 * @param direction
	 */
	private void fightNPC(String direction) {
		remove(npc);
		npc=new GImage("NPCX"+direction+".png");
		npc.scale(0.18,0.18);
		if(direction.equals("Left")) {
			add(npc,npcX-190,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		AudioClip hitSound = MediaTools.loadAudioClip("hit.wav");
		hitSound.play();
		pause(500);
		hitPlayer(direction);
	}
	
	/**
	 * the position of NPC right after attacking or defending
	 * @param direction
	 */
	private void drawNPCOriginalPosition(String direction) {
		remove(npc);
		npc=new GImage("TianCe"+direction+".png");
		npc.scale(0.2,0.2);
		add(npc,npcX,npcY);
	}
	
	/**
	 * check if NPC hit player in attacking
	 * @param direction
	 */
	private void hitPlayer(String direction) {
		boolean hit;
		if(direction.equals("Left")) {
			hit=(npc.getX()+npc.getWidth()>=plT2.getX());
		} else {
			hit=(npc.getX()<plT2.getX()+plT2.getWidth());
		}
		if(hit) {
			plLife-=NPC_ATTACK;
		}
	}
	/**
	 * NPC defend himself against the attack from player
	 * @param direction
	 */
	private void defendNPC(String direction) {
		pause(100);
		drawDefendNPCFrontCe(direction);
		drawDefendNPCFront(direction);
		drawDefendNPCFrontCe(direction);
		drawNPCOriginalPosition(direction);
	}
	
	/**
	 * the gesture of player preparing to using airflow
	 * @param direction
	 */
	private void drawDefendNPCFrontCe(String direction) {
		remove(npc);
		npc=new GImage("NPCCeFront"+direction+".png");
		npc.scale(0.15,0.15);
		add(npc,npcX,npcY);
		pause(100);
	}
	
	/**
	 * the gesture of player using airflow
	 */
	private void drawDefendNPCFront(String direction) {
		remove(npc);
		npc=new GImage("NPCDa"+direction+".png");
		npc.scale(0.29,0.29);
		if(direction.equals("Left")) {
			add(npc,npcX-10,npcY);
		} else {
			add(npc,npcX,npcY);
		}
		pause(600);
	}
	
	
	/**
	 * player use airflow to attack the NPC
	 * @param direction
	 */
	private void plDaZhao(String direction) {
		if(airflow==null) {
			//player
			pause(100);
			addDaZhaoCharacterBack();
			addDaZhaoPrepare(direction);
			addDaZhao(direction);
			addDaZhaoPrepare(direction);
			addDaZhaoCharacterBack();
			addPlayerAfterAttack(direction);
			//effect
			addDaZhaoEffect(direction);
		}
	}
	
	/**
	 * the first gesture of player preparing to use hidden weapon
	 */
	private void addDaZhaoCharacterBack() {
		remove(plT2);
		plT2=new GImage("CharacterBack.png");
		plT2.scale(0.2,0.2);
		add(plT2,plT2X,plT2Y);
		pause(100);
	}
	
	/**
	 * the 2nd gesture of player preparing to use hidden weapon
	 */
	private void addDaZhaoPrepare(String direction) {
		remove(plT2);
		plT2=new GImage("DaZhaoPrepare"+direction+".png");
		plT2.scale(0.175,0.175);
		add(plT2,plT2X-35,plT2Y);
		pause(100);
	}
	
	/**
	 * the gesture of player using hidden weapon
	 * @param direction
	 */
	private void addDaZhao(String direction) {
		remove(plT2);
		plT2=new GImage("DaZhao"+direction+".png");
		plT2.scale(0.175,0.175);
		add(plT2,plT2X-80,plT2Y);
		pause(100);
	}
	
	/**
	 * the gesture of player right after attack
	 * @param direction
	 */
	private void addPlayerAfterAttack(String direction) {
		remove(plT2);
		plT2=new GImage("Character"+direction+".png");
		plT2.scale(0.2,0.2);
		add(plT2,plT2X,plT2Y);
	}
	
	/**
	 * the image of moving airflow
	 * @param direction
	 */
	private void addDaZhaoEffect(String direction) {
		//add the effect
			airflow=new GImage("Yinbo"+direction+".png");
			airflow.scale(0.1,0.1);
			double airflowX;
			if(direction.equals("Left")) {
				airflowX=plT2X-100;
			} else {
				airflowX=plT2X+plT2.getWidth()+100;
			}
			double airflowY=plT2Y+plT2.getY()/3.0;
			add(airflow,airflowX,airflowY);
			airflowX+=airflow.getWidth();
			airflowY+=airflow.getHeight()/2.0;
		
			//move the airflow
			AudioClip hitSound = MediaTools.loadAudioClip("hit.wav");
			hitSound.play();
			boolean hitNPC=false;
			for(int i=0;i<80;i++) {
				remove(airflow);
				airflow=new GImage("Yinbo"+direction+".png");
				airflow.scale(0.1*Math.pow(1.02,i),0.1*Math.pow(1.02,i));
				airflowX=getAirflowX(direction, i, airflowX);
				airflowY-=airflow.getHeight()/2.0;
				add(airflow,airflowX,airflowY);
				pause(DELAY*20);
				airflowX=retrieveAirflowX(direction, i, airflowX);
				airflowY+=airflow.getHeight()/2.0;
				//if the airflow hits the NPC
				if(!hitNPC) {
					hitNPC=checkAirflowHit(direction);
				} else {
					break;
				}
			}
			if(!hitNPC) {
				npcLife-=AIRFLOW_DAMAGE;
				remove(airflow);
				airflow=null;
			}
	}

	
	private double getAirflowX(String direction, int i, double airflowX) {
		if(direction.equals("Left")) {
			airflowX-=airflow.getWidth()+i+0.1*Math.pow(i,2);
		} else {
			airflowX-=airflow.getWidth()-i-0.1*Math.pow(i,2);
		}
		return airflowX;
	}
	
	private double retrieveAirflowX(String direction, int i, double airflowX) {
		if(direction.equals("Left")) {
			airflowX+=airflow.getWidth()+i+0.1*Math.pow(i,2);
		} else {
			airflowX+=airflow.getWidth()-i-0.1*Math.pow(i,2);
		}
		return airflowX;
	}
	
	/**
	 * check whether the airflow hit NPC at defending
	 * @param direction
	 * @return true if the NPC is defending
	 */
	private boolean checkAirflowHit(String direction) {
		double airflowFrontX;
		double airflowFrontY=airflow.getY()-airflow.getHeight()/2.0;;
		if (direction.equals("Left")) {
			airflowFrontX=airflow.getX();
		} else {
			airflowFrontX=airflow.getX()+airflow.getWidth();
		}
		GObject status=getElementAt(airflowFrontX,airflowFrontY);
		GImage defendPrepare1= new GImage("NPCCeFrontLeft.png");
		GImage defendPrepare2= new GImage("NPCCeFrontRight.png");
		GImage defend1=new GImage("NPCDaLeft.png");
		GImage defend2=new GImage("NPCDaRight.png");
		boolean npcStatus=false;
		if(status!=null) {
			npcStatus=(status.equals(defendPrepare1)) || (status.equals(defendPrepare2)) || (status.equals(defend1)) || (status.equals(defend2));
			if(npcStatus) {
				remove(airflow);
				airflow=null;
			} 
		}
		return npcStatus;
	}
	/**
	 * player throw hidden weapon at his enemy
	 * @param direction
	 */
	private void plXiaoZhao(String direction) {
		if(biao==null) {
			pause(100);
			addPlayerXiaoZhaoGesture(direction);
			addPlayerAfterAttack(direction);
			addXiaoZhaoEffect(direction);
		}
	}
	
	/**
	 * the gesture of player using the hidden weapon
	 * @param direction
	 */
	private void addPlayerXiaoZhaoGesture(String direction) {
		remove(plT2);
		plT2=new GImage("ZhaoShi"+direction+".png");
		plT2.scale(0.17,0.17);
		add(plT2,plT2X-50,plT2Y);
		pause(200);
	}
	
	/**
	 * the image of the moving hidden weapon
	 * @param direction
	 */
	private void addXiaoZhaoEffect(String direction) {
		//add effect
		biao=new GImage("Biao"+direction+".png");
		biao.scale(0.15,0.15);
		double biaoX;
		if (direction.equals("Left")) {
			biaoX=plT2X-120;
		} else {
			biaoX=plT2X+plT2.getWidth()+120;
		}
		add(biao,biaoX,plT2Y+plT2.getY()/6.0);
		
		//move effect
		AudioClip hitSound = MediaTools.loadAudioClip("hit.wav");
		hitSound.play();
		boolean hitNPC=false;
		for (int i=0;i<80;i++) {
			double x;
			if (direction.equals("Left")) {
				x=-2-0.01*Math.pow(i, 2);
			} else {
				x=2+0.01*Math.pow(i, 2);
			}
			biao.move(x, 0);
			pause(7);
			if(!hitNPC) {
				hitNPC=checkBiaoHit(direction);
			} else {
				break;
			}
		}
		if(!hitNPC) {
			npcLife-=BIAO_DAMAGE;
			remove(biao);
			biao=null;
		}
	}
	
	/**
	 * whether the hidden weapon hit NPC at defending
	 * @param direction
	 * @return true if hitting NPC at defending
	 */
	private boolean checkBiaoHit(String direction) {
		double biaoFrontX;
		double biaoFrontY=biao.getY()-biao.getHeight()/2.0;
		if (direction.equals("Left")) {
			biaoFrontX=biao.getX();
		} else {
			biaoFrontX=biao.getX()+biao.getWidth();
		}
		GObject status=getElementAt(biaoFrontX,biaoFrontY);
		GImage defendPrepare1= new GImage("NPCCeFrontLeft.png");
		GImage defendPrepare2= new GImage("NPCCeFrontRight.png");
		GImage defend1=new GImage("NPCDaLeft.png");
		GImage defend2=new GImage("NPCDaRight.png");
		boolean npcStatus=false;
		if(status!=null) {
			npcStatus= (status.equals(defendPrepare1)) || (status.equals(defendPrepare2)) || (status.equals(defend1)) || (status.equals(defend2));
			if(npcStatus) {
				remove(biao);
				biao=null;
			}
		}
		return npcStatus;
	}
	
	/**
	 * move the player according to which key the player press
	 * Also, prevent the player from going out of the wall
	 */
	private void movePlayer() {
		remove(plT2);
		if(left2) {
			addPlayerAfterAttack("Left");
			playerMoveLeft();
			while(left2) {
				boolean hitWall=checkPlayerHitWall(plT2);
				if(!hitWall) {
					playerMoveLeft();
				} 
			}
		} else if(right2) {
			addPlayerAfterAttack("Right");
			playerMoveRight();
			while(right2) {
				boolean hitWall=checkPlayerHitWall(plT2);
				if(!hitWall) {
					playerMoveRight();
				}
			}
		}
	}
	
	/**
	 * player move one step to the left
	 */
	private void playerMoveLeft() {
		plT2.move(-1, 0);
		plT2X--;
		pause(10);
	}
	
	/**
	 * player move one step to the right
	 */
	private void playerMoveRight() {
		plT2.move(1, 0);
		plT2X++;
		pause(10);
	}
	
	/**
	 * check whether player hit the wall
	 * @param role
	 * @return
	 */
	private boolean checkPlayerHitWall(GImage role) {
		boolean oppositeDirection=false;
		boolean hitWall=role.getX()<0 || role.getX()+role.getWidth()>getWidth();
		if(hitWall) {
			oppositeDirection=true;
		}
		return oppositeDirection;
	}
	
	/**
	 * after player finish the first two task, they go to the third scene
	 */
	private void goThirdScene() {
		goToTheHead();
		meetHead();
		winGame();
	}
	
	/**
	 * the setup of player on the way to meet the head
	 */
	private void goToTheHead() {
		removeAll();
		//add bg
		GImage bg=new GImage("BianHua6.jpg");
		add(bg,0,0);
		//add rain
		GImage rain=new GImage("Rain.png");
		rain.scale(0.45,0.45);
		add(rain,0,-700);
		//add cloud
		GImage wind=new GImage("Wind.png");
		wind.scale(0.4,0.4);
		add(wind,-wind.getWidth()+getWidth(),0);
		//move bg
		AudioClip rainSound = MediaTools.loadAudioClip("rain.wav");
		rainSound.play();
		for(int i=0;i<400;i++) {
			bg.move(-1,0);
			rain.move(0, 2);
			wind.move(1, 0);
			pause(20);
		}
		removeAll();
	}
	
	/**
	 * the setup of player meeting the head
	 */
	private void meetHead() {
		//add bg
		GImage bg=new GImage("BianHua4.jpg");
		bg.scale(0.27,0.27);
		add(bg);
		//add npc
		GImage npcT3=new GImage("NPCWestFront.png");
		npcT3.scale(0.5,0.5);
		add(npcT3,getWidth()/2,getHeight()/3);
		//add scroll
		GImage scroll=new GImage("Scroll.png");
		scroll.scale(0.4,0.34);
		add(scroll,80,100);
		//add prompt
		double x=scroll.getX()+50;
		double y=scroll.getY()+80;
		double lineHeight=30;
		createPrompt("Thank you for delivering",x,y);
		createPrompt("the scroll. You are now",x,y+lineHeight);
		createPrompt("qualified to join us.",x,y+2*lineHeight);
		createPrompt("Welcome to ChunYang.",x,y+3*lineHeight);
		createPrompt("Let's defeat LuShan",x,y+4*lineHeight);
		createPrompt("together in the future.",x,y+5*lineHeight);
	}
	
	/**
	 * if player doesn't win the game
	 */
	private void goDie() {
		GImage bg=new GImage("BianHua8.jpg");
		bg.scale(0.8,0.8);
		add(bg);
		GImage gameOver=new GImage("GameOver.png");
		gameOver.scale(0.6,0.6);
		add(gameOver,(getWidth()-gameOver.getWidth())/2.0,(getHeight()-gameOver.getHeight())/2.0);
		AudioClip die = MediaTools.loadAudioClip("die.wav");
		die.play();
	}
	
	/**
	 * if player win the game
	 */
	private void winGame() {
		waitForClick();
		removeAll();
		
		GImage bg=new GImage("BianHua3.jpg");
		bg.scale(0.46,0.46);
		add(bg);
		
		GImage thanks=new GImage("Thanks.png");
		thanks.scale(0.2,0.2);
		add(thanks,(getWidth()-thanks.getWidth())/2.0,(getHeight()-thanks.getHeight())/2.0);
		
		AudioClip softSound = MediaTools.loadAudioClip("soft.wav");
		softSound.play();
		firstClicked=false;
		createEffect();
	}
}