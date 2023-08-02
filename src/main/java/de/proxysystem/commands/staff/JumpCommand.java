package de.proxysystem.commands.staff;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class JumpCommand extends Command {

  public JumpCommand() {
    super("jump");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.jump")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
    }
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
    }

    if (args.length != 1) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.JUMP_USAGE)));
    }

    String targetPlayer = args[0];
    final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetPlayer);
    if (target != null) {
      ((ProxiedPlayer) sender).connect(target.getServer().getInfo());
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.JUMP_SUCCESS)
              .replace("%name%", target.getDisplayName())));
    } else {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_ONLINE)));
    }
  }
}
