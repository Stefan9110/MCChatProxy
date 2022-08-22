package com.github.stefan9110.MCChatProxy.lib.minecraft;

import org.jetbrains.annotations.NotNull;

public record MinecraftServer(String address, int port) {
    public MinecraftServer(@NotNull String address, int port) {
        this.address = address;
        this.port = port;
    }
}
