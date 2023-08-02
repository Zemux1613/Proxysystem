package de.proxysystem.listener;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.enums.Messages;
import de.proxysystem.database.modells.StaffMember;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void handleNameResultUpdates(PostLoginEvent event) {
    if (!ProxySystem.getInstance().getNameStorageRepository()
        .hasNameResult(event.getPlayer().getUniqueId())) {
      ProxySystem.getInstance().getNameStorageRepository()
          .createUnknownUser(event.getPlayer().getUniqueId(), event.getPlayer().getName());
    }
    ProxySystem.getInstance().getNameStorageRepository().checkForUsernameUpdate(event.getPlayer());
  }

  @EventHandler
  public void handleStaffMember(PostLoginEvent event) {
    final StaffMember staffMember = ProxySystem.getInstance().getStaffMemberRepository()
        .getStaffMember(event.getPlayer().getUniqueId());
    if (event.getPlayer().hasPermission("proxysystem.staffMember") && staffMember == null) {
      ProxySystem.getInstance().getStaffMemberRepository()
          .createStaffMember(event.getPlayer().getUniqueId());
    }

    if (staffMember != null) {
      if (!event.getPlayer().hasPermission("proxysystem.staffMember")) {
        ProxySystem.getInstance().getStaffMemberRepository()
            .deleteStaffMember(staffMember.getUuid());
        return;
      }
      event.getPlayer().sendMessage(TextComponent.fromLegacyText(
          ProxySystem.getInstance().getMessageConfiguration()
              .getMessage(Messages.TEAM_CHAT_JOIN_MESSAGE)
              .replace("%status%", staffMember.isTeamChatState() ? "§a✓" : "§c✕")));
    }
  }
}
