package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
public class Player extends GameObject {
    public int score;
    private final boolean isPlayer1;
    private boolean hasTopCollision;
    private boolean hasBottomCollision;

    public Player(Rectangle rectangle, boolean isPlayer1) {
        super(rectangle, "images/players.png", 500);
        this.isPlayer1 = isPlayer1;
    }

    public void update(float deltaTime) {

        if (isPlayer1) {

            if (!hasTopCollision && Gdx.input.isKeyPressed(Input.Keys.W))
                bounds.y += actualSpeed * deltaTime;

            else if (!hasBottomCollision && Gdx.input.isKeyPressed(Input.Keys.S))
                bounds.y -= actualSpeed * deltaTime;
        }
        else {
            if (!hasTopCollision && Gdx.input.isKeyPressed(Input.Keys.UP))
                bounds.y += actualSpeed * deltaTime;

            else if (!hasBottomCollision &&  Gdx.input.isKeyPressed(Input.Keys.DOWN))
                bounds.y  -= actualSpeed * deltaTime;
        }
    }

    public void hasCollision(Rectangle topCollisionBounds, Rectangle bottomCollisionBounds){

        hasTopCollision = bounds.overlaps(topCollisionBounds);
        hasBottomCollision = bounds.overlaps(bottomCollisionBounds);
    }
}
