package com.github.stefan9110.MCChatProxy;

import com.github.stefan9110.MCChatProxy.config.Settings;
import com.github.stefan9110.MCChatProxy.lib.minecraft.MinecraftClient;
import com.github.stefan9110.MCChatProxy.lib.minecraft.MinecraftServer;

public class Main {
    public static void main(String[] args) {
        createFiles();
        var client = new MinecraftClient(new MinecraftServer(Settings.MINECRAFT_SERVER_ADDRESS.getValue(), Settings.MINECRAFT_SERVER_PORT.getValue()), MinecraftClient.ClientType.MICROSOFT);
        client.connect();
    }

    private static void createFiles() {
        Settings.initialize();
    }
}
