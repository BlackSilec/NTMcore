package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtectCMD implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            ItemStack item = p.getItemInHand();
            if (item.getType() == null || item.getType()== Material.AIR) {
                p.sendMessage(ChatColor.RED+"Возьми карту в руку");
            } else if(item.getType()==Material.FILLED_MAP){

                ItemMeta itemM = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                if (itemM.getLore() != null) {lore = itemM.getLore();}
                if (lore.toString().contains("Некопируемая")) {
                    p.sendMessage(ChatColor.RED + "Эта карта уже и так некопируема");
                    return true;
                }
                if(p.getLevel()>=30){
                    p.setLevel(p.getLevel() - 30);
                } else {
                    p.sendMessage(ChatColor.RED+"Необходим 30й уровень опыта");
                    return true;
                }
                lore.add("Некопируемая");
                itemM.setLore(lore);
                item.setItemMeta(itemM);
                p.sendMessage(ChatColor.GREEN+"Карта была защищена от копирования");
            } else {
                p.sendMessage(ChatColor.RED+"Возьми карту в руку");
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
