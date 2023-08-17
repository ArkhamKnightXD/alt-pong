package knight.arkham.helpers;

import knight.arkham.Pong;

public class Constants {
    public static final float PIXELS_PER_METER = 32.0f;
    public static final int FULL_SCREEN_HEIGHT = Pong.INSTANCE.screenHeight;
    public static final float BOX2D_FULL_SCREEN_HEIGHT = Pong.INSTANCE.screenHeight / PIXELS_PER_METER;
    public static final int FULL_SCREEN_WIDTH = Pong.INSTANCE.screenWidth;
    public static final float BOX2D_FULL_SCREEN_WIDTH = Pong.INSTANCE.screenWidth / PIXELS_PER_METER;
}
