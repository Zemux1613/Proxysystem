package de.proxysystem.commands.admin;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ProxyCommand extends Command {

  public ProxyCommand() {
    super("proxy");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
      return;
    }
    if (!sender.hasPermission("proxysystem.proxy")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
      return;
    }

    final String hostAddress = ProxyServer.getInstance().getPlayer(sender.getName()).getServer()
        .getInfo().getAddress().getAddress().getHostAddress();

    sender.sendMessage(TextComponent.fromLegacyText(
        ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PROXY)
            .replace("%address%", hostAddress)));
  }
}
