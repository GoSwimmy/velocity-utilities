package net.rytale.rtools.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.rytale.rtools.Main;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StaffChat implements Command {

    private final ProxyServer server;

    public static HashMap<UUID, Boolean> toggle = new HashMap<>();

    public StaffChat(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        if (strings.length != 0) {
            String format = Main.getConfig().getString("messages.staffchat");
            String msg = String.join(" ", strings);
            format = format.replace("%message%", msg).replace("&", "§").replace("%player%", (commandsource instanceof Player ? ((Player) commandsource).getUsername() : "Console"));
            for (Player player : server.getAllPlayers()) {
                if(player.hasPermission("rtools.staffchat")) {
                    player.sendMessage(TextComponent.of(format));
                }
            }
        } else {
            if(commandsource instanceof Player) {
                Player p = (Player) commandsource;
                if(toggle.containsKey(p.getUniqueId()) || !toggle.get(p.getUniqueId())) {
                    toggle.put(p.getUniqueId(), true);
                    commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.staffchat-toggle-on").replace("&", "§")));
                } else {
                    toggle.put(p.getUniqueId(), false);
                    commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.staffchat-toggle-off").replace("&", "§")));
                }
            } else {
                commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.staffchat-invalid").replace("&", "§")));
            }
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.staffchat");
    }
}
