package de.proxysystem.listener;

import de.proxysystem.ProxySystem;
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

}
