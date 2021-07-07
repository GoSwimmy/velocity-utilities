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

public class Tp implements Command {

    private final ProxyServer server;

    public Tp(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        if (strings.length == 1) {
            Optional<Player> ot = server.getPlayer(strings[0]);
            if(ot.isPresent() && ot.get().getCurrentServer().isPresent()) {
                if(commandsource instanceof Player) {
                    ot.get().getCurrentServer().ifPresent(serverConnection -> ((Player) commandsource).createConnectionRequest(serverConnection.getServer()).fireAndForget());
                }
            } else {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.tp-not-online").replace("%player%", strings[0]).replace("&", "§")));
            }
        } else {
            commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.tp-invalid").replace("&", "§")));
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.tp");
    }
}
