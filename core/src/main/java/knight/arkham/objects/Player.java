package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
public class Player extends GameObject {
    public int score;
    private final boolean isPlayer1;

    public Player(float positionX, float positionY, boolean isPlayer1) {
        super(new Rectangle(positionX, positionY, 16, 64), 500);
        this.isPlayer1 = isPlayer1;
    }

    public void update(float deltaTime) {

        boolean hasTopCollision = actualBounds.y > 640 - actualBounds.height;
        boolean hasBottomCollision = actualBounds.y < 0;

        if (isPlayer1) {

            if (!hasTopCollision && Gdx.input.isKeyPressed(Input.Keys.W))
                actualBounds.y += actualSpeed * deltaTime;

            else if (!hasBottomCollision && Gdx.input.isKeyPressed(Input.Keys.S))
                actualBounds.y -= actualSpeed * deltaTime;
        }
        else {
            if (!hasTopCollision && Gdx.input.isKeyPressed(Input.Keys.UP))
                actualBounds.y += actualSpeed * deltaTime;

            else if (!hasBottomCollision &&  Gdx.input.isKeyPressed(Input.Keys.DOWN))
                actualBounds.y  -= actualSpeed * deltaTime;
        }
    }

    public void draw(ShapeRenderer shape) {
        shape.rect(actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }
}
