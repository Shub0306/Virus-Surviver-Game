package SV.main.entity.powerUp;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;

public class Flower extends Entity{

	public Flower(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.flower.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
		
	}

	public void update() {
		
	}

}
