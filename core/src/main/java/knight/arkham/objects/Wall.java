package knight.arkham.objects;

import com.badlogic.gdx.math.Rectangle;

public class Wall extends GameObject {

    public Wall(Rectangle rectangle) {
        super(rectangle, "images/wall.png", 0);
    }
}
