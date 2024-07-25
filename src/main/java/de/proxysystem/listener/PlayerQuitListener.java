package de.proxysystem.listener;

import de.proxysystem.ProxySystem;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void handlePlaytime(ServerDisconnectEvent event) {
    ProxySystem.getInstance().getPlaytimeRepository()
        .handleNetworkQuit(event.getPlayer().getUniqueId());
  }
}
