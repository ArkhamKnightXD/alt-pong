package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Pong;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.objects.Ball;
import knight.arkham.objects.Player;

public class GameScreen extends ScreenAdapter {

    private final Pong game;
    private final Player player;
    private final Player enemy;
    private final Ball ball;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final TextureRegion[] scoreNumbers;
    private final Music music;
    private final Sound winSound;
    private final ShapeRenderer shapeRenderer;

    public GameScreen(boolean isNewGame) {

        game = Pong.INSTANCE;

        camera = new OrthographicCamera();

        viewport = new FitViewport(game.screenWidth, game.screenHeight, camera);

        float midScreenHeight = game.screenHeight / 2f;
        float midScreenWidth = game.screenWidth / 2f;

//        Si deseo posicionar la camara en el centro debo de dividir el screen height y width entre 2
        camera.position.set(midScreenWidth, midScreenHeight, 0);

        player = new Player(10, midScreenHeight, true);
        enemy = new Player(game.screenWidth-26, midScreenHeight, false);

        if (!isNewGame)
            GameDataHelper.loadGameData(player, enemy);

        ball = new Ball(new Vector2(midScreenWidth, midScreenHeight));

        scoreNumbers = loadTextureSprite();

        music = AssetsHelper.loadMusic("epic.wav");

        music.play();
        music.setLooping(true);
        music.setVolume(0.2f);

        winSound = AssetsHelper.loadSound("win.wav");
        shapeRenderer = new ShapeRenderer();
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

        ball.hasCollision(player.actualBounds);
        ball.hasCollision(enemy.actualBounds);

        if (ball.actualBounds.x > game.screenWidth) {

            player.score += 1;
            ball.resetBallPosition();
        }

        if (ball.actualBounds.x < 0) {

            enemy.score += 1;
            ball.resetBallPosition();
        }

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
    public void render(float deltaTime) {

        update(deltaTime);

        draw();
    }

    private void draw() {

        ScreenUtils.clear(0.1f,0.5f,0.4f,0);

        //Puedo tener un shape rendered y batch renderer al mismo tiempo sin problemas
        // y a ambos puedo indicarles la misma camara para el setProjectionMatrix.
        shapeRenderer.setProjectionMatrix(camera.combined);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        drawScoreNumbers(game.batch, player.score, 200);

        drawScoreNumbers(game.batch, enemy.score, 700);

        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.4f, 0.8f, 0.6f, 0));

        shapeRenderer.circle(game.screenWidth / 2f,  game.screenHeight / 2f, 150);

        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.line(game.screenWidth / 2f, game.screenHeight, game.screenWidth / 2f -6, 0);

        player.draw(shapeRenderer);
        enemy.draw(shapeRenderer);

        shapeRenderer.setColor(Color.YELLOW);
        ball.draw(shapeRenderer);

        shapeRenderer.end();
    }

    private void drawScoreNumbers(SpriteBatch batch, int scoreNumber, float x){

        final float width = 48;
        final float height = 64;

        if (scoreNumber < 10)
            batch.draw(scoreNumbers[scoreNumber], x, 560, width , height);

        else {

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(0, 1))], x, 560 , width , height);

            batch.draw(scoreNumbers[Integer.parseInt(("" + scoreNumber).substring(1, 2))], x +20, 560, width, height);
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        music.dispose();
        winSound.dispose();
    }
}
