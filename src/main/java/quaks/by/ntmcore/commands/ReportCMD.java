package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quaks.by.ntmcore.utils.ChatUtils;

import java.awt.Color;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Репорт не был доставлен: опишите свою проблему");
                return true;
            }
            String message = String.join(" ", args);
            String world = player.getWorld().getEnvironment().name();
            if (world.equals(World.Environment.NETHER.name())) {
                world = "Нижний мир";
            }
            if (world.equals(World.Environment.NORMAL.name())) {
                world = "Верхний мир";
            }
            if (world.equals(World.Environment.THE_END.name())) {
                world = "Энд";
            }
            MessageEmbed embed = new EmbedBuilder()
                    .setDescription(new Date() + "\n" + "```" + message + "```")
                    .setColor(Color.YELLOW)
                    .setFooter("X:" + player.getLocation().getBlockX() + " Y:" + player.getLocation().getBlockY() + " Z:" + player.getLocation().getBlockZ() + " " + world)
                    .build();
            WebhookUtil.deliverMessage(DiscordSRV.getPlugin().getOptionalTextChannel("report"), player, "", embed);
            player.sendMessage(ChatColor.GREEN + "Ожидайте, скоро с вами свяжется член администрации");
            {
                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    if (p1.hasPermission("group.helper")||p1.hasPermission("group.moderator")||p1.isOp()) {
                        p1.spigot().sendMessage(ChatUtils.genChatMessage("[R]", player.getName(), message, "Репорт", "Написать " + player.getName(), null, new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + player.getName() + " "), net.md_5.bungee.api.ChatColor.AQUA, net.md_5.bungee.api.ChatColor.of("#9EFF86"), net.md_5.bungee.api.ChatColor.WHITE));
                        p1.playNote(p1.getLocation(), Instrument.BELL, Note.flat(0, Note.Tone.A));
                    }
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
