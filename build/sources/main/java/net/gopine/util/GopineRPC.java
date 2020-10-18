package net.gopine.util;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.time.OffsetDateTime;


/**
 * The DiscordRPC Class
 * @author Hot Tutorials | Hot Tutorials#8262
 * @since b0.1
 */
public class GopineRPC {

    RichPresence.Builder builder = new RichPresence.Builder();
    IPCClient client = new IPCClient(766300045918076938L);

    public static boolean isConnected = false;
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public void init() {
        Logger.info("Started DiscordRPC...");
        client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                setPresence("Starting Client...", "Starting Client...", "gopinelarge");
                client.sendRichPresence(builder.build());
                Logger.info("Set DiscordRPC Presence...");
            }
        });
        try {
            client.connect();
            builder.setStartTimestamp(OffsetDateTime.now());
            client.sendRichPresence(builder.build());
            Logger.info("Connected to RPC Client!");
            this.setConnected(true);
        } catch (Exception ignored) {
            this.setConnected(false);
        }
    }

    /**
     * @param firstLine is the firstLine
     * @author MatthewTGM | MatthewTGM#4058
     * @since b0.1
     */
    public void setPresence(String firstLine) {
        builder.setDetails(firstLine);
        client.sendRichPresence(builder.build());
    }

    /**
     * @param firstLine is the firstLine
     * @param secondLine is the secondLine
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public void setPresence(String firstLine, String secondLine) {
        builder.setDetails(firstLine)
                .setState(secondLine);
        client.sendRichPresence(builder.build());
    }

    /**
     * @param firstLine is the firstLine
     * @param secondLine is the secondLine
     * @param largeImage is the large image that shows
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public void setPresence(String firstLine, String secondLine, String largeImage) {
        builder.setDetails(firstLine)
                .setState(secondLine)
                .setLargeImage(largeImage);
        client.sendRichPresence(builder.build());
    }


    /**
     * @param firstLine is the firstLine
     * @param secondLine is the secondLine
     * @param largeImage is the large image that shows
     * @param smallImage is the image that shows in the bottom right of the largeImage
     * @author Hot Tutorials | Hot Tutorials#8262
     * @since b0.1
     */
    public void setPresence(String firstLine, String secondLine, String largeImage, String smallImage) {
        builder.setDetails(firstLine)
                .setState(secondLine)
                .setLargeImage(largeImage)
                .setSmallImage(smallImage);
        client.sendRichPresence(builder.build());
    }

}
