package net.gopine.modules.impl;

import com.sun.net.httpserver.HttpServer;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.enums.ProductType;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import net.gopine.assets.gui.GopineButtonRound;
import net.gopine.events.EventSubscriber;
import net.gopine.events.impl.gui.EventGuiSwitch;
import net.gopine.events.manager.EventManager;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.gopine.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect;

public class SpotifyDisplayModule extends RenderedModule {

    public SpotifyDisplayModule(boolean toggled) {
        super("Spotify", ModuleCategory.RENDERING, toggled);
        this.approximateWidth = 290;
        this.approximateHeight = 64;
    }

    private static final String CLIENT_ID = "1387997144534e6685f712c76391160d"/*"e7ae558508d046d3bd9d3fdee91229cd"*/;
    private static final URI REDIRECT_URI = SpotifyHttpManager.makeUri("http://localhost:42069");
    private static final String CHALLENGE = "w6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";
    private static final String CODE_VERIFIER = "NlJx4kD4opk4HY7zBM6WfUHxX7HoF8A2TUhOIPGA74w";
    private static final SpotifyApi api =
            SpotifyApi.builder().setClientId(CLIENT_ID).setRedirectUri(REDIRECT_URI).build();
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest =
            api.authorizationCodePKCEUri(CHALLENGE)
                    .scope(
                            "user-read-playback-state user-read-currently-playing user-modify-playback-state streaming user-read-private")
                    .build();
    protected static Integer millisThrough;
    protected static Integer millisAll;

    protected static String previousSong;
    protected static Integer percentage;
    protected static Track currentlyPlaying;
    protected static ResourceLocation coverImage;
    protected static BufferedImage coverImageBuffer;
    protected static Boolean playing;
    private static boolean ready = false;
    private static boolean hasPremium = false;

    @Override
    public void onModuleEnable() {
        Logger.ModLogger.warn("[Gopine Spotify] Starting...");
        EventManager.register(this);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(42069), 0);
            server.createContext(
                    "/",
                    httpExchange -> {
                        String code = queryToMap(httpExchange.getRequestURI().getQuery()).get("code");
                        if (code != null) {
                            server.stop(0);
                            setupApi(code);
                        }
                    });
            server.start();
            Logger.ModLogger.warn("[Gopine Spotify] Opening Spotify authenticator in your favourite browser!");
            Desktop.getDesktop().browse(authorizationCodeUriRequest.execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onModuleEnable();
    }

    private final Color darkGrey = new Color(169, 169, 169, 133);
    private final Color lightGrey = new Color(105, 105, 105, 133);

    @Override
    public void onRender(ScreenPos pos) {
        if (coverImage != null && coverImageBuffer != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(coverImage);
            drawScaledCustomSizeModalRect(pos.getExactPosX(), pos.getExactPosY(), 0, 0, 64, 64, 64, 64, 64, 64);
        }
        if (percentage != null) {
            int max = 150;
            int wayThroughPixels = (int) (max * ((double) percentage / 100));
            new RenderUtils().drawRoundedRect(pos.getExactPosX() + 110, pos.getExactPosY() + 58, max, 4, 2, darkGrey);
            new RenderUtils().drawRoundedRect(pos.getExactPosX() + 110, pos.getExactPosY() + 58, wayThroughPixels, 4, 2, lightGrey);
        }
        if (millisThrough != null) {
            font.drawString(formatMillis(millisThrough), pos.getExactPosX() + 82, pos.getExactPosY() + 56, lightGrey.getRGB());
        }
        if (millisAll != null) {
            font.drawString(formatMillis(millisAll), pos.getExactPosX() + 265, pos.getExactPosY() + 56, lightGrey.getRGB());
            if(millisThrough > millisAll) {
                update();
            }
        }
        if (currentlyPlaying != null) {
            String msg = currentlyPlaying.getName();
            String authorList;
            if (currentlyPlaying.getArtists().length == 1) {
                authorList = currentlyPlaying.getArtists()[0].getName();
            } else if (currentlyPlaying.getArtists().length == 2) {
                authorList =
                        currentlyPlaying.getArtists()[0].getName()
                                + " and "
                                + currentlyPlaying.getArtists()[1].getName();
            } else {
                StringBuilder authors = new StringBuilder();
                int index = 0;
                for (ArtistSimplified author : currentlyPlaying.getArtists()) {
                    if (index == currentlyPlaying.getArtists().length - 1) {
                        authors.append(" and ").append(author.getName());
                    } else if (index == currentlyPlaying.getArtists().length - 2) {
                        authors.append(author.getName());
                    } else {
                        authors.append(author.getName()).append(", ");
                    }
                    index++;
                }
                authorList = authors.toString();
            }
            msg += " - " + authorList;
            font.drawString(msg, pos.getExactPosX() + 75, pos.getExactPosY() + 10, -1);
        }
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        if (coverImage != null && coverImageBuffer != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(coverImage);
            drawScaledCustomSizeModalRect(pos.getExactPosX(), pos.getExactPosY(), 0, 0, 64, 64, 64, 64, 64, 64);
        }
        if (percentage != null) {
            int max = 150;
            int wayThroughPixels = (int) (max * ((double) percentage / 100));
            new RenderUtils().drawRoundedRect(pos.getExactPosX() + 110, pos.getExactPosY() + 58, max, 4, 2, darkGrey);
            new RenderUtils().drawRoundedRect(pos.getExactPosX() + 110, pos.getExactPosY() + 58, wayThroughPixels, 4, 2, lightGrey);
        }
        if (millisThrough != null) {
            font.drawString(formatMillis(millisThrough), pos.getExactPosX() + 82, pos.getExactPosY() + 56, lightGrey.getRGB());
        }
        if (millisAll != null) {
            font.drawString(formatMillis(millisAll), pos.getExactPosX() + 265, pos.getExactPosY() + 56, lightGrey.getRGB());
            if(millisThrough > millisAll) {
                update();
            }
        }
        if (currentlyPlaying != null) {
            String msg = currentlyPlaying.getName();
            String authorList;
            if (currentlyPlaying.getArtists().length == 1) {
                authorList = currentlyPlaying.getArtists()[0].getName();
            } else if (currentlyPlaying.getArtists().length == 2) {
                authorList =
                        currentlyPlaying.getArtists()[0].getName()
                                + " and "
                                + currentlyPlaying.getArtists()[1].getName();
            } else {
                StringBuilder authors = new StringBuilder();
                int index = 0;
                for (ArtistSimplified author : currentlyPlaying.getArtists()) {
                    if (index == currentlyPlaying.getArtists().length - 1) {
                        authors.append(" and ").append(author.getName());
                    } else if (index == currentlyPlaying.getArtists().length - 2) {
                        authors.append(author.getName());
                    } else {
                        authors.append(author.getName()).append(", ");
                    }
                    index++;
                }
                authorList = authors.toString();
            }
            msg += " - " + authorList;
            font.drawString(msg, pos.getExactPosX() + 75, pos.getExactPosY() + 10, -1);
        }
        super.onDummyRender(pos);
    }

    private void setupApi(String code) {
        try {
            AuthorizationCodeCredentials credentials =
                    api.authorizationCodePKCE(code, CODE_VERIFIER).build().execute();
            api.setAccessToken(credentials.getAccessToken());
            api.setRefreshToken(credentials.getRefreshToken());

            int time = credentials.getExpiresIn() - 30;
            new Thread("Spotify Token Renewer") {
                @Override
                public void run() {
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(time);
                            AuthorizationCodeCredentials credentials1 =
                                    api.authorizationCodePKCERefresh().build().execute();
                            api.setAccessToken(credentials1.getAccessToken());
                            api.setRefreshToken(credentials1.getRefreshToken());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
            new Thread("Spotify Timer Updater") {
                @Override
                public void run() {
                    while (true) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            if (millisThrough != null)
                                millisThrough = millisThrough + 1000;
                            updatePercentage();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            ready = true;

            Logger.ModLogger.warn(
                    "[Gopine Spotify] Logged in as: "
                            + api.getCurrentUsersProfile().build().execute().getDisplayName());

            hasPremium =
                    api.getCurrentUsersProfile().build().execute().getProduct().equals(ProductType.PREMIUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatMillis(int millis) {
        if (millis >= 3600000) {
            return String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        } else {
            return String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        }
    }

    @EventSubscriber
    public void onGuiSwitch(EventGuiSwitch event) {
        if (api.getAccessToken() == null && event.screen instanceof GuiMainMenu) {
            try {
                // TODO: Fix or get someone to Mixin it.
                Field buttonListField = GuiScreen.class.getDeclaredField("buttonList");
                buttonListField.setAccessible(true);
                ArrayList<GopineButtonRound> buttonList = (ArrayList<GopineButtonRound>) buttonListField.get(event.screen);
                buttonList.add(new GopineButtonRound(69, 10, 10, "Setup Spotify", 6, new Color(255, 255, 255, 133), new Color(199, 226, 84, 133), new Color(0, 100, 50), new Color(0, 100, 50)));
                buttonListField.set(event.screen, buttonList);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (ready) {
            update();
        }
    }

    private static void update() {
        Thread updateThread = new Thread("Spotify Updater Thread") {
            @Override
            public void run() {
                try {
                    CurrentlyPlayingContext context =
                            api.getInformationAboutUsersCurrentPlayback().build().execute();
                    if (context != null) currentlyPlaying = (Track) context.getItem();
                    if(context != null && currentlyPlaying != null && currentlyPlaying != (context).getItem()) currentlyPlaying = (Track) context.getItem();

                    if (context != null && currentlyPlaying != null) {
                        millisThrough = context.getProgress_ms();
                        millisAll = currentlyPlaying.getDurationMs();
                        updatePercentage();
                        if (previousSong == null) previousSong = "";
                        if (!previousSong.equals(currentlyPlaying.getId())) {
                            coverImageBuffer =
                                    ImageIO.read(new URL(currentlyPlaying.getAlbum().getImages()[0].getUrl()));
                            Minecraft.getMinecraft()
                                    .addScheduledTask(
                                            () -> {
                                                DynamicTexture dynamicTexture = new DynamicTexture(coverImageBuffer);
                                                coverImage =
                                                        Minecraft.getMinecraft()
                                                                .getTextureManager()
                                                                .getDynamicTextureLocation("cover.jpg", dynamicTexture);
                                            });
                            previousSong = currentlyPlaying.getId();
                        }

                        playing = context.getIs_playing();
                    } else {
                        percentage = null;
                        coverImageBuffer = null;
                        coverImage = null;
                        previousSong = "";
                        playing = null;

                        millisAll = null;
                        millisThrough = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if(updateThread.isAlive()) {
            return;
        } else {
            updateThread.start();
        }
    }

    private static void updatePercentage() {
        if (millisThrough != null && millisAll != null) {
            double divided = (double) millisThrough / millisAll;
            double p = divided * 100;
            percentage = (int) p;
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

}