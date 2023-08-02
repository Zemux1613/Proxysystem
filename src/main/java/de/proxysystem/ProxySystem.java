package de.proxysystem;

import de.proxysystem.commands.HubCommand;
import de.proxysystem.commands.PingCommand;
import de.proxysystem.config.BasicFileConfiguration;
import de.proxysystem.config.MessageConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class ProxySystem extends Plugin {

  @Getter
  private static ProxySystem instance;

  private BasicFileConfiguration basicFileConfiguration;
  private MessageConfiguration messageConfiguration;

  @Override
  public void onEnable() {
    instance = this;
    basicFileConfiguration = new BasicFileConfiguration();
    messageConfiguration = new MessageConfiguration();

    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(
        messageConfiguration.getMessage(Messages.PREFIX) + "Starting " + getDescription().getName()
            + " by " + getDescription().getAuthor() + " v" + getDescription().getVersion()));

    // register Commands
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand());
    if (basicFileConfiguration.getSetting(GeneralConfig.GENERAL_ENABLE_HUB_COMMAND)
        .equalsIgnoreCase(Boolean.TRUE.toString())) {
      ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand());
    }
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}


