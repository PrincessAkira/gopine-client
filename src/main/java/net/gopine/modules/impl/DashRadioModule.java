package net.gopine.modules.impl;

import javazoom.jl.player.Player;
import net.gopine.modules.Module;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.RenderedModule;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.gopine.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class DashRadioModule extends RenderedModule {

    public int station = 1;
    public String stationStr;

    public DashRadioModule() {
        super("Dash Radio", ModuleCategory.GAMEPLAY);
        if(station == 0) {
            stationStr = "Sixties";
        } else if(station == 1) {
            stationStr = "DashX";
        }
    }

    private Player player;
    private Thread thread;

    private JSONObject file;
    private JSONArray stationArr;
    private JSONObject stationObj;

    @Override
    public void onModuleEnable() {
        try {
            file = (JSONObject) new JSONParser().parse(this.readURLContent(new URL("https://dash-api.com/api/v3/allData.php").getContent()));
            stationArr = (JSONArray) file.get("stations");
            stationObj = (JSONObject) stationArr.get(station);
            this.approximateWidth = 32 + font.getStringWidth("Station Name: " + stationObj.get("short_name"));
            this.approximateHeight = 32;
            this.setStreamURL(new URL(this.parseM3U(new URL((String) stationObj.get("stream_url")).openStream())).openStream());
            Logger.CustomLogger.info("Dash Radio", "Starting playing music from: " + stationObj.get("short_name"));
            this.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
        super.onModuleEnable();
    }

    @Override
    public void onModuleDisable() {
        this.stop();
        super.onModuleDisable();
    }

    @Override
    public void onRender(ScreenPos pos) {
        try {
            final BufferedImage bi = ImageIO.read(new URL((String) stationObj.get("large_logo_url")));
            final DynamicTexture image = new DynamicTexture(bi);
            final ResourceLocation rl = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("cover.png", image);
            Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
            Gui.drawModalRectWithCustomSizedTexture(pos.getExactPosX(), pos.getExactPosY(), 0, 0, 32, 32, 32, 32);
            font.drawString("Station Name: " + stationObj.get("short_name"), pos.getExactPosX() + 32, pos.getExactPosY(), -1);
            new RenderUtils().drawRect(pos.getExactPosX(), pos.getExactPosY(), pos.getExactPosX() + this.approximateWidth, pos.getExactPosY() + this.approximateHeight, new Color(140, 140, 140, 100).getRGB());
        } catch(Exception e) {
            e.printStackTrace();
        }
        super.onRender(pos);
    }

    @Override
    public void onDummyRender(ScreenPos pos) {
        try {
            final BufferedImage bi = ImageIO.read(new URL((String) stationObj.get("large_logo_url")));
            final DynamicTexture image = new DynamicTexture(bi);
            final ResourceLocation rl = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("cover.png", image);
            Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
            Gui.drawModalRectWithCustomSizedTexture(pos.getExactPosX(), pos.getExactPosY(), 0, 0, 32, 32, 32, 32);
            font.drawString("Station Name: " + stationObj.get("short_name"), pos.getExactPosX() + 32, pos.getExactPosY(), -1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        super.onDummyRender(pos);
    }

    private String parseM3U(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            return br.readLine();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readURLContent(Object o) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) o));
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isThreadRunning() {
        return thread != null;
    }

    public void setStreamURL(InputStream is) {
        try {
            player = new Player(is);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Objects.requireNonNull(player);

        thread = new Thread(() -> {
            try {
                player.play();
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stop() {
        if(isThreadRunning()) {
            thread.interrupt();
            thread = null;

            if(player != null) {
                player.close();
            }
        }
    }

}