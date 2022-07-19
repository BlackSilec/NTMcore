package quaks.by.ntmcore.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quaks.by.ntmcore.files.SpectatorHistory;

import java.util.Collections;
import java.util.List;

public class SpectatorCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            switch (args.length){
                case 0:{
                    if(SpectatorHistory.get().get(player.getName()+".pos")==null){
                        SpectatorHistory.get().set(player.getName() + ".pos", player.getLocation());
                        SpectatorHistory.save();
                        player.setGameMode(GameMode.SPECTATOR);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1));
                    }else{
                        player.teleport((Location) SpectatorHistory.get().get(player.getName()+".pos"));
                        SpectatorHistory.get().set(player.getName() + ".pos", null);
                        SpectatorHistory.save();
                        player.setGameMode(GameMode.SURVIVAL);
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    }
                    break;
                }
                case 1:{
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target==null){
                        player.sendMessage(ChatColor.RED+"Игрок оффлайн");
                        break;
                    }
                    if(SpectatorHistory.get().get(player.getName()+".pos")==null){
                        SpectatorHistory.get().set(player.getName() + ".pos", player.getLocation());
                        SpectatorHistory.save();
                    }
                    player.setGameMode(GameMode.SPECTATOR);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 1));
                    player.teleport(target.getLocation());
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 1:
                return null;
            default:
                return Collections.emptyList();
        }
    }
}
