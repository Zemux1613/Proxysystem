package de.proxysystem.commands.staff;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class JoinCommand extends Command {

  public JoinCommand() {
    super("join");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    final ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!player.hasPermission("proxysystem.join")) {
      player.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
      return;
    }
    player.sendMessage(TextComponent.fromLegacyText(
        ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.JOIN)));
  }
}
