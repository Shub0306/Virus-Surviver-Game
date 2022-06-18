package SV.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import SV.main.entity.Coin;
import SV.main.entity.Entity;
import SV.main.entity.mob.Bat;
import SV.main.entity.mob.Monster;
import SV.main.entity.mob.Player;
import SV.main.entity.mob.Snail;
import SV.main.tile.Flag;
import SV.main.tile.Info;
import SV.main.tile.Pipe;
import SV.main.tile.PowerUpBlock;
import SV.main.tile.Tile;
import SV.main.tile.Wall;

public class Handler {
	
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public void render(Graphics g) {
    	
        for(int i=0;i<entity.size();i++) {
            Entity en = entity.get(i);
            if(Game.getVisibleArea()!=null&&
                    en.getBounds().intersects(Game.getVisibleArea())&&en.getId()!=Id.particle) en.render(g);
        }
        
        for(int i=0;i<tile.size();i++) {
            Tile ti = tile.get(i);
            if(Game.getVisibleArea()!=null&&
                    ti.getBounds().intersects(Game.getVisibleArea())) ti.render(g);
        }
        
        for(int i=0;i<entity.size();i++) {
            Entity en = entity.get(i);
            if(Game.getVisibleArea()!=null&&
            		en.getBounds().intersects(Game.getVisibleArea())&&en.getId()==Id.particle) en.render(g);
        }
        
        g.drawImage(Game.coin.getBufferedImage(), Game.getVisibleArea().x+20, Game.getVisibleArea().y+20, 75, 75, null);
        g.setColor(Color.black);
        g.setFont(new Font("Courier",Font.BOLD,20));
        g.drawString("x" + Game.coins, Game.getVisibleArea().x+100, Game.getVisibleArea().y+95); 
    	
    }
    
    public void update() {
    	
        for(int i=0;i<entity.size();i++) {
            Entity en = entity.get(i);
            en.update();
        }
        
        for(int i=0;i<tile.size();i++) {
            Tile ti = tile.get(i);
            if(Game.getVisibleArea()!=null&&
                    ti.getBounds().intersects(Game.getVisibleArea())) ti.update();
        }
    	
    }
    
    public void addEntity(Entity en) {
        entity.add(en);
    }
    
    public void removeEntity(Entity en) {
        entity.remove(en);
    }
    
    public void addTile(Tile ti) {
        tile.add(ti);
    }
    
    public void removeTile(Tile ti) {
        tile.remove(ti);
    }
    
    public void createLevel(BufferedImage level) {
    	
    	int width = level.getWidth();
        int height = level.getHeight();
        
        for(int y=0;y<height;y++) { 
        	for(int x=0;x<width;x++) {
        		int pixel = level.getRGB(x, y);
        		
        		int red = (pixel >> 16) & 0xff;                            
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                if(red==255&&green==255&&blue==255) addTile (new Wall (x*64, y*64, 64, 64, true, Id.wall, this ));
                if(red==0&&green==0&&blue==255) addEntity (new Player (x*64, y*64, 64, 64, Id.player, this ));
                if(red==255&&green==119&&blue==0) addEntity (new Monster (x*64, y*64, 85, 85, Id.monster, this ));
                if(red==68&&green==0&&blue==43) addEntity (new Snail (x*64, y*64, 85, 85, Id.snail, this ));
                if(red==255&&green==255&&blue==0) addTile (new PowerUpBlock (x*64, y*64, 64, 64, true, Id.powerUp, this, Game.growUp, 0 ));
                if(red==255&&green==245&&blue==0) addTile (new PowerUpBlock (x*64, y*64, 64, 64, true, Id.powerUp, this, Game.lifeUp, 1 ));
                if(red==0&&(green>=125&&green<=128)&&blue==0) addTile (new Pipe (x*64, y*64, 64, 64*16, true, Id.pipe, this, 128-green, true ));
                if(red==255&&green==250&&blue==0) addEntity (new Coin (x*64, y*64, 64, 64, Id.coin, this ));
                if(red==128&&green==0&&blue==128) addEntity (new Bat (x*64, y*64, 64, 64, Id.bat, this, 3));
                if(red==120&&green==230&&blue==220) addTile (new Flag (x*64, y*64, 64, 64*7, true, Id.flag, this ));
                if(red==70&&green==54&&blue==38) addTile (new Info (x*64, y*64, 64, 64, true, Id.start, this, 0 ));
                if(red==255&&green==222&&blue==173) addTile (new Info (x*64, y*64, 64, 64, true, Id.goal, this, 1 ));
                if(red==70&&green==70&&blue==70) addTile(new PowerUpBlock (x*64, y*64, 64, 64, true, Id.powerUp, this, Game.star, 0 ));
                if(red==40&&green==0&&blue==68) addTile(new PowerUpBlock (x*64, y*64, 64, 64, true, Id.powerUp, this, Game.flower, 0 ));
        	}
    	}
        
        Game.deathY = Game.getDeathY();
    	
    }
    
    public void clearLevel() {
    	
    	entity.clear();
    	tile.clear();
    	
    }
   
}
