package ga.matthewtgm.utils;

import ga.matthewtgm.handlers.ScoreboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;

public class HypixelUtils {

    public static boolean isOnHypixel;


    /**
     * Returns whether the player is connected to Hypixel
     */
    public static boolean getIsOnHypixel() {
        return isOnHypixel;
    }

    /**
     * Sets whether the player is connected to Hypixel
     * @param onHypixel
     */
    public static void setOnHypixel(boolean onHypixel) {
        isOnHypixel = onHypixel;
    }

    public static class HypixelSkywarsUtils {

    }

    public static class HypixelSkyBlockUtils {

        public static HypixelSkyBlockUtils instance;

        /**
         * Returns an instance of the HypixelSkyBlockUtils subclass
         */
        public static HypixelSkyBlockUtils getInstance() {
            return instance;
        }

        /**
         * The skill for every level-up in every skill for Hypixel SkyBlock
         */
        public int[] skillXPPerLevel = new int[]{0, 50, 125, 200, 300, 500, 750, 1000, 1500, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 30000, 50000,
                75000, 100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000,
                1200000, 1300000, 1400000, 1500000, 1600000, 1700000, 1800000, 1900000, 2000000, 2100000, 2200000,
                2300000, 2400000, 2500000, 2600000, 2750000, 2900000, 3100000, 3400000, 3700000, 4000000};

        /**
         * Adds a check for if the player is connected to Hypixel and is playing SkyBlock
         */
        public void isInHypixelSkyBlock(boolean skyblockBoolean) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && HypixelUtils.isOnHypixel) {
                ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                if (scoreboardObj != null) {
                    String scObjName = ScoreboardHandler.cleanScoreboard(scoreboardObj.getDisplayName());
                    if (scObjName.contains("SKYBLOCK")) {
                        skyblockBoolean = true;
                        return;
                    }
                }
            }
            skyblockBoolean = false;
        }

    }

}