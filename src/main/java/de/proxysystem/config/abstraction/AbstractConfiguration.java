package de.proxysystem.config.abstraction;

import java.io.File;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

@Getter
public abstract class AbstractConfiguration {

  private final File file;
  private final net.md_5.bungee.config.Configuration configuration;

  @SneakyThrows
  public AbstractConfiguration(final File file) {
    this.file = file;
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdir();
    }
    if (!file.exists()) {
      file.createNewFile();
    }
    this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    init();
  }

  protected abstract void init();

  protected void initValue(Configuration configuration, String key, String defaultValue) {
    if (configuration.get(key) == null) {
      configuration.set(key, defaultValue);
    }
  }
}
