package de.proxysystem.config;

import de.proxysystem.ProxySystem;
import de.proxysystem.config.abstraction.AbstractConfiguration;
import de.proxysystem.config.enums.GeneralConfig;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BasicFileConfiguration extends AbstractConfiguration {

  private static final ConcurrentHashMap<GeneralConfig, String> settings = new ConcurrentHashMap<>();

  public BasicFileConfiguration() {
    super(new File(ProxySystem.getInstance().getDataFolder(), "config.yml"));
  }

  public String getSetting(GeneralConfig setting) {
    return settings.getOrDefault(setting, setting.getDefaultValue());
  }

  @Override
  protected void init() {
    try {
      Configuration configuration = super.getConfiguration();

      // default values
      for (GeneralConfig value : GeneralConfig.values()) {
        initValue(configuration, value.getKey(), value.getDefaultValue());
      }

      ConfigurationProvider.getProvider(YamlConfiguration.class)
          .save(configuration, super.getFile());

      // load values
      for (GeneralConfig value : GeneralConfig.values()) {
        settings.put(value, configuration.getString(value.getKey()));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
