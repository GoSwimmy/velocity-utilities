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
import java.util.Optional;

public class Find implements Command {

    private final ProxyServer server;

    public Find(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        if (strings.length == 1) {
            String format = Main.getConfig().getString("messages.find");
            Optional<Player> player = server.getPlayer(strings[0]);
            if(player.isPresent() && player.get().getCurrentServer().isPresent()) {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.find")
                        .replace("%player%", player.get().getUsername())
                        .replace("%server%", player.get().getCurrentServer().get().getServerInfo().getName())
                        .replace("&", "§")));
            } else {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.find-not-online").replace("%player%", strings[0]).replace("&", "§")));
            }
        } else {
            commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.find-invalid").replace("&", "§")));
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.find");
    }
}
