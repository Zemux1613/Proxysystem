package de.proxysystem.database;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.BasicFileConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class SqlConnector {

  private final BasicFileConfiguration basicFileConfiguration;
  private Connection connection;

  public SqlConnector(BasicFileConfiguration basicFileConfiguration) {
    this.basicFileConfiguration = basicFileConfiguration;
    connect();
  }

  private void connect() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(
            "jdbc::mysql/" + basicFileConfiguration.getSetting(GeneralConfig.DATABASE_HOST) + ":"
                + basicFileConfiguration.getSetting(GeneralConfig.DATABASE_PORT) + "/"
                + basicFileConfiguration.getSetting(GeneralConfig.DATABASE_DATABASE)
                + "?autoReconnect=true",
            basicFileConfiguration.getSetting(GeneralConfig.DATABASE_USERNAME),
            basicFileConfiguration.getSetting(GeneralConfig.DATABASE_PASSWORD));
        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.DATABASE_CONNECTION_CONNECTED)));
      } catch (SQLException e) {
        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.DATABASE_CONNECTION_FAIL)));
        throw new RuntimeException(e);
      }
    }
  }

  public void disconnect() {
    if (connection != null) {
      try {
        connection.close();
        ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.DATABASE_CONNECTION_DISCONNECT)));
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }


}
