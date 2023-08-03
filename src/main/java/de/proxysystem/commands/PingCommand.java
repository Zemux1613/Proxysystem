package de.proxysystem.commands;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {

  public PingCommand() {
    super("ping");
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (commandSender instanceof ProxiedPlayer) {
      commandSender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PING)
              .replace("%ping%", ((ProxiedPlayer) commandSender).getPing() + "")));
    } else {
      commandSender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
    }
  }
}
