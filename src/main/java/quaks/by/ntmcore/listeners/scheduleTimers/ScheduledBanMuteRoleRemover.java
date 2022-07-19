package quaks.by.ntmcore.listeners.scheduleTimers;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import quaks.by.ntmcore.files.MuteList;
import quaks.by.ntmcore.files.RoleList;
import quaks.by.ntmcore.utils.ChatUtils;

import java.util.Date;

public class ScheduledBanMuteRoleRemover {
    public void scheduleTimer(Plugin plugin, final World world) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            long time = world.getTime();
            if (time == 13000) {
                for(OfflinePlayer p : plugin.getServer().getOfflinePlayers()){
                    if (ChatUtils.isMuted(p.getName())){
                            DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(p.getUniqueId())), DiscordUtil.getRole(RoleList.get().getString("mute")));
                    }
                    if (!p.isBanned()){
                        DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(p.getUniqueId())), DiscordUtil.getRole(RoleList.get().getString("ban")));
                    }
                }
            }
        }, 1, 1);
    }
}
