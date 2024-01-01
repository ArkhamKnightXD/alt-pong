package knight.arkham.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import knight.arkham.screens.GameScreen;

public class Ball extends GameObject {
    private final Vector2 velocity;
    private final GameScreen gameScreen;
    private final Vector2 initialPosition;
    private final int radius;

    public Ball(Vector2 position, GameScreen gameScreen) {
        super(new Rectangle(position.x, position.y, 20, 20), 300);

        this.gameScreen = gameScreen;
        velocity = new Vector2(getRandomDirection(), getRandomDirection());
        initialPosition = position;
        radius = 12;
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

        boolean hasTopCollision = actualBounds.y > 640 - radius;
        boolean hasBottomCollision = actualBounds.y < radius;

        if (hasTopCollision || hasBottomCollision)
            reverseVelocityY();

        if (actualBounds.x > 960){
            gameScreen.getPlayer().score += 1;
            resetBallPosition();
        }

        if (actualBounds.x < 0){
            gameScreen.getEnemy().score += 1;
            resetBallPosition();
        }
    }

    public void hasCollision(Rectangle playerBounds){

        boolean hasCollision = circleRectCollision(new Vector2(actualBounds.x, actualBounds.y), playerBounds);

        if (hasCollision)
            reverseVelocityX();
    }

    //This check the collision between circle and rectangle, it works kind of well, but still has room for improvement.
    public Boolean circleRectCollision(Vector2 circlePosition, Rectangle bounds) {

        float distanceY = Math.abs(circlePosition.y - bounds.y);

        if (distanceY > (bounds.height / 2 + radius))
            return false;

        float distanceX = Math.abs(circlePosition.x - bounds.x);

        if (distanceX > (bounds.width / 2 + radius))
            return false;

        if (distanceX <= (bounds.width / 2))
            return true;

        if (distanceY <= (bounds.height / 2))
            return true;

        float a = distanceX - bounds.width / 2;
        float b = distanceY - bounds.height / 2;

        float cSqr = a * a + b * b;

        return (cSqr <= (radius * radius));
    }

    public void draw(ShapeRenderer shape) {
        shape.circle(actualBounds.x, actualBounds.y, radius);
    }

    public void reverseVelocityX(){
        velocity.x *= -1;
    }
    public void reverseVelocityY(){
        velocity.y *= -1;
    }
}
