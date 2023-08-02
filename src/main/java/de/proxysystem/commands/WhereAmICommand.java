package de.proxysystem.commands;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WhereAmICommand extends Command {

  public WhereAmICommand() {
    super("whereami");
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (commandSender instanceof ProxiedPlayer) {
      commandSender.sendMessage(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.WHERE_AM_I)
              .replace("%server%", ((ProxiedPlayer) commandSender).getServer().getInfo().getName() + ""));
    } else {
      commandSender.sendMessage(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER));
    }
  }
}
