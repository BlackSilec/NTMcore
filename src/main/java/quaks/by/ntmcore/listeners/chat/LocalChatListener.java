package quaks.by.ntmcore.listeners.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import quaks.by.ntmcore.utils.ChatUtils;

import static quaks.by.ntmcore.utils.ChatUtils.sendLogger;

public class LocalChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().isOp()||event.getPlayer().hasPermission("group.moderator")||event.getPlayer().hasPermission("group.helper"))
        {
            if (event.getMessage().charAt(0) != '!' && event.getMessage().charAt(0) != '$' && event.getMessage().charAt(0) != '@') {
                chat(event);
            }
            return;
        }
            if (event.getMessage().charAt(0) != '!' && event.getMessage().charAt(0) != '$') {
                chat(event);
            }

    }

    private void chat(AsyncPlayerChatEvent event) {
        sendLogger('L',event.getPlayer().getName(),event.getMessage());
        ChatUtils.sendLocalMessage(ChatUtils.genChatMessage(
                "[L]", event.getPlayer().getName(), event.getMessage(), "Локальный чат", "Написать "+event.getPlayer().getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,null), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+event.getPlayer().getName()+" "), ChatColor.GRAY, ChatColor.of("#9EFF86"),ChatColor.WHITE
        ),event.getPlayer(),100);
    }
}
