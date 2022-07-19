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

import static quaks.by.ntmcore.utils.ChatUtils.isMuted;
import static quaks.by.ntmcore.utils.ChatUtils.sendLogger;

public class GlobalChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        String msg = event.getMessage();
        if(msg.startsWith("!")){
            if(p.getScoreboardTags().contains("global.off")){
                p.sendMessage(ChatColor.RED+"У вас отключён глобальный чат");
                return;
            }
            sendLogger('G',event.getPlayer().getName(),event.getMessage());
            msg = ChatUtils.unPrefix('!',msg);
            msg = ChatUtils.unSpaced(msg);
            ChatUtils.sendGlobalMessage(ChatUtils.genChatMessage(
                    "[G]", p.getName(), msg, "Глобальный чат", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"!"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.of("#f4f47c"), ChatColor.of("#9EFF86"),ChatColor.WHITE
            ),Bukkit.getOnlinePlayers(), p);
            if(!isMuted(p.getName())){
                WebhookUtil.deliverMessage(DiscordSRV.getPlugin().getOptionalTextChannel("global"), event.getPlayer(), ChatUtils.unPrefix('!', msg));
            }
        }
    }
}
