package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Pong;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.objects.Ball;
import knight.arkham.objects.Player;
import knight.arkham.objects.Wall;

import static knight.arkham.helpers.Constants.*;

public class GameScreen extends ScreenAdapter {

    private final Pong game;
    private final Player player;
    private final Player enemy;
    private final Ball ball;
    private final Wall bottomWall;
    private final Wall topWall;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final TextureRegion[] scoreNumbers;
    private final Music music;
    private final Sound winSound;

    public GameScreen(boolean isNewGame) {

        game = Pong.INSTANCE;

        player = new Player(new Rectangle(480, 600, 16, 64), true);
        enemy = new Player(new Rectangle(1420,600, 16, 64), false);

        if (!isNewGame)
            GameDataHelper.loadGameData(player, enemy);

        ball = new Ball(new Rectangle(1000,600, 20, 20), this);

        topWall = new Wall(new Rectangle(480,906, FULL_SCREEN_WIDTH, 64));
        bottomWall = new Wall(new Rectangle(480,314, FULL_SCREEN_WIDTH, 64));

        camera = new OrthographicCamera();

        viewport = new FitViewport(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT, camera);

        camera.position.set(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT, 0);

        scoreNumbers = loadTextureSprite();

        music = AssetsHelper.loadMusic("epic.wav");

        music.play();
        music.setLooping(true);
        music.setVolume(0.3f);

        winSound = AssetsHelper.loadSound("win.wav");
    }

    private TextureRegion[] loadTextureSprite(){

        Texture textureToSplit = new Texture("images/numbers.png");

        return TextureRegion.split(textureToSplit, textureToSplit.getWidth() / 10, textureToSplit.getHeight())[0];
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void update(float deltaTime){

        player.update(deltaTime);
        enemy.update(deltaTime);
        ball.update(deltaTime);

        manageObjectCollisions();

        setGameOverScreen();

        manageGameData();

        game.manageExitTheGame();
    }

    private void manageObjectCollisions() {

        player.hasCollision(topWall.getBounds(), bottomWall.getBounds());
        enemy.hasCollision(topWall.getBounds(), bottomWall.getBounds());

        ball.hasCollision(topWall);
        ball.hasCollision(bottomWall);
        ball.hasCollision(player);
        ball.hasCollision(enemy);
    }

    private void manageGameData() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            GameDataHelper.saveGameData(player.score, enemy.score);
    }

    private void setGameOverScreen() {

        if (player.score > 4 || enemy.score > 4){

            winSound.play();
            game.setScreen(new MainMenuScreen());
        }
    }


    @Override
    public void render(float deltaTime) {

        update(deltaTime);

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        topWall.draw(game.batch);

        drawScoreNumbers(game.batch, player.score, 500);

        drawScoreNumbers(game.batch, enemy.score, 1380);

        bottomWall.draw(game.batch);

        player.draw(game.batch);
        ball.draw(game.batch);
        enemy.draw(game.batch);

        game.batch.end();
    }

    private void drawScoreNumbers(SpriteBatch batch, int scoreNumber, float x){

        final float width = 48;
        final float height = 64;

        if (scoreNumber < 10)
            batch.draw(scoreNumbers[scoreNumber], x, 900, width , height);

        else {

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(0, 1))], x, 900 , width , height);

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(1, 2))], x +20, 900, width, height);
        }
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        topWall.dispose();
        bottomWall.dispose();
        player.dispose();
        ball.dispose();
        enemy.dispose();
        music.dispose();
    }

    public Player getPlayer() {return player;}
    public Player getEnemy() {return enemy;}
}
