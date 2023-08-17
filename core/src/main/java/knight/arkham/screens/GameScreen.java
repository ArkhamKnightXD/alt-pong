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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Pong;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.objects.Ball;
import knight.arkham.objects.Player;
import knight.arkham.objects.Wall;

import static knight.arkham.helpers.Constants.*;
import static knight.arkham.helpers.Constants.BOX2D_FULL_SCREEN_HEIGHT;

public class GameScreen extends ScreenAdapter {

    private final Pong game;
    private final Player player;
    private final Player enemy;
    private final Ball ball;
    private final Wall bottomWall;
    private final Wall topWall;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final World world;
    private final TextureRegion[] scoreNumbers;
    private final Music music;
    private final Sound winSound;


    public GameScreen(boolean isNewGame) {

        game = Pong.INSTANCE;

        world = new World(new Vector2(0, 0), true);

        GameContactListener contactListener = new GameContactListener(this);

        world.setContactListener(contactListener);

        player = new Player(new Rectangle(490, 600, 16, 64), world, true);
        enemy = new Player(new Rectangle(1430,600, 16, 64), world, false);

        if (!isNewGame)
            GameDataHelper.loadGameData(player, enemy);

        ball = new Ball(new Rectangle(1000,600, 32, 32), this);

        topWall = new Wall(new Rectangle(FULL_SCREEN_WIDTH,930, FULL_SCREEN_WIDTH, 64), world);
        bottomWall = new Wall(new Rectangle(FULL_SCREEN_WIDTH,350, FULL_SCREEN_WIDTH, 64), world);

        camera = new OrthographicCamera();

        viewport = new FitViewport(BOX2D_FULL_SCREEN_WIDTH, BOX2D_FULL_SCREEN_HEIGHT, camera);

        camera.position.set(BOX2D_FULL_SCREEN_WIDTH, BOX2D_FULL_SCREEN_HEIGHT, 0);

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

    private void update(){

        world.step(1 / 60f, 6, 2);

        player.update();
        enemy.update();
        ball.update();

        setGameOverScreen();

        manageGameData();

        game.manageExitTheGame();
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
    public void render(float delta) {

        update();

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        topWall.draw(game.batch);

//        El orden importa debido a que draw esta después de top-wall, este se renderizara encima de él y no detrás
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
            batch.draw(scoreNumbers[scoreNumber], x/PIXELS_PER_METER, 900/PIXELS_PER_METER,
                width/PIXELS_PER_METER , height/PIXELS_PER_METER);

        else {

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(0, 1))], x/PIXELS_PER_METER,
                900/PIXELS_PER_METER , width/PIXELS_PER_METER , height/PIXELS_PER_METER);

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(1, 2))], x/PIXELS_PER_METER +20/PIXELS_PER_METER,
                900/PIXELS_PER_METER, width/PIXELS_PER_METER, height/PIXELS_PER_METER);
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

    public Ball getBall() {return ball;}

    public Player getPlayer() {return player;}

    public Player getEnemy() {return enemy;}

    public World getWorld() {return world;}
}
