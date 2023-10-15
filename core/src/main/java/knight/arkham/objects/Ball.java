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

        actualBounds.x = initialPosition.x;
        actualBounds.y = initialPosition.y;
    }

    public void update(float deltaTime){

        actualBounds.x += velocity.x * actualSpeed * deltaTime;
        actualBounds.y += velocity.y * actualSpeed * deltaTime;

        boolean hasTopCollision = actualBounds.y > 885;
        boolean hasBottomCollision = actualBounds.y < 380;

        if (hasTopCollision || hasBottomCollision)
            reverseVelocityY();

        if (actualBounds.x > 1450){
            gameScreen.getPlayer().score += 1;
            resetBallPosition();
        }

        if (actualBounds.x < 470){
            gameScreen.getEnemy().score += 1;
            resetBallPosition();
        }
    }

    public void hasCollision(Rectangle playerBounds){

        boolean hasCollision = actualBounds.overlaps(playerBounds);

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
