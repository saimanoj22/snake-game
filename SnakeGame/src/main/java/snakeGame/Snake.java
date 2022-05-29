package snakeGame;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeUnit> snake;
    private int speed;
    
    public Snake(){
        this.snake = new ArrayList<>();
        this.speed = 5;
    }
    
    public void addUnit(SnakeUnit snakeUnit){
        this.snake.add(snakeUnit);
    }

    public List<SnakeUnit> getSnake() {
        return snake;
    }

    public void setSnake(List<SnakeUnit> snake) {
        this.snake = snake;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void increaseSpeed(){
        this.speed++;
    }
    
    public SnakeUnit getUnit(int i){
        return this.snake.get(i);
    }
    
    public void goUp(){
        this.snake.get(0).setY(snake.get(0).getY() - 1);
    }
    
    public void goDown(){
        this.snake.get(0).setY(snake.get(0).getY() + 1);
    }
    
    public void goLeft(){
        this.snake.get(0).setX(snake.get(0).getX() - 1);
    }
    
    public void goRight(){
        this.snake.get(0).setX(snake.get(0).getX() + 1);
    }
}
