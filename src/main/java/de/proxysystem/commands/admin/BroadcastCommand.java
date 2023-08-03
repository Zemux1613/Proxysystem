package de.proxysystem.commands.admin;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.GeneralConfig;
import de.proxysystem.config.enums.Messages;
import java.util.StringJoiner;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCommand extends Command {

  public BroadcastCommand() {
    super("broadcast", "", "bc");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.broadcast")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
    }
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
    }
    if (args.length == 0) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.BROADCAST_USAGE)));
    }
    final StringJoiner stringJoiner = new StringJoiner(" ");
    for (String arg : args) {
      stringJoiner.add(arg);
    }
    for (final ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
      final boolean whiteSpaces = ProxySystem.getInstance().getBasicFileConfiguration()
          .getSetting(GeneralConfig.BROADCAST_WITH_WHITESPACES)
          .equalsIgnoreCase(Boolean.TRUE.toString());
      final BaseComponent[] emptyComponent = TextComponent.fromLegacyText("");
      if (whiteSpaces) {
        all.sendMessage(emptyComponent);
      }
      all.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.BROADCAST_CHAT_FORMAT).replace("%message%",
                  ChatColor.translateAlternateColorCodes('&', stringJoiner.toString()))));
      if (whiteSpaces) {
        all.sendMessage(emptyComponent);
      }
    }
  }
}
