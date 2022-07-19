package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import quaks.by.ntmcore.utils.ChatUtils;

import java.util.Collections;
import java.util.List;

public class TryCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Опиши действие, которое ты сделал/делаешь");
                return true;
            }
            String message = String.join(" ", args);
            String rand;
            if(Math.random()>0.5){
                rand = ChatColor.GREEN+"[Успешно]";
            }else{
                rand = ChatColor.RED+"[Неуспешно]";
            }
            ChatUtils.sendLocalMessage(ChatUtils.genTryMessage(player.getName(),message,ChatColor.GRAY,false,rand),player,100);
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
