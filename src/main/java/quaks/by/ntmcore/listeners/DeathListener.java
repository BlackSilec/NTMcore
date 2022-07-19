package quaks.by.ntmcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import quaks.by.ntmcore.files.MuteList;

public class DeathListener implements Listener {
    @EventHandler
    public void deathListener(PlayerDeathEvent event){
        if(MuteList.get().getBoolean(event.getEntity().getName() + ".muted")){
            event.setDeathMessage(null);
        }
    }
}
