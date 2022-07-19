package quaks.by.ntmcore.listeners.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static quaks.by.ntmcore.utils.ChatUtils.*;

public class VanillaChatDisabler implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if(isEmpty(unPrefix('!',event.getMessage()))){
            event.setMessage("!☺");
        }
        event.setCancelled(true);
    }
}
