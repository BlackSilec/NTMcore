package quaks.by.ntmcore.enums;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import quaks.by.ntmcore.files.RoleList;

import java.awt.*;

public enum RoleManager {
    ADMINISTRATOR("admin","`"),
    MODERATOR("moderator","`"),
    HELPER("helper","`"),
    EVENT_MANAGER("event_manager","`");
    public final String id;
    public final String prefix;
    RoleManager(String id, String prefix){
        this.id = RoleList.get().getString(id);
        this.prefix = prefix;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }
    public Color getColor(){
        return DiscordSRV.getPlugin().getJda().getRoleById(id).getColor();
    }
    public String getHEXColorCode(){
        return String.format("#%02x%02x%02x", getColor().getRed(), getColor().getGreen(), getColor().getBlue());
    }
    public String getFormattedPrefix(){
        return "§"+getHEXColorCode()+prefix+"§r";
    }
    public boolean isMe(String name){
        try {
            return DiscordSRV.getPlugin().getJda().getRoleById(id).getGuild().getMember(DiscordSRV.getPlugin().getJda().getUserById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId()))).getRoles().contains(DiscordSRV.getPlugin().getJda().getRoleById(id));
        } catch (Exception e){
            return false;
        }
    }
}
