package quaks.by.ntmcore.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ClickOnPlayerEventListener implements Listener
{
    @EventHandler
    public void onPlayerChat(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        if(event.getRightClicked() instanceof Player){
            Player p2 = (Player) event.getRightClicked();
            TextComponent nickname = new TextComponent(p2.getName());
            nickname.setColor(ChatColor.of("#9EFF86"));
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR,nickname);
        }
    }
}
