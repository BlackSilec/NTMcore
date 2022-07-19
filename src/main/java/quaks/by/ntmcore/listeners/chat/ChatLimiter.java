package quaks.by.ntmcore.listeners.chat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatLimiter implements Listener {
    @EventHandler
    public void chatLimiter(AsyncPlayerChatEvent event){
        if(event.getMessage().length()>200){
            event.getPlayer().sendMessage(ChatColor.RED + "Сообщение слишком длинное");
            event.setCancelled(true);
        }
    }
}
