package SV.main.tile;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.powerUp.Flower;
import SV.main.entity.powerUp.GrowUp;
import SV.main.entity.powerUp.PowerStar;
import SV.main.gfx.Sprite;

public class PowerUpBlock extends Tile{
	
	private Sprite powerUp;
	
	private boolean poppedUp = false;
	
	private int spriteY = getY();
	private int type;

	public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, int type) {
		super(x, y, width, height, solid, id, handler);
		this.powerUp = powerUp;
		this.type = type;
	}
	
	public void render(Graphics g) {
		
		if(!poppedUp) g.drawImage(powerUp.getBufferedImage(), x, spriteY, getWidth(), getHeight(), null);
		if(!activated) g.drawImage(Game.powerUp.getBufferedImage(), x, y, getWidth(), getHeight(), null);
		else g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, getWidth(), getHeight(), null);
		
	}
	
	public void update() {
		
		if(activated&&!poppedUp) {
			spriteY--;                                                          
            if(spriteY<=y-getHeight()) {
            	if(powerUp==Game.growUp||powerUp==Game.lifeUp) {
            		handler.addEntity(new GrowUp (x, spriteY, getWidth(), getHeight(), Id.growUp, handler, type));
            	}
            	else if(powerUp==Game.flower) {
            		handler.addEntity(new Flower (x, spriteY, getWidth(), getHeight(), Id.flower, handler));
            	}
            	else if(powerUp==Game.star) {
            		handler.addEntity(new PowerStar (x, spriteY, getWidth(), getHeight(), Id.star, handler));
            	}
            	
            	poppedUp = true;
            }
		}
		
	}

}
