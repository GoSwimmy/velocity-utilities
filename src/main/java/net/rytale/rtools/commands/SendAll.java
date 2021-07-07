package net.rytale.rtools.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.text.TextComponent;
import net.rytale.rtools.Main;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SendAll implements Command {

    private final ProxyServer server;

    public SendAll(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {

        if (strings.length == 1) {
            if(server.getServer(strings[0]).isPresent()) {
                Optional<RegisteredServer> s = server.getServer(strings[0]);
                String format = Main.getConfig().getString("messages.sendall").replace("%server%", s.get().getServerInfo().getName()).replace("&", "§");
                for(Player all : server.getAllPlayers()) {
                    all.createConnectionRequest(s.get()).connect();
                    commandsource.sendMessage(TextComponent.of(format));
                }
            } else {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.sendall-not-server").replace("%server%", strings[0]).replace("&", "§")));
            }
        } else {
            commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.sendall-invalid").replace("&", "§")));
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.sendall");
    }
}
