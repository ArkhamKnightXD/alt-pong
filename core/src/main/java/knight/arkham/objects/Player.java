package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
public class Player extends GameObject {
    private final boolean isPlayer1;
    public static int score;

    public Player(Rectangle rectangle, boolean isPlayer1) {
        super(rectangle, "images/players.png", 10);
        this.isPlayer1 = isPlayer1;
        score = 0;
    }

    public void update() {

        if (isPlayer1) {

            if (Gdx.input.isKeyPressed(Input.Keys.W))
                bounds.y += actualSpeed;

            else if (Gdx.input.isKeyPressed(Input.Keys.S))
                bounds.y  -= actualSpeed;
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                bounds.y += actualSpeed;

            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                bounds.y  -= actualSpeed;
        }
    }
}
