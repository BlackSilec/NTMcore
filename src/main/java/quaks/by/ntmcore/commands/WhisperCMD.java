package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quaks.by.ntmcore.utils.ChatUtils;

import java.util.Collections;
import java.util.List;

public class WhisperCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            TextComponent base = new TextComponent(ChatColor.GRAY+p.getName()+" прошептал • ");
            base.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/wh "));
            base.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Прошептать в ответ").color(ChatColor.GRAY).create()));
            TextComponent msg = new TextComponent(String.join(" ", args));
            msg.setHoverEvent(null);
            TextComponent result = new TextComponent();
            ChatUtils.insertPrefix(sender.getName(),result);
            result.addExtra(base);
            result.addExtra(msg);
            ChatUtils.sendLocalMessage(result,p,5
            );
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
