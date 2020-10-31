package net.gopine.listeners;

import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.client.EventChatReceived;
import net.gopine.events.impl.client.EventClientChatReceived;
import net.gopine.modules.impl.OofModule;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OofModListener {

    private Minecraft mc = Minecraft.getMinecraft();

    private String nameToCheck;

    public OofModListener() {
        this.nameToCheck = "";
    }

    @EventSubscriber
    public void onDeathMessageReceived(EventClientChatReceived e) {
        if (this.nameToCheck.isEmpty()) {
            this.nameToCheck = this.mc.thePlayer.getName();
        }
        final String line = e.message.getUnformattedText();
        if (!OofModule.shouldPlay || line.split(" ").length == 0) {
            return;
        }
        final String killMessageRegex = "(\\w{1,16}).+ (by|of|to|for|with) (" + this.nameToCheck + ")";
        final String usernamePatternRegex = "^[a-zA-Z0-9_-]{3,16}$";
        final Pattern killMessagePattern = Pattern.compile(killMessageRegex);
        final Pattern usernamePattern = Pattern.compile(usernamePatternRegex);
        if(line.isEmpty()) return;
        final Matcher killMessageMatcher = killMessagePattern.matcher(line);
        final Matcher usernameMatcher = usernamePattern.matcher(line.split(" ")[0]);
        if (usernameMatcher.matches() && killMessageMatcher.find()) {
            final String killed = killMessageMatcher.group(1);
            if (!killed.equals(this.nameToCheck)) {
                try {
                    this.playSound();
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void playSound() throws Exception {
        /*if (!this.mod.getSoundSetting().exists()) {
            return;
        }*/
        final File file1 = new File("oof.wav");
        Logger.CustomLogger.info("OofMod #1", file1);
        Logger.CustomLogger.info("OofMod #2", file1.toURI().toURL());
        Logger.CustomLogger.info("OofMod #3", file1.toString());
        final AudioInputStream audioIn = AudioSystem.getAudioInputStream(/*OofModule.getSoundSetting().toURI().toURL()*/file1.toURI().toURL());
        final Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        final FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(OofModule.getVolume() - 30.0f);
        clip.start();
        //this.mc.thePlayer.playSound("gopineclient:oof", 30.0f, 1.0f);
    }

}