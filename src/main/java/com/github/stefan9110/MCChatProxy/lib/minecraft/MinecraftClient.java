package com.github.stefan9110.MCChatProxy.lib.minecraft;

import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import org.jetbrains.annotations.NotNull;

import java.net.Proxy;

import static com.github.stefan9110.MCChatProxy.lib.minecraft.LoginUtil.msaLogin;

public class MinecraftClient {
    private final MinecraftServer server;
    private final ClientType clientType;
    private final Session client;
    private final AuthenticationService account;

    public MinecraftClient(@NotNull MinecraftServer server, ClientType type) {
        this.server = server;
        this.clientType = type;

        // Attempt authentication here
        account = authenticate();
        if (account == null) throw new RuntimeException("e"); //TODO: handle this shit

        client = new TcpClientSession(server.address(), server.port(), new MinecraftProtocol(account.getSelectedProfile(), account.getAccessToken()), null);
        var session = new SessionService();
        session.setProxy(Proxy.NO_PROXY);
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, session);
    }

    public void connect() {
        client.connect();
    }

    public ClientType getClientType() {
        return clientType;
    }

    public @NotNull MinecraftServer getServer() {
        return server;
    }

    public @NotNull AuthenticationService getAccount() {
        return account;
    }

    private AuthenticationService authenticate() {
        switch (clientType) {
            case MICROSOFT -> {
                return msaLogin();
            }
            default -> {
                return null;
            }
        }
    }

    public enum ClientType {
        MICROSOFT, MOJANG, OFFLINE;
    }
}
