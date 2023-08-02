package de.proxysystem;

import de.proxysystem.commands.HubCommand;
import de.proxysystem.commands.PingCommand;
import de.proxysystem.commands.TeamChatCommand;
import de.proxysystem.config.BasicFileConfiguration;
import de.proxysystem.config.MessageConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.database.SqlConnector;
import de.proxysystem.database.repositories.NameStorageRepository;
import de.proxysystem.listener.PlayerJoinListener;
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
  private SqlConnector sqlConnector;
  private NameStorageRepository nameStorageRepository;
  @Override
  public void onEnable() {
    instance = this;
    basicFileConfiguration = new BasicFileConfiguration();
    messageConfiguration = new MessageConfiguration();

    sqlConnector = new SqlConnector(basicFileConfiguration);

    nameStorageRepository = new NameStorageRepository(sqlConnector);

    ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(
        messageConfiguration.getMessage(Messages.PREFIX) + "Starting " + getDescription().getName()
            + " by " + getDescription().getAuthor() + " v" + getDescription().getVersion()));

    // register Commands
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new PingCommand());
    if (basicFileConfiguration.getSetting(GeneralConfig.GENERAL_ENABLE_HUB_COMMAND)
        .equalsIgnoreCase(Boolean.TRUE.toString())) {
      ProxyServer.getInstance().getPluginManager().registerCommand(this, new HubCommand());
    }
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new TeamChatCommand());

    // register Listeners
    ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerJoinListener());
  }

  @Override
  public void onDisable() {
    sqlConnector.disconnect();
  }
}


