package net.gopine.util.files;

import com.google.gson.Gson;
import net.gopine.modules.ModuleCategory;
import net.gopine.modules.draggable.ScreenPos;
import net.gopine.util.Logger;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class FileHandler {

    public static File gopineDataDir = new File(Minecraft.getMinecraft().mcDataDir, "Gopine Client");
    public static File moduleDataDir = new File(gopineDataDir, "Modules");
    public static File settingDataDir = new File(gopineDataDir, "Settings");

    private Gson gson = new Gson();

    public void initFiles() {

        if(!gopineDataDir.exists()) {
            gopineDataDir.mkdirs();
        }

        if(!moduleDataDir.exists()) {
            moduleDataDir.mkdirs();
        }

        if(!settingDataDir.exists()) {
            settingDataDir.mkdirs();
        }

    }

    public void saveBasicModuleData(String s, ModuleCategory moduleCategory, boolean bool) {
        FileWriter fileWriter = null;
        final File file1 = new File(moduleDataDir, s + ".json");
        try {
            final JSONObject obj = new JSONObject();
            obj.put("name", s);
            obj.put("category", moduleCategory.toString());
            obj.put("toggle_state", bool);
            fileWriter = new FileWriter(file1);
            fileWriter.write(obj.toString());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveBasicModuleData(String s, ModuleCategory moduleCategory, boolean bool, ScreenPos pos) {
        FileWriter fileWriter = null;
        final File file1 = new File(moduleDataDir, s + ".json");
        try {
            final JSONObject obj = new JSONObject();
            obj.put("name", s);
            obj.put("category", moduleCategory.toString());
            obj.put("toggle_state", bool);
            JSONObject posData = new JSONObject();
            posData.put("x", pos.getExactPosX());
            posData.put("y", pos.getExactPosY());
            obj.put("pos", posData);
            fileWriter = new FileWriter(file1);
            fileWriter.write(obj.toString());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject readBasicModuleData(String s) {

        JSONObject returnObj;

        JSONParser jsonParser = new JSONParser();
        final File file1 = new File(moduleDataDir, s + ".json");

        try(FileReader reader = new FileReader(file1)) {

            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}