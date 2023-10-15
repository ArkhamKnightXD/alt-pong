package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected float actualSpeed;
    protected final Rectangle actualBounds;
    private final Texture sprite;

    protected GameObject(Rectangle rectangle, String spritePath, float speed) {
        actualBounds = rectangle;
        sprite = new Texture(spritePath);
        actualSpeed = speed;
    }

    public void draw(Batch batch) {

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Rectangle getBounds() {return actualBounds;}
    public void dispose() {sprite.dispose();}
}
