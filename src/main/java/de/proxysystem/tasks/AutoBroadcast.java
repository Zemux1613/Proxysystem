package de.proxysystem.tasks;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.AutoBroadcastConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.tasks.abstraction.RepeatableTask;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AutoBroadcast extends RepeatableTask {

  private static int index = 0;

  public AutoBroadcast() {
    super(Long.parseLong(ProxySystem.getInstance().getBasicFileConfiguration()
        .getSetting(GeneralConfig.AUTO_BROADCAST_DELAY)), 0);
  }

  @Override
  public void run() {
    if (index > AutoBroadcastConfiguration.getMessages().size()) {
      index = 0;
    }
    for (final ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
      final boolean whiteSpaces = ProxySystem.getInstance().getBasicFileConfiguration()
          .getSetting(GeneralConfig.BROADCAST_WITH_WHITESPACES).equals(Boolean.TRUE.toString());
      final BaseComponent[] emptyComponent = TextComponent.fromLegacyText("");
      if (whiteSpaces) {
        all.sendMessage(emptyComponent);
      }
      all.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.BROADCAST_CHAT_FORMAT)
              .replace("%message%", AutoBroadcastConfiguration.getMessages().get(index))));
      if (whiteSpaces) {
        all.sendMessage(emptyComponent);
      }
    }
    index++;
  }
}
