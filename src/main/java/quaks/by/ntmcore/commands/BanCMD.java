package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quaks.by.ntmcore.files.MuteList;
import quaks.by.ntmcore.files.RoleList;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static quaks.by.ntmcore.utils.ChatUtils.isNumeric;
import static quaks.by.ntmcore.utils.ChatUtils.unSpaced;

public class BanCMD implements CommandExecutor, TabExecutor {
    String[] lsts = {"hours","days"};
    List<String> list =  Arrays.asList(lsts);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 0:
                sender.sendMessage(ChatColor.RED+"Введите ник нарушителя, время и причину бана после /"+label);
                return true;
            case 1:
                sender.sendMessage(ChatColor.RED+"Введите время и причину бана после /"+label+" "+args[0]);
                return true;
            case 2:
                if(isNumeric(args[1])) {
                    sender.sendMessage(ChatColor.RED + "Введите тип времени и причину бана после /" + label + " " + args[0] + " " + args[1]);
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED + "Некорректный формат даты");
                    return true;
                }
            case 3:
                if(!isNumeric(args[1])) {
                    sender.sendMessage(ChatColor.RED + "Некорректный формат даты");
                    return true;
                }
                if (args[2].equals("days")||args[2].equals("hours")){
                    sender.sendMessage(ChatColor.RED + "Введите причину бана после /" + label + " " + args[0] + " " + args[1] + " " + args[2]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Некорректный формат даты");
                    return true;
                }
                return true;
            default:
            {
                if(!isNumeric(args[1])) {
                    sender.sendMessage(ChatColor.RED + "Некорректный формат даты");
                    return true;
                }
                if(Arrays.asList(Bukkit.getOfflinePlayers()).contains(Bukkit.getOfflinePlayer(args[0]))){
                    String[] args2 = args.clone();
                    args2[0] = "";
                    args2[1] = "";
                    args2[2] = "";
                    String message2 = String.join(" ", args2);
                    if(args[2].equals("days")){
                        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(args[0],ChatColor.RED+message2,new Date(System.currentTimeMillis()+ 60L *60*1000*24*Integer.parseInt(args[1])),null);
                        sender.getServer().spigot().broadcast(
                                new TextComponent(org.bukkit.ChatColor.RED + args[0] + " был забанен на "+ org.bukkit.ChatColor.YELLOW + args[1] + ChatColor.RED+ " дней (" + unSpaced(removeChars(message2)) +")"
                                ));
                        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.RED);
                        embedBuilder.setDescription(":no_entry_sign: **" + args[0] + " был забанен на "+args[1]+" дней ("+unSpaced(message2)+")"+"**");
                        WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
                    }
                    if(args[2].equals("hours")){
                        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(args[0],ChatColor.RED+message2,new Date(System.currentTimeMillis()+ 60L *60*1000*Integer.parseInt(args[1])),null);
                        sender.getServer().spigot().broadcast(
                                new TextComponent(org.bukkit.ChatColor.RED + args[0] + " был забанен на "+ org.bukkit.ChatColor.YELLOW + args[1] + ChatColor.RED+ " часов (" + unSpaced(removeChars(message2)) +")"
                                ));
                        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.RED);
                        embedBuilder.setDescription(":no_entry_sign: **" + args[0] + " был забанен на "+args[1]+" часов ("+unSpaced(message2)+")"+"**");
                        WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
                    }
                    DiscordUtil.addRolesToMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId())), DiscordUtil.getRole(RoleList.get().getString("ban")));
                    String name = args[0];
                    args[0] = "";
                    args[1] = "";
                    args[2] = "";
                    String message = String.join(" ", args);
                    if(Bukkit.getServer().getPlayer(name)!=null) {
                        Bukkit.getServer().getPlayer(name).kickPlayer(ChatColor.RED + "Вы были забанены.\n" + removeChars(message));
                    }
                }else{
                    sender.sendMessage(ChatColor.RED+"Такого игрока не существует");
                }
            }
        }
        return true;
    }
    public String removeChars(String s){
        return s.substring(1).substring(1).substring(1);
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length){
            case 1: return null;
            case 3: return list;
            default:return Collections.emptyList();
        }
    }
}
