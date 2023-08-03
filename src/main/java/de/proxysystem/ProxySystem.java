package de.proxysystem;

import de.proxysystem.commands.HubCommand;
import de.proxysystem.commands.PingCommand;
import de.proxysystem.commands.WhereAmICommand;
import de.proxysystem.commands.admin.ProxyCommand;
import de.proxysystem.commands.staff.ClearChatCommand;
import de.proxysystem.commands.staff.JoinCommand;
import de.proxysystem.commands.staff.JumpCommand;
import de.proxysystem.commands.staff.PullCommand;
import de.proxysystem.commands.staff.TeamChatCommand;
import de.proxysystem.config.AutoBroadcastConfiguration;
import de.proxysystem.config.BasicFileConfiguration;
import de.proxysystem.config.CustomPrefixesConfiguration;
import de.proxysystem.config.MessageConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.database.SqlConnector;
import de.proxysystem.database.repositories.NameStorageRepository;
import de.proxysystem.database.repositories.StaffMemberRepository;
import de.proxysystem.listener.PlayerJoinListener;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;

@Getter
public class ProxySystem extends Plugin {

  @Getter
  private static final Long systemStart = System.currentTimeMillis();
  @Getter
  private static ProxySystem instance;
  private BasicFileConfiguration basicFileConfiguration;
  private MessageConfiguration messageConfiguration;
  private CustomPrefixesConfiguration customPrefixesConfiguration;
  private AutoBroadcastConfiguration autoBroadcastConfiguration;
  private SqlConnector sqlConnector;
  private NameStorageRepository nameStorageRepository;
  private StaffMemberRepository staffMemberRepository;

  @Override
  public void onEnable() {
    instance = this;
    basicFileConfiguration = new BasicFileConfiguration();
    messageConfiguration = new MessageConfiguration();

    if (basicFileConfiguration.getSetting(GeneralConfig.DISPLAY_NAME_PREFIXING)
        .equals(Boolean.TRUE.toString())) {
      customPrefixesConfiguration = new CustomPrefixesConfiguration();
    }

    if (basicFileConfiguration.getSetting(GeneralConfig.AUTO_BROADCAST)
        .equals(Boolean.TRUE.toString())) {
      autoBroadcastConfiguration = new AutoBroadcastConfiguration();
    }

    sqlConnector = new SqlConnector(basicFileConfiguration);

    nameStorageRepository = new NameStorageRepository(sqlConnector);
    staffMemberRepository = new StaffMemberRepository(sqlConnector);

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
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new JumpCommand());
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new PullCommand());
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new WhereAmICommand());
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new ClearChatCommand());
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new ProxyCommand());
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new JoinCommand());

    // register Listeners
    ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerJoinListener());
  }

  @Override
  public void onDisable() {
    sqlConnector.disconnect();
  }
}


