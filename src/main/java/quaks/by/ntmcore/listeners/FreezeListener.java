package quaks.by.ntmcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static quaks.by.ntmcore.NTMcore.freeze_team;


public class FreezeListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(freeze_team.hasPlayer(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventory(InventoryDragEvent e){
        Player p = (Player) e.getViewers().toArray()[0];
        if(freeze_team.hasPlayer(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (freeze_team.hasPlayer(p)) {
                e.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void onDamageTo(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (freeze_team.hasPlayer(p)) {
                e.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void onUse(PlayerInteractEvent e){
            Player p = e.getPlayer();
            if (freeze_team.hasPlayer(p)) {
                e.setCancelled(true);
            }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(freeze_team.hasPlayer(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(freeze_team.hasPlayer(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(freeze_team.hasPlayer(p)){
            e.setCancelled(true);
        }
    }
}
