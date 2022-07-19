package quaks.by.ntmcore.listeners.chat;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import quaks.by.ntmcore.utils.ChatUtils;

import static quaks.by.ntmcore.utils.ChatUtils.sendLogger;

public class TradeChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
            Player p = event.getPlayer();
            String msg = event.getMessage();
            if(event.getMessage().startsWith("$")){
                sendLogger('$',event.getPlayer().getName(),event.getMessage());
                msg = ChatUtils.unPrefix('$',msg);
                msg = ChatUtils.unSpaced(msg);
                ChatUtils.sendGlobalMessage(
                        ChatUtils.genChatMessage(
                                "[$]", p.getName(), msg, "Торговый чат", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"$"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.GREEN, ChatColor.of("#9EFF86"),ChatColor.WHITE
                        ), Bukkit.getOnlinePlayers(),p
                );
            }
    }
}
