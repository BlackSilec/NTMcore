package quaks.by.ntmcore.listeners.chat;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import quaks.by.ntmcore.utils.ChatUtils;

public class ChatCapsFixer implements Listener {
    @EventHandler
    public void chatLimiter(AsyncPlayerChatEvent event){
        String message = event.getMessage();
        message = ChatUtils.unPrefix('!',message);
        long chars = (message.chars().count());
        message = message.replaceAll("[^A-Za-zА-Яа-я]+", "");
        long uppers = (message.chars().filter(Character::isUpperCase).count());
        if(uppers!=0){
            if (chars / uppers < 2) {
                event.getPlayer().sendMessage(ChatColor.RED + "Ваше сообщение содержит 50% или более символов в верхнем регистре");
                event.setMessage(event.getMessage().toLowerCase());
            }
        }
    }
}
