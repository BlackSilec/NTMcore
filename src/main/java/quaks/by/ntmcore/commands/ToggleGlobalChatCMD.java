package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ToggleGlobalChatCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if (p.getScoreboardTags().contains("global.off")) {
                p.sendMessage(ChatColor.GREEN + "Вы включили глобальные чаты");
                p.getScoreboardTags().remove("global.off");
                p.setPlayerListName(p.getPlayerListName().replace("§8`§r",""));
                //Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player "+p.getName()+" suffix &r"));
            }else{
                p.sendMessage(ChatColor.RED + "Вы отключили глобальные чаты");
                p.getScoreboardTags().add("global.off");
                p.setPlayerListName(p.getPlayerListName()+"§8`§r");
                //Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nametagedit player "+p.getName()+" suffix &8`&r"));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
