package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import quaks.by.ntmcore.utils.ChatUtils;

import java.util.Collections;
import java.util.List;

public class GmeCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Опиши действие, которое ты сделал/делаешь");
                return true;
            }
            String message = String.join(" ", args);
            ChatUtils.sendGlobalMessage(ChatUtils.genMeMessage(player.getName(),message,ChatColor.of("#9EFF86"),true), Bukkit.getOnlinePlayers(),player);
            if(!ChatUtils.isMuted(player.getName())){
                if (DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(player.getUniqueId()) == null) {
                    WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), player.getName() + "/gme", DiscordSRV.getAvatarUrl(player), message, null);
                } else {
                    WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), player.getName() + "/gme", DiscordSRV.getPlugin().getJda().getUserById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(player.getUniqueId())).getAvatarUrl(), message, null);
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}