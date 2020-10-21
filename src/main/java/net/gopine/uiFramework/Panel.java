package net.gopine.uiFramework;

import net.gopine.util.Logger;
import org.lwjgl.util.vector.Vector2f;

/** This is a panel, that holds other objects
 * @author basilicous
 * @since 0.1b
 */
public class Panel {
    public Vector2f size;
    public Vector2f position;

    /**
     * @author Basilicous
     * @since 0.1b
     * @param width The wid
     * @param height
     * @param a1
     * @param a2
     * @throws Exception
     */
    public Panel(int width, int height, Anchors a1, Anchors a2) throws Exception {
        this.size = new Vector2f(width, height);
        this.position = new Anchor(a1, a2).getPositon();
    }
}
