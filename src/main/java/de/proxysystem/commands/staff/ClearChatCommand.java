package de.proxysystem.commands.staff;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ClearChatCommand extends Command {

  public ClearChatCommand() {
    super("clearchat");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.clearchat")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
      return;
    }

    if (args.length != 0) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.CLEAR_CHAT_USAGE)));
    }

    for (int i = 0; i < 150; i++) {
      ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(""));
    }

    ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(
        ProxySystem.getInstance().getMessageConfiguration()
            .getMessage(Messages.CLEAR_CHAT_SUCCESS)));
  }
}
