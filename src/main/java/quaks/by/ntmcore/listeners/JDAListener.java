package quaks.by.ntmcore.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.GuildUnavailableEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.ButtonClickEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.guild.GuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.components.ActionRow;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.components.Button;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JDAListener extends ListenerAdapter {

    private final Plugin plugin;

    public JDAListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override // we can use any of JDA's events through ListenerAdapter, just by overriding the methods
    public void onGuildUnavailable(@NotNull GuildUnavailableEvent event) {
        plugin.getLogger().severe("Oh no " + event.getGuild().getName() + " went unavailable :(");
    }
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(event.getChannel().getId().equals(DiscordSRV.getPlugin().getOptionalTextChannel("request").getId())){
            if(event.isWebhookMessage()){
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setDescription("**Заявка:** `"+event.getAuthor().getName()+"`")
                        .setColor(Color.YELLOW)
                        .build()).setActionRow(sendButtons(event.getAuthor().getName())).queue();
            }
        }
    }
    private static List<Button> sendButtons(String name){
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.success("add","Принять " + name));
        buttons.add(Button.danger("skip","Отклонить " + name));
        return buttons;
    }
    @Override
    public void onButtonClick(ButtonClickEvent event){
        String name;
        ActionRow original = event.getMessage().getActionRows().get(0);
        switch (event.getButton().getId()){
            case "add":
                name = event.getButton().getLabel().replace("Принять ","");
                event.getMessage().editMessage(new EmbedBuilder()
                        .setDescription("**Заявка:** `"+name+"`\nОдобрено "+Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getUser().getId())).getName())
                        .setColor(Color.GREEN)
                        .build()).queue();
                event.getMessage().editMessageComponents(original).setActionRows().queue();
                Bukkit.getScheduler().scheduleSyncDelayedTask(DiscordSRV.getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "easywl add "+name));
                break;
            case "skip":
                name = event.getButton().getLabel().replace("Отклонить ","");
                event.getMessage().editMessage(new EmbedBuilder()
                        .setDescription("**Заявка:** `"+name+"`\nОтклонено "+Bukkit.getOfflinePlayer(DiscordSRV.getPlugin().getAccountLinkManager().getUuid(event.getUser().getId())).getName())
                        .setColor(Color.RED)
                        .build()).queue();
                event.getMessage().editMessageComponents(original).setActionRows().queue();
                break;
        }
    }
}
