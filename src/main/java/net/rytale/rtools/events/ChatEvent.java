package net.rytale.rtools.events;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.rytale.rtools.Main;
import net.rytale.rtools.commands.StaffChat;

public class ChatEvent {

    private final ProxyServer server;

    public ChatEvent(ProxyServer server) {
        this.server = server;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChat(PlayerChatEvent event) {
        Player p = event.getPlayer();
        if(StaffChat.toggle.containsKey(p.getUniqueId()) && StaffChat.toggle.get(p.getUniqueId())) {
            event.setResult(PlayerChatEvent.ChatResult.denied());
            String format = Main.getConfig().getString("messages.staffchat");
            String msg = String.join(" ", event.getMessage());
            format = format.replace("%message%", msg).replace("&", "§").replace("%player%", p.getUsername());
            for (Player player : server.getAllPlayers()) {
                if(player.hasPermission("rtools.staffchat")) {
                    player.sendMessage(TextComponent.of(format));
                }
            }
        }
    }
}
