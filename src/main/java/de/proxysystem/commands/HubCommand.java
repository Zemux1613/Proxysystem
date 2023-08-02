package de.proxysystem.commands;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Comparator;

public class HubCommand extends Command {

  public HubCommand() {
    super("hub");
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (commandSender instanceof final ProxiedPlayer player) {
      final String serverName = player.getServer().getInfo().getName();
      final String hubServerGroup = ProxySystem.getInstance().getBasicFileConfiguration()
          .getSetting(GeneralConfig.GENERAL_HUB_GROUP_NAME).toLowerCase();
      if (serverName.toLowerCase().contains(hubServerGroup)) {
        commandSender.sendMessage(ProxySystem.getInstance().getMessageConfiguration()
            .getMessage(Messages.HUB_ALREADY_JOINED));
        return;
      }
      ProxyServer.getInstance().getServers().values().stream()
          .filter(serverInfo -> serverInfo.getName().toLowerCase().contains(hubServerGroup))
          .min(Comparator.comparingInt(o -> o.getPlayers().size())).ifPresentOrElse(player::connect,
              () -> player.sendMessage(TextComponent.fromLegacyText(
                  ProxySystem.getInstance().getMessageConfiguration()
                      .getMessage(Messages.NOT_HUB_FOUND))));
    } else {
      commandSender.sendMessage(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER));
    }
  }
}
