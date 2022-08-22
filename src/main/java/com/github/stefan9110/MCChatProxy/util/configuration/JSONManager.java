package com.github.stefan9110.MCChatProxy.util.configuration;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class JSONManager {
    public abstract @NotNull JSONObject getJson();

    protected JSONManager() {

    }

    public boolean contains(String path) {
        if (!path.contains(".")) return getJson().has(path);
        return contains(path.split("\\."));
    }

    public boolean contains(String[] path) {
        JSONObject findKey = getJson();
        if (path.length <= 1) return findKey.has(path[0]);

        for (int i = 0; i < path.length; i++) {
            Object obj;
            try {
                obj = findKey.get(path[i]);
            } catch (JSONException ex) {
                return false;
            }

            if (obj instanceof JSONObject) {
                findKey = (JSONObject) obj;
            }
            else if (i + 1 < path.length) return false;
        }
        return true;
    }

    public JSONObject localizePath(String path) {
        if (!contains(path)) return null;
        if (!path.contains(".")) return getJson();
        return localizePath(path.split("\\."));
    }

    public JSONObject localizePath(String[] pathArr) {
        if (!contains(pathArr)) return null;
        JSONObject getter = getJson();
        for (int i = 0; i < pathArr.length - 1; i++) {
            getter = getter.getJSONObject(pathArr[i]);
        }
        return getter;
    }

    public boolean isNull(String path) {
        if (!contains(path)) return true;
        return isNull(path.split("\\."));
    }

    public boolean isNull(String[] pathArr) {
        JSONObject getter = getJson();
        for (int i = 0; i < pathArr.length - 1; i++) {
            getter = getter.getJSONObject(pathArr[i]);
        }
        return getter.isNull(pathArr[pathArr.length - 1]);
    }


    private String pathSolver(String path) {
        if (!path.contains(".")) return path;
        String[] pathArr = path.split("\\.");
        return pathArr[pathArr.length - 1];
    }

    public List<?> getList(String path) {
        List<Object> result = new ArrayList<>();
        localizePath(path).getJSONArray(pathSolver(path)).forEach(result::add);
        return result;
    }

    public Object get(String path) {
        return localizePath(path).get(pathSolver(path));
    }

    public Object get(String[] path) {
        return localizePath(path).get(path[path.length - 1]);
    }

    public boolean getBoolean(String path) {
        return localizePath(path).getBoolean(pathSolver(path));
    }

    public JSONArray getJsonArray(String path) {
        return localizePath(path).getJSONArray(pathSolver(path));
    }

    public int getInt(String path) {
        return localizePath(path).getInt(pathSolver(path));
    }

    public long getLong(String path) {
        return localizePath(path).getLong(pathSolver(path));
    }

    public short getShort(String path) {
        return (short) localizePath(path).getInt(pathSolver(path));
    }

    public float getFloat(String path) {
        return localizePath(path).getFloat(pathSolver(path));
    }

    public JSONObject getJSONObject(String path) {
        return localizePath(path).getJSONObject(pathSolver(path));
    }

    public List<String> getStringList(String path) {
        return new ArrayList<>((List<String>) getList(path));
    }

    public JSONObject getConfigurationSection(String path) {
        return localizePath(path).getJSONObject(pathSolver(path));
    }

    public String getString(String path) {
        return localizePath(path).getString(pathSolver(path));
    }

    public double getDouble(String path) {
        return localizePath(path).getDouble(pathSolver(path));
    }

    public static JSONManager of(JSONObject obj) {
        return new JSONManager() {
            @Override
            public @NotNull JSONObject getJson() {
                return obj;
            }
        };
    }
}
