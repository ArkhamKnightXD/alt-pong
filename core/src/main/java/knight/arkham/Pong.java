package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import knight.arkham.screens.MainMenuScreen;

public class Pong extends Game {
    public static Pong INSTANCE;
    public SpriteBatch batch;
    public int screenWidth;
    public int screenHeight;

    public Pong() {

        INSTANCE = this;
    }

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        setScreen(new MainMenuScreen());
    }


    public void manageExitTheGame() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
