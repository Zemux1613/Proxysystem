package de.proxysystem.commands;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.database.modells.StaffMember;
import java.util.StringJoiner;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamChatCommand extends Command {

  public TeamChatCommand() {
    super("teamchat");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (!sender.hasPermission("proxysystem.teamchat")) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.NO_PERMISSION)));
      return;
    }
    if (args.length == 0) {
      sender.sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.TEAM_CHAT_ONLINE)));
      ProxySystem.getInstance().getStaffMemberRepository().getStaffMembers()
          .forEach(staffMember -> {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(staffMember.getUuid());
            String server = "§coffline";
            if (player != null) {
              server = player.getServer().getInfo().getName();
            }
            sender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.TEAM_CHAT_ONLINE_FORMAT).replace("%name%",
                        (player != null ? player.getDisplayName()
                            : ProxySystem.getInstance().getNameStorageRepository()
                                .getNameResultByUUID(staffMember.getUuid()).orElseThrow().username()))
                    .replace("%server%", ChatColor.translateAlternateColorCodes('&', server))
                    .replace("%status%", (staffMember.isTeamChatState() ? "§a✓" : "§c✕"))));
          });
    } else if (args.length == 1) {
      if (sender instanceof ProxiedPlayer player) {
        if (args[0].equalsIgnoreCase("login") || args[0].equalsIgnoreCase("logout")) {
          final StaffMember staffMember = ProxySystem.getInstance().getStaffMemberRepository()
              .getStaffMember(player.getUniqueId());
          if (staffMember == null) {
            return;
          }
          if (staffMember.isTeamChatState() && args[0].equalsIgnoreCase("logout")) {
            sender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.TEAM_CHAT_LOGOUT_SUCCESS)));
            ProxySystem.getInstance().getStaffMemberRepository()
                .updateStaffMember(player.getUniqueId(), false);
            return;
          }
          if (!staffMember.isTeamChatState() && args[0].equalsIgnoreCase("logout")) {
            sender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.TEAM_CHAT_LOGOUT_FAIL)));
            return;
          }
          if (staffMember.isTeamChatState() && args[0].equalsIgnoreCase("login")) {
            sender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.TEAM_CHAT_LOGIN_FAIL)));
            return;
          }
          if (!staffMember.isTeamChatState() && args[0].equalsIgnoreCase("login")) {
            sender.sendMessage(TextComponent.fromLegacyText(
                ProxySystem.getInstance().getMessageConfiguration()
                    .getMessage(Messages.TEAM_CHAT_LOGIN_SUCCESS)));
            ProxySystem.getInstance().getStaffMemberRepository()
                .updateStaffMember(player.getUniqueId(), true);
          }
        } else {
          sendMessage(args[0], sender);
        }
      } else {
        sendMessage(args[0], sender);
      }
    } else {
      StringJoiner messageJoiner = new StringJoiner(" ");
      for (String arg : args) {
        messageJoiner.add(arg);
      }
      sendMessage(messageJoiner.toString(), sender);
    }
  }

  public void sendMessage(String message, CommandSender from) {
    ProxySystem.getInstance().getStaffMemberRepository().getStaffMembers().forEach(staffMember -> {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(staffMember.getUuid());
      if (player != null && staffMember.isTeamChatState()) {
        player.sendMessage(TextComponent.fromLegacyText(
            ProxySystem.getInstance().getMessageConfiguration()
                .getMessage(Messages.TEAM_CHAT_FORMAT).replace("%message%", message)
                .replace("%name%",
                    (from instanceof ProxiedPlayer ? ((ProxiedPlayer) from).getDisplayName()
                        : "§4Console"))));
      }
    });
  }
}
