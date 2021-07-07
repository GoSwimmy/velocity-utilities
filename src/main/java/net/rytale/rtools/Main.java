package net.rytale.rtools;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.rytale.rtools.commands.*;
import net.rytale.rtools.events.ChatEvent;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(
        id = "rtools",
        name = "RTools",
        version = "1.0"
)
public class Main {

    private final ProxyServer server;
    private static Toml config;
    public static Path folder;

    public Toml loadConfig(Path path) {
        File folder = path.toFile();
        File file = new File(folder, "config.toml");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if(!file.exists()) {
            try(InputStream input = getClass().getResourceAsStream("/"+file.getName())) {
                if(input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            }catch(IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return new Toml().read(file);
    }

    @Inject
    public Main(ProxyServer server, Logger logger, @DataDirectory final Path folder) {
        this.server = server;
        this.folder = folder;
        config = loadConfig(folder);
    }

    @Inject
    private Logger logger;

    public static Toml getConfig() {
        return config;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        server.getEventManager().register(this, new ChatEvent(server));

        server.getCommandManager().register(new Broadcast(server), "gbroadcast", "gbc");
        server.getCommandManager().register(new Find(server), "find");
        server.getCommandManager().register(new Send(server), "send");
        server.getCommandManager().register(new SendAll(server), "sendall");
        server.getCommandManager().register(new StaffChat(server), "staffchat", "sc");
        server.getCommandManager().register(new Tp(server), "gtp");
        server.getCommandManager().register(new Reload(server), "rtreload");
    }

    public static void reload() {
        File file = new File(String.valueOf(folder), "config.toml");
        config = new Toml().read(file);
    }
}
