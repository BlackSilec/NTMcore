package quaks.by.ntmcore.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.*;
import github.scarsz.discordsrv.dependencies.emoji.EmojiParser;
import github.scarsz.discordsrv.util.DiscordUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import quaks.by.ntmcore.utils.ChatUtils;

import static quaks.by.ntmcore.utils.ChatUtils.unSpaced;

public class DiscordSRVListener {
    private final Plugin plugin;

    public DiscordSRVListener(Plugin plugin) {
        this.plugin = plugin;
}
    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        // Example of using JDA's events
        // We need to wait until DiscordSRV has initialized JDA, thus we're doing this inside DiscordReadyEvent
        DiscordUtil.getJda().addEventListener(new JDAListener(plugin));

        // ... we can also do anything other than listen for events with JDA now,
        plugin.getLogger().info("Chatting on Discord with " + DiscordUtil.getJda().getUsers().size() + " users!");
        // see https://ci.dv8tion.net/job/JDA/javadoc/ for JDA's javadoc
        // see https://github.com/DV8FromTheWorld/JDA/wiki for JDA's wiki
    }
    @Subscribe
    public void discordGlobalChatMessageProcessed(DiscordGuildMessagePostProcessEvent event){
        event.setCancelled(true);
        if(event.getChannel().getId().equals(DiscordSRV.getPlugin().getOptionalTextChannel("global").getId())){
                ChatUtils.sendGlobalMessage(ChatUtils.genChatMessage(
                        "[G]", Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId())).getName(), EmojiParser.parseToAliases(unSpaced(event.getMessage().getContentDisplay())), "Сообщение отправлено через Discord","Скопировать: "+event.getAuthor().getAsTag(), null, new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,event.getAuthor().getAsTag()), ChatColor.of("#9e86ff"), ChatColor.of("#9EFF86"),ChatColor.WHITE
                ),Bukkit.getOnlinePlayers());
                ChatUtils.sendLogger('D',Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId())).getName(),event.getMessage().getContentRaw());
            return;
        }
    }
    @Subscribe
    public void discordAdminChatMessageProcessed(DiscordGuildMessagePostProcessEvent event){
        event.setCancelled(true);
        if(event.getChannel().getId().equals(DiscordSRV.getPlugin().getOptionalTextChannel("admin").getId())){
            ChatUtils.sendGroupMessage(ChatUtils.genChatMessage(
                    "[A]", Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId())).getName(), EmojiParser.parseToAliases(unSpaced(event.getMessage().getContentDisplay())), "Сообщение отправлено через Discord","Скопировать: "+event.getAuthor().getAsTag(), null, new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,event.getAuthor().getAsTag()), ChatColor.RED, ChatColor.of("#9EFF86"),ChatColor.of("#9e86ff")
            ),Bukkit.getOnlinePlayers(),"admin.chat",true);
            ChatUtils.sendLogger('A',Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getAuthor().getId())).getName(),event.getMessage().getContentRaw());
            return;
        }
    }
    @Subscribe
    public void deathMessage(DeathMessagePreProcessEvent e){
        if(e.getPlayer().getScoreboardTags().contains("muted.true")){
            e.setCancelled(true);
        }
    }
}
