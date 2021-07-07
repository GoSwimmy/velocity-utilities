package net.rytale.rtools.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.rytale.rtools.Main;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Broadcast implements Command {

    private final ProxyServer server;

    public Broadcast(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        if (strings.length > 0) {
            String format = Main.getConfig().getString("messages.broadcast");
            String msg = String.join(" ", strings);
            format = format.replace("%message%", msg).replace("&", "§");
            for (Player player : server.getAllPlayers()) {
                player.sendMessage(TextComponent.of(format));
            }
        } else {
            commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.broadcast-invalid").replace("&", "§")));
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.broadcast");
    }
}
