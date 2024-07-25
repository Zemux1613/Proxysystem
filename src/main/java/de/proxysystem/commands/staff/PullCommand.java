package de.proxysystem.commands.staff;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PullCommand extends Command {

  public PullCommand() {
    super("pull");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.pull")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
    }
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
    }

    if (args.length != 1) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PULL_USAGE)));
    }

    String targetPlayer = args[0];
    if (targetPlayer.equalsIgnoreCase("all")) {
      ProxyServer.getInstance().getPlayers()
          .forEach(player -> pullPlayer((ProxiedPlayer) sender, player));
      return;
    }
    final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetPlayer);
    if (target != null) {
      pullPlayer((ProxiedPlayer) sender, target);
    } else {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_ONLINE)));
    }
  }

  private void pullPlayer(ProxiedPlayer sender, ProxiedPlayer target) {
    target.connect(sender.getServer().getInfo());
    target.sendMessage(TextComponent.fromLegacyText(
        ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PULL_SUCCESS)
            .replace("%server%", sender.getServer().getInfo().getName())));
  }
}
