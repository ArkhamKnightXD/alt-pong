package knight.arkham.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import knight.arkham.screens.GameScreen;

public class Ball extends GameObject {
    private final Vector2 velocity;
    private final GameScreen gameScreen;
    private final Vector2 initialPosition;

    public Ball(Rectangle rectangle, GameScreen gameScreen) {
        super(rectangle, "images/white.png", 300);

        this.gameScreen = gameScreen;
        velocity = new Vector2(getRandomDirection(), getRandomDirection());
        initialPosition = new Vector2(rectangle.x, rectangle.y);
    }

    private float getRandomDirection(){

        return (Math.random() < 0.5) ? 1 : -1;
    }

    private void resetBallPosition(){
        velocity.set(getRandomDirection(), getRandomDirection());

        bounds.x = initialPosition.x;
        bounds.y = initialPosition.y;
    }

    public void update(float deltaTime){

        bounds.x += velocity.x * actualSpeed * deltaTime;
        bounds.y += velocity.y * actualSpeed * deltaTime;

        boolean hasTopCollision = bounds.y > 885;
        boolean hasBottomCollision = bounds.y < 380;

        if (hasTopCollision || hasBottomCollision)
            reverseVelocityY();

        if (bounds.x > 1450){
            gameScreen.getPlayer().score += 1;
            resetBallPosition();
        }

        if (bounds.x < 470){
            gameScreen.getEnemy().score += 1;
            resetBallPosition();
        }
    }

    public void hasCollision(Rectangle playerBounds){

        boolean hasCollision = bounds.overlaps(playerBounds);

        if (hasCollision)
            reverseVelocityX();
    }

    public void reverseVelocityX(){
        velocity.x *= -1;
    }
    public void reverseVelocityY(){
        velocity.y *= -1;
    }
}
