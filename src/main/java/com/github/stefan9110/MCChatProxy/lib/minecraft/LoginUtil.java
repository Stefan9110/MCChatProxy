package com.github.stefan9110.MCChatProxy.lib.minecraft;

import com.github.stefan9110.MCChatProxy.config.Settings;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.MsaAuthenticationService;
import com.github.steveice10.mc.auth.util.MSALApplicationOptions;

public class LoginUtil {
    protected static AuthenticationService msaLogin() {
        try {
            var auth = new MsaAuthenticationService(Settings.AZURE_CLIENT_ID.getValue(), new MSALApplicationOptions.Builder().offlineAccess(true).build());
            auth.setDeviceCodeConsumer(deviceCode -> {
                if (deviceCode != null)
                    System.out.printf("%s (%s)%n", deviceCode.userCode(), deviceCode.verificationUri());
                else System.out.println("Authenticating using cached tokens...");
            });
            auth.login();
            return auth;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
