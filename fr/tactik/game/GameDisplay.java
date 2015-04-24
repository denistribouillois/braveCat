package fr.tactik.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This is the GameDisplay Class. This class permit to display all the entity of the game.
 * 
 * @author Juliette Belin, Alice Neichols, Denis Tribouillois 
 * @version 1.0
 */

public class GameDisplay extends JPanel implements Runnable {
	//hero
	//monde
	//controler de jeu
	//controler de menu

	static String rootdir = System.getProperty("user.dir");
	
	Image background;
	Vector<Mobile> mobiles;
	Vector<Still> stills;
	int offsetX=0;
	int offsetY=0;
	
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	int lastFpsTime =0;
    int fps = 0;
	
	public void initGame() {
		
		mobiles = new Vector<Mobile>();
		stills = new Vector<Still>();
		
		Player player = new Player(10,10,50,50,false, rootdir + "/images/game/player/",1, 1);
		MobilePlat mobile1 = new MobilePlat(600,600,50,50,false, rootdir + "/images/game/mobile1/",2, 1);
		MobilePlat mobile2 = new MobilePlat(400,400,50,50,false, rootdir + "/images/game/mobile2/",0, 1);
		
		mobiles.add (player);
		mobiles.add (mobile1);
		mobiles.add (mobile2);
		
		StillPlat still1 = new StillPlat(200,200,50,50,false, rootdir + "/images/game/still1/",0, 1);
		StillPlat still2 = new StillPlat(300,300,50,50,false, rootdir + "/images/game/still2/",0, 1);
		
		stills.add (still1);
		stills.add (still2);
	}
	
	public void gameUpdate() {
		mobiles.get(0).moveRight(1);
		mobiles.get(1).moveLeft(1);
		mobiles.get(2).moveUp(1);
		//entities.get(0).setPosX((entities.get(0).getPosX() + 1)%800 );
	}
	
	public void render() {
		repaint();
	}
	
	
	public GameDisplay() {
		// TODO Auto-generated constructor stub
		initGame();	
	}
	
	
	
	public void setBackground(String path){
		  // load the background image
        try {
        	background = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	
	@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    	// Background
	    	g.drawImage(background,0,0,null);
    		
	    	// Mobile elements
	    	for (int i = 0; i < mobiles.size(); i++){
	    		g.drawImage(mobiles.get(i).getCurrentTexture(),(int)mobiles.get(i).getPosX()+offsetX,(int)mobiles.get(i).getPosY()+offsetY,null);
	    	}
	    	
	    	// Still elements
	    	for (int i = 0; i < stills.size(); i++){
	    		g.drawImage(stills.get(i).getCurrentTexture(),(int)stills.get(i).getPosX()+offsetX,(int)stills.get(i).getPosY()+offsetY,null);
	    	}

	}

	@Override
	public void run() {
		
		while (true){
			 long now = System.nanoTime();
		     long updateLength = now - lastLoopTime;
		     lastLoopTime = now;
		     double delta = updateLength / ((double)OPTIMAL_TIME);

		      // update the frame counter
		      lastFpsTime += updateLength;
		      fps++;
		      
		      // update our FPS counter if a second has passed since
		      // we last recorded
		      if (lastFpsTime >= 1000000000)
		      {
		         System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
		      if (lastFpsTime >= 1000000000)
		      {
		         System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
			gameUpdate();
			render();
			
			try {
				Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
