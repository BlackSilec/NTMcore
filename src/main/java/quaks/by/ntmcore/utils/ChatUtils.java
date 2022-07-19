package quaks.by.ntmcore.utils;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import quaks.by.ntmcore.enums.RoleManager;
import quaks.by.ntmcore.files.MuteList;
import quaks.by.ntmcore.files.RoleList;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    public static String unPrefix(char prefix, String text){
        String a = unSpaced(text);
        if(prefix == a.charAt(0)){
            return a.substring(1);
        }
        return unSpaced(a);
    }
    public static String unSpaced(String text){
        return  text.trim();
    }
    public static void sendGlobalMessage(TextComponent message, Collection<? extends Player> res, Player sender) {
        if(ChatUtils.isMuted(sender.getName())){
            sender.sendMessage(ChatColor.RED + "Вы не можете писать в чат!");
            return;
        }
        if(sender.getScoreboardTags().contains("global.off")){
            sender.sendMessage(ChatColor.RED + "Вы отключили глобальный чат!");
            return;
        }
        for (Player p : res) {
            if (!p.getScoreboardTags().contains("global.off")){
                p.spigot().sendMessage(message);
            }
        }
    }
    public static void sendGlobalMessage(TextComponent message, Collection<? extends Player> res) {
        for (Player p : res) {
            if (!p.getScoreboardTags().contains("global.off")){
                p.spigot().sendMessage(message);
            }
        }
    }
    public static void sendGroupMessage(TextComponent message, Collection<? extends Player> res, String permission, boolean forOp) {
        for (Player p : res) {
            if(forOp){
                if (p.hasPermission(permission) || p.isOp()) {
                    p.spigot().sendMessage(message);
                }
            }else{
                if (p.hasPermission(permission)){
                    p.spigot().sendMessage(message);
                }
            }
        }
    }
    public static void sendLocalMessage(TextComponent message, Player sender, int r) {
            if (MuteList.get().get(sender.getName() + ".muted")!=null && isMuted(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "Вы не можете писать в чат!");
                return;
            }
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (sender.getWorld().getEnvironment().name().equals(other.getWorld().getEnvironment().name())) {
                if (other.getLocation().distance(sender.getLocation()) <= r) {
                    other.spigot().sendMessage(message);
                }
            }
        }
    }
    public static TextComponent genChatMessage(String chatType, String name, String message, String chatTypeDescription, String nameDescription, ClickEvent chatTypeClickEvent, ClickEvent nameClickEvent, ChatColor chatTypeColor, ChatColor nameColor, ChatColor messageColor) {
        TextComponent urls = getUrls(message);
        message = unSpaced(removeUrl(message)); // todo Сделать выделение ссылок и вложений ВЕЗДЕ
        TextComponent chat_type = new TextComponent(chatType);
        chat_type.setColor(chatTypeColor);
        chat_type.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(chatTypeDescription).color(ChatColor.GRAY).create()));
        chat_type.setClickEvent(chatTypeClickEvent);
        TextComponent line = new TextComponent(" | ");
        line.setColor(ChatColor.GRAY);
        TextComponent Tname = new TextComponent(name);
        Tname.setColor(nameColor);
        Tname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(nameDescription).color(ChatColor.GRAY).create()));
        Tname.setClickEvent(nameClickEvent);
        TextComponent dot = new TextComponent(" • ");
        dot.setColor(ChatColor.GRAY);
        TextComponent Tmessage = new TextComponent(message);
        Tmessage.setColor(messageColor);
        TextComponent result = new TextComponent();
        result.addExtra(chat_type);
        result.addExtra(line);
        insertPrefix(name,result);
        result.addExtra(Tname);
        result.addExtra(dot);
        result.addExtra(Tmessage);
        result.addExtra(urls);
        return result;
    }
    public static TextComponent genDoMessage(String name, String message, ChatColor nameColor, boolean global){
        TextComponent urls = getUrls(message);
        message = unSpaced(removeUrl(message));
        TextComponent message1 = new TextComponent(message);
        message1.setItalic(true);
        TextComponent dot = new TextComponent(" • ");
        dot.setColor(ChatColor.GRAY);
        TextComponent Tname = new TextComponent(name);
        Tname.setColor(nameColor);
        TextComponent result = new TextComponent();
        if(global){
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /gdo").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gdo "));
        }else{
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /do").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/do "));
        }
        result.addExtra(message1);
        result.addExtra(urls);
        result.addExtra(dot);
        insertPrefix(name,result);
        Tname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Написать "+name).color(ChatColor.GRAY).create()));
        Tname.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+name));
        result.addExtra(Tname);
        return result;
    }
    public static TextComponent genMeMessage(String name, String message, ChatColor nameColor, boolean global){
        TextComponent urls = getUrls(message);
        message = unSpaced(removeUrl(message));
        TextComponent message1 = new TextComponent(message);
        message1.setItalic(true);
        TextComponent dot = new TextComponent("• ");
        dot.setColor(ChatColor.GRAY);
        TextComponent Tname = new TextComponent(name+" ");
        Tname.setColor(nameColor);
        Tname.setItalic(true);
        Tname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Написать "+name).color(ChatColor.GRAY).create()));
        Tname.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+name));
        TextComponent result = new TextComponent();
        result.addExtra(dot);
        insertPrefix(name,result);
        result.addExtra(Tname);
        if(global){
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /gme").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gme "));
        }else{
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /me").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/me "));
        }
        result.addExtra(message1);
        result.addExtra(urls);
        return result;
    }
    public static TextComponent genTryMessage(String name, String message, ChatColor nameColor, boolean global, String rand){
        TextComponent urls = getUrls(message);
        message = unSpaced(removeUrl(message));
        TextComponent message1 = new TextComponent(message);
        TextComponent dot = new TextComponent("• ");
        dot.setColor(ChatColor.GRAY);
        TextComponent Tname = new TextComponent(name+" ");
        Tname.setColor(nameColor);
        Tname.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Написать "+name).color(ChatColor.GRAY).create()));
        Tname.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell "+name));
        TextComponent result = new TextComponent();
        result.addExtra(dot);
        insertPrefix(name,result);
        result.addExtra(Tname);
        if(global){
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /gtry").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gtry "));
        }else{
            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Выполнить /try").color(ChatColor.GRAY).create()));
            message1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/try "));
        }
        result.addExtra(message1);
        result.addExtra(urls);
        result.addExtra(new TextComponent(" "+rand));
        return result;
    }
    public static void insertPrefix(String name, TextComponent result){
        if(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId())!=null){
            if(RoleManager.ADMINISTRATOR.isMe(name)){
                TextComponent prefix = new TextComponent(RoleManager.ADMINISTRATOR.getPrefix());
                prefix.setColor(ChatColor.of(RoleManager.ADMINISTRATOR.getColor()));
                result.addExtra(prefix);
            }else if(RoleManager.MODERATOR.isMe(name)){
                TextComponent prefix = new TextComponent(RoleManager.MODERATOR.getPrefix());
                prefix.setColor(ChatColor.of(RoleManager.MODERATOR.getColor()));
                result.addExtra(prefix);
            }else if(RoleManager.HELPER.isMe(name)){
                TextComponent prefix = new TextComponent(RoleManager.HELPER.getPrefix());
                prefix.setColor(ChatColor.of(RoleManager.HELPER.getColor()));
                result.addExtra(prefix);
            }else if(RoleManager.EVENT_MANAGER.isMe(name)){
                TextComponent prefix = new TextComponent(RoleManager.EVENT_MANAGER.getPrefix());
                prefix.setColor(ChatColor.of(RoleManager.EVENT_MANAGER.getColor()));
                result.addExtra(prefix);
            }
        }
    }
    public static boolean isNumeric(String str) {
        try {
            double var1 = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }
    public static void sendLogger(char prefix, String name, String message){
        Bukkit.getLogger().info("["+prefix+"] | "+name+" • "+unPrefix(prefix,message));
    }
    public static void sendPrivateMessage(TextComponent message, Player p) {
        p.spigot().sendMessage(message);
    }
    public static TextComponent genTellMessage(String name1, String name2, String message, String reply) {
        TextComponent names = new TextComponent(name1);
        names.setColor(ChatColor.GOLD);
        names.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Ответить " + reply)
                .color(ChatColor.GRAY)
                .create()));
        names.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + reply + " "));
        TextComponent separator = new TextComponent(" → ");
        separator.setColor(ChatColor.GRAY);
        names.addExtra(separator);
        TextComponent names2 = new TextComponent(name2);
        names2.setColor(ChatColor.GOLD);
        names.addExtra(names2);
        TextComponent sk = new TextComponent("[");
        sk.addExtra(names);
        TextComponent sk2 = new TextComponent("]");
        sk2.setColor(ChatColor.WHITE);
        sk2.addExtra(" :" + message);
        sk.addExtra(sk2);

        return sk;
    }
    public static boolean isEmpty(String message){
        return message.equals("");
    }
    public static boolean isMuted(String name){
        if(MuteList.get().getBoolean(name+".muted")){
            Date buf = new Date();
            if (MuteList.get().get(name + ".mute_date") == null) {
                return false;
            }
            if (buf.after((Date) MuteList.get().get(name + ".mute_date"))) {
                MuteList.get().set(name + ".muted", false);
                MuteList.save();
                DiscordUtil.removeRolesFromMember(DiscordUtil.getMemberById(DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(Bukkit.getOfflinePlayer(name).getUniqueId())), DiscordUtil.getRole(RoleList.get().getString("mute")));
                return false;
            } else {
                return true;
            }
        }else {return false;}
    }
    public static String removeUrl(String commentstr) {
        // rid of ? and & in urls since replaceAll can't deal with them
        String commentstr1 = commentstr.replaceAll("\\?", "").replaceAll("\\&", "");

        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr1.replaceAll(m.group(i).replaceAll("\\?", "").replaceAll("\\&", ""),"").trim();
            i++;
        }
        return commentstr;
    }
    public static TextComponent getUrls(String commentstr) {
        TextComponent t = new TextComponent();
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            TextComponent t1;
            //commentstr = commentstr.replaceAll(m.group(i),"").trim();
            if(i==0){
                t1 = new TextComponent("[Ссылка]");
            }else{
                t1 = new TextComponent(" [Ссылка]");
            }
            t1.setColor(ChatColor.GRAY);
            t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Перейти").color(ChatColor.GRAY).create()));
            t1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, m.group(i)));
            t.addExtra(t1);
            i++;
        }
        return t;
    }
}


