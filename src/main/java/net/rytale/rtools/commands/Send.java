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

public class Send implements Command {

    private final ProxyServer server;

    public Send(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        if (strings.length == 2) {
            if(server.getServer(strings[1]).isPresent()) {
                Optional<Player> ot = server.getPlayer(strings[0]);
                if(ot.isPresent() && ot.get().getCurrentServer().isPresent()) {
                    Optional<RegisteredServer> s = server.getServer(strings[1]);
                    String format = Main.getConfig().getString("messages.send").replace("%player%", ot.get().getUsername()).replace("%server%", s.get().getServerInfo().getName()).replace("&", "§");
                    ot.get().createConnectionRequest(s.get()).connect();
                    commandsource.sendMessage(TextComponent.of(format));
                } else {
                    commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.send-not-online").replace("%player%", strings[0]).replace("&", "§")));
                }
            } else {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.send-not-server").replace("%server%", strings[1]).replace("&", "§")));
            }
        } else {
            commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.send-invalid").replace("&", "§")));
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.send");
    }
}