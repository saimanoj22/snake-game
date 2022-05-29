package snakeGame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    
    Snake snake = new Snake();
    SnakeUnit unit = new SnakeUnit(0, 0);
    Food food = new Food();
    static int width = 20;
    static int height = 20;
    static int unitSize = 25;
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    public enum Dir{
        left, right, up, down
    }
    
    public static void main(String[] args) {
        launch(App.class);
    }
    
    @Override
    public void start(Stage window) throws Exception {
        food.newFood(snake, width, height);
        VBox root = new VBox();
        Canvas canvas = new Canvas(width * unitSize, height * unitSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        animationTimer(gc);
        Scene scene = new Scene(root, width * unitSize, height * unitSize);
        setControls(scene);
        addInitialUnits();
        window.setScene(scene);
        window.setTitle("Snake");
        window.show();
    }
    
    public void animationTimer(GraphicsContext gc){
        new AnimationTimer(){
            long lastTick = 0;
            
            public void handle(long now){
                if(lastTick == 0){
                    lastTick = now;
                    tick(gc);
                    return;
                }
                if(now - lastTick > 1000000000 / snake.getSpeed()){
                    lastTick = now;
                    tick(gc);
                }
            }
        }.start();
    }
    
    public void setControls(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode() == KeyCode.UP && direction != Dir.down){
                    direction = Dir.up;
            }
            if(key.getCode() == KeyCode.LEFT && direction != Dir.right){
                direction = Dir.left;
            }
            if(key.getCode() == KeyCode.DOWN && direction!= Dir.up){
                direction = Dir.down;
            }
            if(key.getCode() == KeyCode.RIGHT && direction != Dir.left){
                direction = Dir.right;
            }
        });
    }
    
    public void addInitialUnits(){
        snake.addUnit(new SnakeUnit(width / 2, height / 2));
        snake.addUnit(new SnakeUnit(width / 2, height / 2));
        snake.addUnit(new SnakeUnit(width / 2, height / 2));
    }
    
    public void tick(GraphicsContext gc){
        if(gameOver){
            showGameOver(gc);
            return;
        }
        snakeBodyFollowsHead();
        changeDirection();
        eatFood();
        checkSelfDestroy();
        Color color = food.setRandomColor();
        fillGraphicalContent(gc, color);
        colorSnake(gc);
    }
    
    public void showGameOver(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.setFont(new Font("", 50));
        gc.fillText("GAME OVER", 100, 250);
    }
    
    public void snakeBodyFollowsHead(){
        for(int i = snake.getSnake().size() - 1; i >= 1; i--){
            snake.getUnit(i).setX(snake.getUnit(i - 1).getX());
            snake.getUnit(i).setY(snake.getUnit(i - 1).getY());
        }
    }
    
    public void changeDirection(){
        switch(direction){
            case up:
                snake.goUp();
                if(snake.getUnit(0).getY() < 0){
                    gameOver = true;
                }
                break;
            case down:
                snake.goDown();
                if(snake.getSnake().get(0).getY() > height){
                    gameOver = true;
                }
                break;
            case left:
                snake.goLeft();
                if(snake.getSnake().get(0).getX() < 0){
                    gameOver = true;
                }
                break;
            case right:
                snake.goRight();
                if(snake.getSnake().get(0).getX() > width){
                    gameOver = true;
                }
                break;
        }
    }
    
    public void eatFood(){
        if(food.getX() == snake.getUnit(0).getX() && food.getY() == snake.getUnit(0).getY()){
            snake.addUnit(new SnakeUnit(-1, -1));
            food.newFood(snake, width, height);
        }
    }
    
    public void checkSelfDestroy(){
        for(int i = 1; i < snake.getSnake().size(); i++){
            if(snake.getUnit(0).getX() == snake.getUnit(i).getX() && snake.getUnit(0).getY() == snake.getUnit(i).getY()){
                gameOver = true;
            }
        }
    }
    
    public void fillGraphicalContent(GraphicsContext gc, Color color){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width*unitSize, height*unitSize);
        
        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (snake.getSpeed() - 6), 10, 30);
        
        gc.setFill(color);
        gc.fillOval(food.getX()*unitSize, food.getY()*unitSize, unitSize, unitSize);
    }
    
    public void colorSnake(GraphicsContext gc){
        for(SnakeUnit c : snake.getSnake()){
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(c.getX() * unitSize, c.getY() * unitSize, unitSize - 1, unitSize - 1);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.getX() * unitSize, c.getY() * unitSize, unitSize - 2, unitSize - 2);
        }
    }
}