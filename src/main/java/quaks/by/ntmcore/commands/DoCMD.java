package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import quaks.by.ntmcore.utils.ChatUtils;

import java.util.Collections;
import java.util.List;

public class DoCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Напишите состояние предмета с которым вы взаимодействуете.\nПример: /do Портфель открыт");
                return true;
            }
            String message = String.join(" ", args);
            ChatUtils.sendLocalMessage(ChatUtils.genDoMessage(player.getName(),message,ChatColor.GRAY, true),player,100);
            
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
