/*
*
* Plugin used for Sending Webhooks to Discord on Server Start/Stop Fully Customizable
*
* */
package de.philcode.checker;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import java.awt.*;
import java.io.File;

public final class Main extends Plugin {

    private String webhook;
    private String author;
    private String colorStart;
    private String colorStop;
    private String titel;
    private String contentStart;
    private String contentStop;
    private String footer;
    private String avatarURL;
    @Override
    public void onEnable() {
        manager = new FileManager(this);
        createFiles();
        DiscordWebhook hook = new DiscordWebhook(webhook);
        hook.setUsername(author);
        hook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(titel)
                .setDescription(contentStart)
                .setColor(Color.decode(colorStart))
                .setFooter(footer, null)
        );
        hook.setAvatarUrl(avatarURL);
        hook.execute();
    }

    private FileManager manager;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFiles() {
        File folder = new File("plugins/OnlineChecker/");
        if (!folder.exists()) folder.mkdir();
        if (!manager.exists("config.yml", "OnlineChecker")) {
            manager.createNewFile("config.yml", "OnlineChecker");
            Configuration cfg = manager.getConfiguration("config.yml", "OnlineChecker");
            cfg.set("webhookURL", "");
            cfg.set("titel", "Server Status:");
            cfg.set("avatarURL", "url_to_an_image");
            cfg.set("author", "YourServer | Status");
            cfg.set("colorStart", "#32CD32");
            cfg.set("contentStart", "The Server has Restarted.");
            cfg.set("colorStop", "#FF0000");
            cfg.set("contentStop", "The Server has stopped.");
            cfg.set("footer", "YourServer.com | Official Discord");
            manager.saveFile(manager.getFile("config.yml", "OnlineChecker"), cfg);
        }
        Configuration cfg = manager.getConfiguration("config.yml", "OnlineChecker");
        webhook = cfg.getString("webhookURL");
        author = cfg.getString("author");
        avatarURL = cfg.getString("avatarURL");
        titel = cfg.getString("titel");

        colorStart = cfg.getString("colorStart");
        contentStart = cfg.getString("contentStart");

        colorStop = cfg.getString("colorStop");
        contentStop = cfg.getString("contentStop");

        footer = cfg.getString("footer");

    }

    @Override
    public void onDisable() {
        DiscordWebhook hook = new DiscordWebhook(webhook);
        hook.setUsername(author);
        hook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(titel)
                .setDescription(contentStop)
                .setColor(Color.decode(colorStop))
                .setFooter(footer, null)
        );
        hook.setAvatarUrl(avatarURL);
        hook.execute();
    }
}
