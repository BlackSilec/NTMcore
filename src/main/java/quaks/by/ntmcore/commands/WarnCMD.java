package quaks.by.ntmcore.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.util.WebhookUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import quaks.by.ntmcore.files.WarnList;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static quaks.by.ntmcore.utils.ChatUtils.unSpaced;

public class WarnCMD implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length){
            case 0:
                sender.sendMessage(ChatColor.RED+"Введите ник нарушителя, мощность и причину варна после /"+label);
                return true;
            case 1:
                sender.sendMessage(ChatColor.RED+"Введите мощность и причину варна после /"+label+" "+args[0]);
                return true;
            case 2:
                sender.sendMessage(ChatColor.RED+"Введите причину варна после /"+label+" "+args[0]+" "+args[1]);
                return true;
            default:{
                if(Arrays.asList(Bukkit.getOfflinePlayers()).contains(Bukkit.getOfflinePlayer(args[0]))){
                    String name = Bukkit.getOfflinePlayer(args[0]).getName();
                    int amount;
                    if(WarnList.get().get(name+".amount")==null){
                        WarnList.get().set(name+".amount",0);
                        amount = 0;
                    } else {
                        amount = WarnList.get().getInt(name+".amount");
                    }
                    amount = amount + 1;
                    if(WarnList.get().get(name+".warns."+amount)==null){
                        WarnList.get().set(name+".warns."+amount+".power",Integer.parseInt(args[1]));
                        String[] asg = args.clone();
                        args[0] = "";
                        args[1] = "";
                        String message = String.join(" ", args);
                        WarnList.get().set(name+".warns."+amount+".reason",removeChars(message));
                        sender.getServer().spigot().broadcast(
                                new TextComponent(org.bukkit.ChatColor.RED + asg[0] + " получил варн мощностью "+ org.bukkit.ChatColor.YELLOW + asg[1] +" ("+removeChars(message)+")"
                                ));
                        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.RED);
                        embedBuilder.setDescription(":exclamation: **" + asg[0] + " получил варн мощностью "+asg[1]+" ("+unSpaced(removeChars(message))+")"+"**");
                        WebhookUtil.deliverMessage((DiscordSRV.getPlugin().getOptionalTextChannel("global")), "NTM Project Чат", "https://cdn.discordapp.com/avatars/960639909226491974/e250699c0329df0415334c63a2391cc2.webp?size=128", "", embedBuilder.build());
                    }
                    WarnList.get().set(name+".amount",amount);
                    WarnList.save();
                    if(WarnList.get().get(name+".amount")!=null&&WarnList.get().getInt(name+".amount")%3==0){
                        int am = WarnList.get().getInt(name+".amount");
                        String reasons = WarnList.get().getString(name+".warns."+am+".reason")+"; "+
                                WarnList.get().getString(name+".warns."+(am-1)+".reason")+"; "+
                                WarnList.get().getString(name+".warns."+(am-2)+".reason");
                        int num = WarnList.get().getInt(name+".warns."+am+".power")+
                                WarnList.get().getInt(name+".warns."+(am-1)+".power")+
                                WarnList.get().getInt(name+".warns."+(am-2)+".power");
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban"+" "+name+" "+num+" "+"days"+" "+reasons);
                    }
                }else{
                    sender.sendMessage(ChatColor.RED+"Такого игрока не существует");
                };
            }

        }

        return true;
    }
    void check(OfflinePlayer p){
        if(WarnList.get().get(p.getName()+".amount")!=null&&WarnList.get().getInt(p.getName()+".amount")%3==0){
            int am = WarnList.get().getInt(p.getName()+".amount");
            String reasons = WarnList.get().getString(p.getName()+".warns."+am+".reason")+"; "+
                    WarnList.get().getString(p.getName()+".warns."+(am-1)+".reason")+";"+
                    WarnList.get().getString(p.getName()+".warns."+(am-2)+".reason");
            int num = WarnList.get().getInt(p.getName()+".warns."+am+".power")+
                    WarnList.get().getInt(p.getName()+".warns."+(am-1)+".power")+
                    WarnList.get().getInt(p.getName()+".warns."+(am-2)+".power");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban "+p.getName()+" "+num+" days"+reasons);
        }
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length){
            case 1:
                return null;
        }
        return Collections.emptyList();
    }

    public String removeChars(String s){
        return s.substring(1).substring(1);
    }
}
