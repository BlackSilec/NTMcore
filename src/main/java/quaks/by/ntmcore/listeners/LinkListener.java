package quaks.by.ntmcore.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LinkListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL) // EventHandler - нужно для создания прослушки ивента
    public void onPlayerJoin(PlayerJoinEvent event) {
        String discordId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(event.getPlayer().getUniqueId()); // Получаем Discord ID с помощью DiscordSrv
        if (discordId == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Твой аккаунт не привязан к Discord-у! Используй /discord link чтобы исправить это."); // получаем из ивента игрока, который вызвал этот ивент и отправляем сообщение
        } // Если ID нет, то ...
    } // PlayerJoinEvent - ивент входа игрока на сервер
}
