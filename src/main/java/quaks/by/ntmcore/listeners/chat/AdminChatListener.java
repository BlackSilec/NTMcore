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

public class AdminChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().isOp() || event.getPlayer().hasPermission("group.moderator") || event.getPlayer().hasPermission("group.helper")){
            Player p = event.getPlayer();
            String msg = event.getMessage();
            if(event.getMessage().startsWith("@")){
                msg = ChatUtils.unPrefix('@',msg);
                msg = ChatUtils.unSpaced(msg);
                ChatUtils.sendGroupMessage(
                        ChatUtils.genChatMessage(
                                "[A]", p.getName(), msg, "Администрация", "Написать "+p.getName(), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"@"), new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/tell "+p.getName()+" "), ChatColor.RED, ChatColor.of("#9EFF86"),ChatColor.WHITE
                        ),Bukkit.getOnlinePlayers(),"admin.chat",true
                );
                WebhookUtil.deliverMessage(DiscordSRV.getPlugin().getOptionalTextChannel("admin"), event.getPlayer(),msg);
            }
        }
        }
    }

