package quaks.by.ntmcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static quaks.by.ntmcore.NTMcore.*;

public class NameTagHideListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setScoreboard(MAIN_SCOREBOARD);
        main_team.addPlayer(e.getPlayer());
    }
}
