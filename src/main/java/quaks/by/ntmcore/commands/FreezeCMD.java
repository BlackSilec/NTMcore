package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static quaks.by.ntmcore.NTMcore.freeze_team;

public class FreezeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0){return true;}
        TextComponent suggest = new TextComponent("[Разморозить]");
        suggest.setColor(ChatColor.GRAY);
        suggest.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Разморозить")
                .color(ChatColor.GRAY)
                .create()));
        suggest.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/freeze "+args[0]));
        TextComponent freeze_msg = new TextComponent("Игрок "+args[0]+" был заморожен ");
        freeze_msg.setColor(ChatColor.RED);
        freeze_msg.addExtra(suggest);
        if(!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()){
            sender.sendMessage(ChatColor.RED + "Игрок "+args[0]+" офлайн");
            return true;
        }
        if (!freeze_team.hasPlayer(Bukkit.getOfflinePlayer(args[0]))) {
            freeze_team.addPlayer(Bukkit.getOfflinePlayer(args[0]));
            sender.sendMessage(freeze_msg);
        } else {
            freeze_team.removePlayer(Bukkit.getOfflinePlayer(args[0]));
            sender.sendMessage(ChatColor.GREEN + "Игрок "+args[0]+" был разморожен");
        }
        //sender.sendMessage(freeze_team.getPlayers().toString());
        return true;
    }
}
