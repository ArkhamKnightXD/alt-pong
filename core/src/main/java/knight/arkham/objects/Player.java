package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
public class Player extends GameObject {
    public int score;
    private final boolean isPlayer1;

    public Player(Rectangle rectangle, boolean isPlayer1) {
        super(rectangle, "images/players.png", 500);
        this.isPlayer1 = isPlayer1;
    }

    public void update(float deltaTime) {

        boolean hasTopCollision = actualBounds.y > 840;
        boolean hasBottomCollision = actualBounds.y < 380;

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
}
