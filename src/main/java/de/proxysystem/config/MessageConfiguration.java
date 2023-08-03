package de.proxysystem.config;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.abstraction.AbstractConfiguration;
import de.proxysystem.config.enums.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MessageConfiguration extends AbstractConfiguration {

  private static final ConcurrentHashMap<Messages, String> messages = new ConcurrentHashMap<>();

  public MessageConfiguration() {
    super(new File(ProxySystem.getInstance().getDataFolder(), "messages.yml"));
  }

  public String getMessage(Messages message) {
    return messages.getOrDefault(message, message.getDefaultValue());
  }

  @Override
  protected void init() {
    try {
      Configuration configuration = super.getConfiguration();

      // default messages
      for (Messages value : Messages.values()) {
        initValue(configuration, value.getKey(), value.getDefaultValue());
      }

      ConfigurationProvider.getProvider(YamlConfiguration.class)
          .save(configuration, super.getFile());

      // load messages
      for (Messages value : Messages.values()) {
        messages.put(value,
            ChatColor.translateAlternateColorCodes('&', configuration.getString(value.getKey()))
                .replace("%prefix%", getMessage(Messages.PREFIX)));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
