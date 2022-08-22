package com.github.stefan9110.MCChatProxy.config;

import com.github.stefan9110.MCChatProxy.util.configuration.FileConfiguration;
import com.github.stefan9110.MCChatProxy.util.configuration.JSONConfiguration;
import org.jetbrains.annotations.NotNull;

public enum Settings implements FileConfiguration {
    AZURE_CLIENT_ID("Minecraft-Proxy.Azure-Client-Id", "abcd1234-efgh-5678-ij90-klmn1234op56"),
    MINECRAFT_SERVER_ADDRESS("Minecraft-Proxy.Server.Address", "127.0.0.1"),
    MINECRAFT_SERVER_PORT("Minecraft-Proxy.Server.Port", 25565),
    MINECRAFT_ACCOUNT_TYPE("Minecraft-Proxy.Account.Type", "MICROSOFT"),
    MINECRAFT_ACCOUNT_USERNAME("Minecraft-Proxy.Account.Username", "username"),
    MINECRAFT_ACCOUNT_PASSWORD("Minecraft-Proxy.Account.Password", "password"),

    DISCORD_TOKEN("Discord.Token", "T0K3NH3R3T0K3NH3R3T0K3NH3R3T0K3NH3R3");

    private final @NotNull Object defaultValue;
    private final @NotNull String path;
    private static JSONConfiguration configuration;

    Settings(@NotNull String path, @NotNull Object defaultValue) {
        this.defaultValue = defaultValue;
        this.path = path;
    }

    @Override
    public @NotNull JSONConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public @NotNull String getPath() {
        return path;
    }

    @Override
    public Object getDefault() {
        return defaultValue;
    }

    public static JSONConfiguration getFile() {
        return configuration;
    }


    public static void initialize() {
        configuration = new JSONConfiguration("Settings", "config");
        for (Settings key : values()) key.setDefault();
        configuration.save();
    }
}
