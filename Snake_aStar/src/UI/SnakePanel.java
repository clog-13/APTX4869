package UI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import AI.SnakeAi;
import Mode.Node;
import Mode.Snake;

public class SnakePanel extends JPanel implements Runnable{

	Snake snake;
	SnakeAi ai;

	boolean END = false;

	public SnakePanel() {
		snake = new Snake();
		Node node = new Node(10, 10);  // 蛇的初始位置
		snake.getList().add(node);
		snake.setFirst(node);
		snake.setLast(node);
		snake.setTail(new Node(0,10));
		snake.setFood(new Node(80,80));  // 食物初始位置
		ai = new SnakeAi();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.pink);
		g.drawRect(10, 10, Snake.map_size, Snake.map_size);
		
		g.setColor(Color.white);	
		paintSnake(g,snake);
		
		g.setColor(Color.white);
		paintFood(g,snake.getFood());
		

		int dir = ai.play2(snake, snake.getFood()); // 选择策略
		if (dir == -1) {
			END = true;
			System.out.println("GG");
		} else {
			snake.move(dir);
		}
	}

	public void paintSnake(Graphics g, Snake snake) {
		for (Node node : snake.getList()) {
			if (node.equals(snake.getFirst())) g.setColor(Color.green);
			if (node.equals(snake.getLast()) &&
					!snake.getFirst().equals(snake.getLast())) {
				g.setColor(Color.blue);
			}

			g.fillRect(node.getX(), node.getY(), Snake.size, Snake.size);
			g.setColor(Color.white);
		}
	}

	public void paintFood(Graphics g,Node food){
		g.setColor(Color.red);
		g.fillOval(food.getX(), food.getY(), Snake.size, Snake.size);
	}
	
	@Override
	public void run() {
		while (!END) {
			try {
				Thread.sleep(30);
				this.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
