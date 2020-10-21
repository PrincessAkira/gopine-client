package net.gopine.uiFramework;

import net.minecraft.client.Minecraft;
import org.lwjgl.util.vector.Vector2f;

/** This a class that handles anchors
 * @author Basilicous
 * @since 0.1b
 */
public class Anchor {
    private Vector2f positon;
    private Vector2f origin = new Vector2f(0, 0);

    /** This takes 2 anchors, then calculates the position,
     * based on the given anchors
     * @author Basilicous
     * @since 0.1b
     * @param a1 Anchor point 1
     * @param a2 Anchor point 2
     * @throws Exception
     */
    public Anchor(Anchors a1, Anchors a2) throws Exception {
        this.positon = new Vector2f(pollAnchor(a1), pollAnchor(a2));
    }

    /** This takes 2 anchors, then calculates the position,
     * based on the given anchors and a set origin
     * @author Basilicous
     * @since 0.1b
     * @param a1 Anchor point 1
     * @param a2 Anchor point 2
     * @param o Origin
     * @throws Exception
     */
    public Anchor(Anchors a1, Anchors a2, Vector2f o) throws Exception {
        this.positon = new Vector2f(pollAnchor(a1), pollAnchor(a2));
    }

    /** Returns the position of an anchor
     * @author Basilicous
     * @since 0.1b
     * @return position
     */
    public Vector2f getPositon() {
        return positon;
    }

    /** This takes an anchor, then calculates the position
     * @author Basilicous
     * @since 0.1b
     * @param a Anchor point
     * @return int anchor position
     * @throws Exception
     */
    private int pollAnchor(Anchors a) throws Exception {
        switch (a) {
            case TOP:
            case LEFT:
                return (int) (0 + origin.x);
            case RIGHT:
                return (int) (Minecraft.getMinecraft().displayWidth + origin.x);
            case BOTTOM:
                return (int) (Minecraft.getMinecraft().displayHeight + origin.y);
        }
        throw new Exception("Invalid anchor type!");
    }
}
