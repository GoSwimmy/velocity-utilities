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

public class Reload implements Command {

    private final ProxyServer server;

    public Reload(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(CommandSource commandsource, @NonNull String[] strings) {
        Main.reload();
        commandsource.sendMessage(TextComponent.of(Main.getConfig().getString("messages.reload").replace("&", "§")));
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("rtools.reload");
    }
}
