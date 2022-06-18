package SV.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import SV.main.entity.Entity;
import SV.main.gfx.Sprite;
import SV.main.gfx.SpriteSheet;
import SV.main.gfx.gui.Launcher;
import SV.main.input.KeyInput;
import SV.main.input.MouseInput;
import SV.main.tile.Tile;

public class Game extends Canvas implements Runnable {
	
    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final int SCALE = 4;
    public static final String TITLE = "VIRUS SURVIVOR";

    private Thread thread;
    private boolean running = false; 
    
    private static BufferedImage[] levels;
    
    private static BufferedImage menu;
    private static BufferedImage background;
    
    private static int playerX, playerY;
    public static int level = 0;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;
    
    public static int coins = 0;
    public static int lives = 5;
    public static int deathScreenTime = 0;
    public static int deathY = 0;
    
    public static boolean showDeathScreen = true;
    public static boolean gameOver = false;
    public static boolean playing = false;
    
    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite growUp;
    public static Sprite lifeUp;
    public static Sprite pipe;
    public static Sprite coin;
    public static Sprite plant;
    public static Sprite star; 
    public static Sprite fireball; 
    public static Sprite flower;
    
    public static Sprite start;
    public static Sprite goal;
    
    public static Sprite[] player;
    public static Sprite[] fireplayer;
    public static Sprite[] monster;
    public static Sprite[] bat;
    public static Sprite[] snail;
    public static Sprite[] flag;
    public static Sprite[] particle;
    
    public static Sound jump;
    public static Sound damage;
    public static Sound death;
    public static Sound power;
    public static Sound win;
    public static Sound themeSong;
    
    private void init() {                                                       
    
        handler = new Handler();
        
        sheet = new SpriteSheet("/spritesheet.png");
        
        cam = new Camera();
        launcher = new Launcher();
        mouse = new MouseInput();
        
        addKeyListener(new KeyInput());
        addMouseListener(mouse);                                                 //two new mouse input sender
        addMouseMotionListener(mouse);
        
        grass = new Sprite(sheet, 2, 1);
        growUp = new Sprite(sheet, 1, 5);
        lifeUp = new Sprite(sheet, 2, 5);
        powerUp = new Sprite(sheet, 5, 1);
        usedPowerUp = new Sprite(sheet, 6, 1);
        pipe = new Sprite(sheet, 2, 8);
        coin = new Sprite(sheet, 7, 1);
        plant = new Sprite(sheet, 1, 8);
        star = new Sprite(sheet, 9, 1);
        flower = new Sprite(sheet, 10, 1);
        fireball = new Sprite(sheet, 11, 1);
        
        start = new Sprite(sheet, 2, 7);
        goal = new Sprite(sheet, 1, 7);
        
        
        player = new Sprite[16];
        fireplayer = new Sprite[16];
        monster = new Sprite[12];
        bat = new Sprite[10];
        snail = new Sprite[10];
        flag = new Sprite[3];
        particle = new Sprite[6];
        
        levels = new BufferedImage[3];
        
        for (int i=0;i<player.length;i++){
            player[i] = new Sprite(sheet,i+1,15);
        }
        
        for (int i=0;i<fireplayer.length;i++){
            fireplayer[i] = new Sprite(sheet,i+1,16);
        }
        
        for (int i=0;i<monster.length;i++){
            monster[i] = new Sprite(sheet,i+1,13);
        }
        
        for (int i=0;i<bat.length;i++){
            bat[i] = new Sprite(sheet,i+1,11);
        }
        
        for (int i=0;i<snail.length;i++){
            snail[i] = new Sprite(sheet,i+1,12);
        }
        
        for(int i = 0;i<flag.length;i++) {
            flag[i] = new Sprite(sheet,i+1,3);
        }
        
        for(int i = 0;i<particle.length;i++) {
            particle[i] = new Sprite(sheet,i+1,2);
        }
        
        try {
			levels[0] = ImageIO.read(getClass().getResource("/level.png"));
			levels[1] = ImageIO.read(getClass().getResource("/level2.png"));
			levels[2] = ImageIO.read(getClass().getResource("/demo.png"));
            background = ImageIO.read(getClass().getResource("/background.png"));
			menu = ImageIO.read(getClass().getResource("/menu.png"));
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
        
        jump = new Sound("/audio/jump.wav");
        damage = new Sound("/audio/damage.wav");
        death = new Sound("/audio/death.wav");
        power = new Sound("/audio/power.wav");
        win = new Sound("/audio/win.wav"); 
        themeSong = new Sound("/audio/ThemeSong.wav");
        
    }
    
    private synchronized void start() {                                         
    	
        if(running) return;
        running = true;                                                           
        thread = new Thread(this, "Thread");
        thread.start();
    	
    }
    
    private synchronized void stop() {                                          
    
        if(!running) return;  
        running = false;
        try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    	
    }
    
    public void run() {
    	
    	init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int updates = 0;
        while(running) {
        	long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;
            while(delta>=1) {
                update();
                updates++;
                delta--;
            }
         render();
         frames++;
         
         if(System.currentTimeMillis()-timer>1000) {
             timer+=1000;
             System.out.println(frames + " Frames Per Seconds " + 
                     updates + " Updates Per Seconds");                         
             frames = 0;
             updates = 0;
         }
        	
        }

        stop();
    	
    }
    
    public void render() {                                                      
    	
        BufferStrategy bs = getBufferStrategy();
        if(bs==null) {
            createBufferStrategy(3);                                            
            return;
        }
        
        Graphics g = bs.getDrawGraphics();   
        
        if(!showDeathScreen) {
        	g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
        	g.setColor(Color.black);                                                
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if(showDeathScreen) {
        	if(!gameOver) {
        		g.setColor(Color.white);
                g.setFont(new Font("Courier",Font.BOLD,50));
                g.drawImage(player[0].getBufferedImage(), 500, 300, 100, 100, null);
                g.drawString("x" + lives, 610, 400);
        	} else {
        		g.setColor(Color.red);
                g.setFont(new Font("Courier",Font.BOLD,50));
                g.drawString("GAME OVER :-( ", 610, 400);
        	}
        }
        
        if(playing) g.translate(cam.getX(), cam.getY());
        if(!showDeathScreen&&playing) handler.render(g);
        else if(!playing) launcher.render(g);
        g.dispose();
        bs.show();
    	
    }
    
    public void update() {                                                     
    
    	if(playing) handler.update();
    	
    	for(int i = 0;i<handler.entity.size();i++) {                          
            Entity en = handler.entity.get(i);
            if(en.getId()==Id.player) {
            	if(!en.goingDownPipe) cam.update(en);
            }
    	}
    	
    	if(showDeathScreen&&!gameOver&&playing) deathScreenTime++;
    	if(deathScreenTime>=180) {
    		deathScreenTime = 0;
    		handler.clearLevel();
    		handler.createLevel(levels[level]);
    		showDeathScreen = false;
    		
    		themeSong.play();
    	}
    	
    }
    
    public static int getFrameWidth() {
        return WIDTH*SCALE;
    }

    public static int getFrameHeight() {
        return HEIGHT*SCALE;
    }
    
    public static void switchLevel() {
        Game.level++;
	    handler.clearLevel();
        handler.createLevel(levels[level]);
        Game.themeSong.play();
        Game.win.play();
    }
    
    public static Rectangle getVisibleArea() {
        for(int i = 0;i<handler.entity.size();i++) {
            Entity en = handler.entity.get(i);
            if(en.getId()==Id.player) {
                if(!en.goingDownPipe) {
                playerX = en.getX();
                playerY = en.getY();
                
                return new Rectangle(playerX
                    -(getFrameWidth()/2-5),playerY-(getFrameHeight()/2-5), 
                    getFrameWidth()+10, getFrameHeight()+10);                    
                }
            }
        }
        return new Rectangle(playerX
                    -(getFrameWidth()/2-5),playerY-(getFrameHeight()/2-5), 
                    getFrameWidth()+10, getFrameHeight()+10);
    }
    
    public static int getDeathY() {                                             
        LinkedList<Tile> tempList = handler.tile;
        
        Comparator<Tile> tileSorter = new Comparator<Tile>() {
            public int compare(Tile t1, Tile t2) {
                if(t1.getY()>t2.getY()) return -1;
                if(t1.getY()<t2.getY()) return 1;
                return 0;
            }
            
        };
        
        Collections.sort(tempList,tileSorter);
        return tempList.getFirst().getY() + tempList.getFirst().getHeight();  
        
    }

    
public Game() {                                                                 
        
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        
    }

public static void main(String[] args) {
	
    Game game = new Game();
    JFrame frame = new JFrame(TITLE);                                           
    frame.add(game);
    frame.pack();
    frame.setResizable(false);                                                 
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    game.start();

}

}
