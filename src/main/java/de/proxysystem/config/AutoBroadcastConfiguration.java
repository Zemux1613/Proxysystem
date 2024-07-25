package de.proxysystem.config;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.abstraction.AbstractConfiguration;
import de.proxysystem.config.enums.Messages;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class AutoBroadcastConfiguration extends AbstractConfiguration {

  @Getter
  private final static LinkedList<String> messages = new LinkedList<>();

  public AutoBroadcastConfiguration() {
    super(new File(ProxySystem.getInstance().getDataFolder(), "broadcasts.yml"));
  }

  @Override
  protected void init() {
    try {
      Configuration configuration = super.getConfiguration();

      if (configuration.get("messages") == null) {
        LinkedList<String> defaultPrefixes = new LinkedList<>();
        defaultPrefixes.add("&1Test message 1");
        defaultPrefixes.add("&2Test message 2");
        defaultPrefixes.add("&3Test message 3");
        defaultPrefixes.add("&4Test message 4");
        configuration.set("messages", defaultPrefixes);
      }

      ConfigurationProvider.getProvider(YamlConfiguration.class)
          .save(configuration, super.getFile());

      // load messages
      configuration
          .getStringList("messages")
          .stream()
          .map(s -> ChatColor.translateAlternateColorCodes('&', s))
          .map(s -> s.replace("%prefix%",
              ProxySystem.getInstance().getMessageConfiguration().getMessage(Messages.PREFIX)))
          .forEach(messages::add);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
