package knight.arkham.objects;

import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected float actualSpeed;
    protected final Rectangle actualBounds;

    protected GameObject(Rectangle rectangle, float speed) {
        actualBounds = rectangle;
        actualSpeed = speed;
    }

    public Rectangle getBounds() {return actualBounds;}
}
