package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quaks.by.ntmcore.files.MuteList;
import quaks.by.ntmcore.files.RoleList;

import java.awt.*;
import java.util.*;
import java.util.List;

import static quaks.by.ntmcore.utils.ChatUtils.isNumeric;

public class MuteCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 0: {
                sender.sendMessage(
                        ChatColor.RED + "Неизвестная или неполная команда:\n" +
                                ChatColor.GRAY + "/" + label + " " + ChatColor.RED + "[никнейм]");
                return true;
            }
            case 1: {
                mute(args[0],sender);
                return true;
            }
            case 2: {
                if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    sender.sendMessage(ChatColor.RED + "Ошибка: такого игрока не существует");
                    return true;
                }
                if (!isNumeric(args[1])) {
                    sender.sendMessage(ChatColor.RED + "Ошибка: дата указана не верно");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Ошибка: дата указана не верно");
                return true;
            }
            case 3: {
                mute(args[0],args[1],args[2],sender);
                return true;
            }
            default: {
                sender.sendMessage(ChatColor.RED + "Ошибка: Слишком много аргументов");
                return true;
            }
        }
    }
    private void mute(String name, CommandSender sender){
        if(MuteList.get().get(name + ".muted") != null){
            if (MuteList.get().getBoolean(name + ".muted")) {
                sender.getServer().spigot().broadcast(new TextComponent(ChatColor.GREEN + name + " был размьючен"));
                EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.GREEN);
                embedBuilder.setDescription(":loud_sound: **" + name + " был размьючен"+"**");
                WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId())),DiscordUtil.getRole(RoleList.get().getString("mute")));
                MuteList.get().set(name + ".muted", false);
                MuteList.save();
                return;
            }
        }
        if(Bukkit.getOfflinePlayer(name).hasPlayedBefore()){
            MuteList.get().set(name + ".muted", true);
            MuteList.save();
            EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.RED);
            embedBuilder.setDescription(":mute: **" + name + " был замьючен навсегда"+"**");
            sender.getServer().spigot().broadcast(new TextComponent(ChatColor.RED + name + " был замьючен навсегда"));
            WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
            DiscordUtil.addRolesToMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId())),DiscordUtil.getRole(RoleList.get().getString("mute")));
        } else {
            sender.sendMessage(ChatColor.RED + "Ошибка: такого игрока не существует");
        }
    }
    private void mute(String name, String time, String type, CommandSender sender){
        if(MuteList.get().get(name + ".muted") != null){
            if (MuteList.get().getBoolean(name + ".muted")) {
                sender.sendMessage(ChatColor.RED + "Ошибка: игрок уже замьючен");
                return;
            }
        }
        if(Bukkit.getOfflinePlayer(name).hasPlayedBefore()){
            if(isNumeric(time)){
                if(type.equals("seconds")||type.equals("minutes")||type.equals("hours")||type.equals("days")){
                    Date d = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    MuteList.get().set(name + ".muted", true);
                    EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.RED);
                    switch (type){
                        case "seconds":{
                            c.add(Calendar.SECOND, Integer.parseInt(time));
                            sender.getServer().spigot().broadcast(new TextComponent(ChatColor.RED + name + " был замьючен на "+ ChatColor.YELLOW + time + " секунд"));
                            embedBuilder.setDescription(":mute: **" + name + " был замьючен на "+time+" секунд"+"**");
                            break;
                        }
                        case "minutes":{
                            c.add(Calendar.MINUTE, Integer.parseInt(time));
                            sender.getServer().spigot().broadcast(new TextComponent(ChatColor.RED + name + " был замьючен на "+ ChatColor.YELLOW + time + " минут"));
                            embedBuilder.setDescription(":mute: **" + name + " был замьючен на "+time+" минут"+"**");
                            break;
                        }
                        case "hours":{
                            c.add(Calendar.HOUR, Integer.parseInt(time));
                            sender.getServer().spigot().broadcast(new TextComponent(ChatColor.RED + name + " был замьючен на "+ ChatColor.YELLOW + time + " часов"));
                            embedBuilder.setDescription(":mute: **" + name + " был замьючен на "+time+" часов"+"**");
                            break;
                        }
                        case "days":{
                            c.add(Calendar.HOUR, Integer.parseInt(time)*24);
                            sender.getServer().spigot().broadcast(new TextComponent(ChatColor.RED + name + " был замьючен на "+ ChatColor.YELLOW + time + " дней"));
                            embedBuilder.setDescription(":mute: **" + name + " был замьючен на "+time+" дней"+"**");
                            break;
                        }
                    }
                    WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
                    Date d1 = c.getTime();
                    MuteList.get().set(name + ".mute_date", d1);
                    MuteList.save();
                    DiscordUtil.addRolesToMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId())),DiscordUtil.getRole(RoleList.get().getString("mute")));
                }else{
                    sender.sendMessage(ChatColor.RED + "Ошибка: дата указана не верно");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "Ошибка: дата указана не верно");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Ошибка: такого игрока не существует");
        }
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length==1){return null;}
        if(args.length == 3){
            String[] l = {"seconds", "minutes", "hours", "days"};
            return Arrays.asList(l);
        }
        return Collections.emptyList();
    }
}
