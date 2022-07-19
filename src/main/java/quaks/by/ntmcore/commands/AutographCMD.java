package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import quaks.by.ntmcore.files.AphList;

import java.util.ArrayList;
import java.util.List;

public class AutographCMD implements CommandExecutor {
    List<String> autographItemList = AphList.get().getStringList("autographItemList");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            assert p != null;
            ItemStack item = p.getItemInHand();
            if (item.getType() == null || item.getType().toString().contains("AIR")) {
                p.sendMessage(AphList.get().getString("itemUndefined"));
            } else if (!autographItemList.contains(item.getType().toString())) {
                p.sendMessage(ChatColor.RED + AphList.get().getString("itemNotTheList"));
                return true;
            } else {
                ItemMeta itemM = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                assert itemM != null;
                if (itemM.getLore() != null) {
                    lore = itemM.getLore();
                }
                if (lore.toString().contains("Подпись")) {
                    p.sendMessage(ChatColor.RED + AphList.get().getString("itemContainsMaxAutographs"));
                    return true;
                }
                lore.add(ChatColor.GRAY + "Подпись " + ChatColor.AQUA + ((Player) sender).getDisplayName());
                itemM.setLore(lore);
                item.setItemMeta(itemM);
            }
        }
        return true;
    }
}
