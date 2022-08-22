package com.github.stefan9110.MCChatProxy.util.configuration;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;


public class JSONConfiguration extends JSONManager {
    private File filePath;
    private final String fullPath, name;
    private JSONObject json;

    public JSONConfiguration(String fileName, String path) {
        this.fullPath = "files/" + path;
        this.name = fileName;
        create();
    }

    public JSONConfiguration(String fileName) {
        this.fullPath = "files/";
        this.name = fileName;
        create();
    }

    public JSONConfiguration(String fileName, String path, boolean isFullPath) {
        this.fullPath = (isFullPath ? "" : "files/") + path;
        this.name = fileName;
        create();
    }

    private void create() {
        File f = new File(fullPath);
        if (!f.exists()) f.mkdirs();
        filePath = new File(fullPath, name + ".json");

        try {
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            json = new JSONObject(getStringJSON(filePath));
        } catch (Exception e) {
            json = new JSONObject();
        }
    }

    private String getStringJSON(File file) throws FileNotFoundException {
        String result = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) result += scanner.nextLine();
        return result;
    }

    @NotNull
    @Override
    public JSONObject getJson() {
        return json;
    }

    public void addDefault(String path, Object object) {
        if (!contains(path)) set(path, object);
    }

    public static boolean exists(String name, String path) {
        return new File("files/" + path + "/", name + ".json").exists();
    }

    public static File getFile(String name, String path) {
        return new File("files/" + path + "/", name + ".json");
    }

    public String getName() {
        return name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void reload() {
        try {
            json = new JSONObject(new Scanner(filePath).nextLine());
        } catch (Exception e) {
            json = new JSONObject();
        }
    }

    public void set(String path, Object obj) {
        if (obj == null) {
            remove(path);
            return;
        }

        String[] pathArr = path.split("\\.");
        if (pathArr.length <= 1) {
            json.put(path, obj);
        }
        else {
            JSONObject[] hierarchy = new JSONObject[pathArr.length - 1];
            for (int i = pathArr.length - 2; i >= 0; i--) {
                String[] toFindPath = Arrays.copyOf(pathArr, i + 1);

                if (contains(toFindPath)) {
                    Object hObj = get(toFindPath);
                    if (!(hObj instanceof JSONObject)) throw new RuntimeException("Json path error");
                    hierarchy[i] = (JSONObject) hObj;
                }
                else {
                    hierarchy[i] = new JSONObject();
                }
            }

            hierarchy[hierarchy.length - 1].put(pathArr[pathArr.length - 1], obj);
            for (int i = hierarchy.length - 1; i >= 1; i--) {
                hierarchy[i - 1].put(pathArr[i], hierarchy[i]);
            }
            json.put(pathArr[0], hierarchy[0]);
        }
    }

    public void setAndSave(String path, Object obj) {
        set(path, obj);
        save();
    }

    public void remove(String path) {
        String[] pathArr = path.split("\\.");
        if (pathArr.length == 0) {
            json.remove(path);
        }
        else {
            JSONObject[] hierarchy = new JSONObject[pathArr.length - 1];
            for (int i = pathArr.length - 2; i >= 0; i--) {
                String[] toFindPath = Arrays.copyOf(pathArr, i + 1);

                if (contains(toFindPath)) {
                    Object hObj = get(toFindPath);
                    if (!(hObj instanceof JSONObject)) throw new RuntimeException("Json path error");
                    hierarchy[i] = (JSONObject) hObj;
                }
                else {
                    hierarchy[i] = new JSONObject();
                }
            }

            hierarchy[hierarchy.length - 1].remove(pathArr[pathArr.length - 1]);
            for (int i = hierarchy.length - 1; i >= 1; i--) {
                hierarchy[i - 1].put(pathArr[i], hierarchy[i]);
            }
            json.put(pathArr[0], hierarchy[0]);
        }
        save();
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(json.toString(3));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        filePath.delete();
    }
}
