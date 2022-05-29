package snakeGame;

import java.util.Random;
import javafx.scene.paint.Color;

public class Food {
    private int x, y;
    private int foodColor;

    public Food() {
        this.x = 0;
        this.y = 0;
        this.foodColor = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFoodColor() {
        return foodColor;
    }

    public void setFoodColor(int foodColor) {
        this.foodColor = foodColor;
    }
    
    public Color setRandomColor(){
        Color color = Color.WHITE;
        switch(getFoodColor()){
            case 0: color = Color.PURPLE;
            break;
            case 1: color = Color.LIGHTBLUE;
            break;
            case 2: color = Color.YELLOW;
            break;
            case 3: color = Color.PINK;
            break;
            case 4: color = Color.ORANGE;
            break;
        }
        return color;
    }
    public void newFood(Snake snake, int width, int height){
        Random rand = new Random();
        start:
        while(true){
            setX(rand.nextInt(width));
            setY(rand.nextInt(height)) ;
            
            for(SnakeUnit c : snake.getSnake()){
                if(c.getX() == getX() && c.getY() == getY()){
                    continue start;
                }
            }
            setFoodColor(rand.nextInt(5));
            snake.increaseSpeed();
            break;
        }
    }
}
