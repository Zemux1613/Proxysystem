package de.proxysystem.commands.staff;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UptimeCommand extends Command {

  public UptimeCommand() {
    super("uptime");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.uptime")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
    }
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NOT_A_PLAYER)));
    }

    if (args.length != 1) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.UPTIME_USAGE)));
    }

    sender.sendMessage(TextComponent.fromLegacyText(
        ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.UPTIME)
            .replace("%time%", formatTime())));
  }

  public String formatTime() {
    long milli = System.currentTimeMillis() - ProxySystem.getSystemStart();
    milli /= 1000;
    long seconds = (milli % 60);
    milli /= 60;
    long minutes = (milli % 60);
    milli /= 60;
    long hours = (milli % 24);
    milli /= 24;
    long days = milli;
    return String.format("%02d Tag(e),%02d Stunde(n),%02d Minute(n), %02d Sekunde(n)", days, hours,
        minutes, seconds);
  }
}
