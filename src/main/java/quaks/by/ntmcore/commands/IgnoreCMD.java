package quaks.by.ntmcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class IgnoreCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = ((Player) sender);
            Bukkit.getLogger().info(((Player) sender).getScoreboardTags().toString());
            switch (args.length){
                case 0:
                    p.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
                    break;
                case 1:
                    if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
                        if(!p.getScoreboardTags().contains("ignore."+args[0])) {
                            p.addScoreboardTag("ignore."+args[0]);
                            p.sendMessage(
                                    ChatColor.GREEN + "Сообщения от данного игрока "+ChatColor.RED+"не будут"+ChatColor.GREEN+" доставляться"
                            );
                        } else {
                            p.removeScoreboardTag("ignore."+args[0]);
                            p.sendMessage(
                                    ChatColor.GREEN + "Сообщения от данного игрока "+ChatColor.RED+"будут"+ChatColor.GREEN+" доставляться"
                            );
                        }
                    } else {
                        p.sendMessage(
                                ChatColor.RED+"Ошибка: такого игрока не существует");
                    }
                    break;
                default:
                    p.sendMessage(
                            ChatColor.RED+"Неизвестная или неполная команда:\n"+
                                    ChatColor.GRAY+"/"+label+" "+ChatColor.RED+"[никнейм]");
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return null;
        }
        return Collections.emptyList();
    }
}
