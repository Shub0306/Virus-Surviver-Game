package SV.main.entity;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;

public class Coin extends Entity{

	public Coin(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.coin.getBufferedImage(), x, y, getWidth(), getHeight(), null);
		
	}

	public void update() {
		
	}

}
