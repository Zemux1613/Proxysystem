package de.proxysystem.commands;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.database.modells.PlayerPlaytime;
import de.proxysystem.utils.TimeProvider;
import java.util.LinkedList;
import java.util.List;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PlaytimeCommand extends Command {

  private static final LinkedList<String> KEYWORDS = new LinkedList<>();

  public PlaytimeCommand() {
    super("playtime", "", "onlinetime", "onlinezeit");
    KEYWORDS.add("day");
    KEYWORDS.add("week");
    KEYWORDS.add("month");
    KEYWORDS.add("all");
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {
    if (args.length == 0) {
      if (commandSender instanceof ProxiedPlayer) {
        final long playtime = ProxySystem.getInstance().getPlaytimeRepository()
            .getPlaytime(((ProxiedPlayer) commandSender).getUniqueId());
        commandSender.sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.PLAYTIME_SELF_ALL)
                .replace("%time%", TimeProvider.getFormatedTime(playtime))));
      } else {
        commandSender.sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.PLAYTIME_USAGE_CONSOLE)));
      }
    } else if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
      final List<PlayerPlaytime> topTenAllTime = ProxySystem.getInstance().getPlaytimeRepository()
          .getTopTenAllTime();
      int rank = 1;
      commandSender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PLAYTIME_TOP)));
      for (final PlayerPlaytime playerPlaytime : topTenAllTime) {
        if (playerPlaytime.getNameResult().isEmpty()) {
          System.out.println("NameResult for " + rank + " rank is unknown.");
          continue;
        }
        commandSender.sendMessage(
            TextComponent
                .fromLegacyText(
                    ProxySystem
                        .getInstance()
                        .getMessageConfiguration()
                        .getMessage(Messages.PLAYTIME_TOP_LINE)
                        .replace("%rank%", rank + "")
                        .replace("%name%", playerPlaytime.getNameResult().get().username())
                        .replace("%time%",
                            TimeProvider.getFormatedTime(playerPlaytime.getPlaytime()))
                )
        );
        rank++;
      }
    } else if (args.length == 1 && KEYWORDS.contains(args[0].toLowerCase())
        && commandSender instanceof ProxiedPlayer) {
      handlePlaytimeType(commandSender, args[0], commandSender.getName(), true);
    } else if (args.length == 2 && KEYWORDS.contains(args[1].toLowerCase())
        && commandSender.hasPermission("craftergang.playtime")) {
      handlePlaytimeType(commandSender, args[1], args[0], false);
    } else {
      if (commandSender instanceof ProxiedPlayer) {
        commandSender.sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.PLAYTIME_USAGE)));
      } else {
        commandSender.sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.PLAYTIME_USAGE_CONSOLE)));
      }
    }
  }

  private void handlePlaytimeType(final CommandSender commandSender, final String type,
      final String name, final boolean isSelf) {
    ProxySystem.getInstance().getNameStorageRepository().getNameResultByName(name)
        .ifPresent(nameResult -> {
          long playtime = -1;
          String message = switch (type.toLowerCase()) {
            case "day" -> {
              playtime = ProxySystem.getInstance().getPlaytimeRepository()
                  .getDailyPlaytime(nameResult.uuid());
              yield (isSelf ? ProxySystem.getInstance().getMessageConfiguration()
                  .getMessage(Messages.PLAYTIME_SELF_DAILY)
                  : ProxySystem.getInstance().getMessageConfiguration()
                      .getMessage(Messages.PLAYTIME_OTHER_DAILY));
            }
            case "week" -> {
              playtime = ProxySystem.getInstance().getPlaytimeRepository()
                  .getWeeklyPlaytime(nameResult.uuid());
              yield (isSelf ? ProxySystem.getInstance().getMessageConfiguration()
                  .getMessage(Messages.PLAYTIME_SELF_WEAKLY)
                  : ProxySystem.getInstance().getMessageConfiguration()
                      .getMessage(Messages.PLAYTIME_OTHER_WEAKLY));
            }
            case "month" -> {
              playtime = ProxySystem.getInstance().getPlaytimeRepository()
                  .getMonthlyPlaytime(nameResult.uuid());
              yield (isSelf ? ProxySystem.getInstance().getMessageConfiguration()
                  .getMessage(Messages.PLAYTIME_SELF_MONTHLY)
                  : ProxySystem.getInstance().getMessageConfiguration()
                      .getMessage(Messages.PLAYTIME_OTHER_MONTHLY));
            }
            case "all" -> {
              playtime = ProxySystem.getInstance().getPlaytimeRepository()
                  .getPlaytime(nameResult.uuid());
              yield (isSelf ? ProxySystem.getInstance().getMessageConfiguration()
                  .getMessage(Messages.PLAYTIME_SELF_ALL)
                  : ProxySystem.getInstance().getMessageConfiguration()
                      .getMessage(Messages.PLAYTIME_OTHER_ALL));
            }
            default -> "";
          };

          if (playtime != -1) {
            commandSender.sendMessage(
                TextComponent.fromLegacyText(message.replace("%player%", name).replace("%time%",
                    TimeProvider.getFormatedTime(playtime))));
          } else {
            commandSender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.PLAYTIME_ERROR)));
          }
        });

  }
}
