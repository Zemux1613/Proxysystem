package de.proxysystem.config;

import de.proxysystem.ProxySystem;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CustomPrefixesConfiguration extends AbstractConfiguration {

  @Getter
  private final static ConcurrentHashMap<String, String> prefixes = new ConcurrentHashMap<>();

  public CustomPrefixesConfiguration() {
    super(new File(ProxySystem.getInstance().getDataFolder(), "prefixes.yml"));
  }

  @Override
  void init() {
    try {
      Configuration configuration = super.getConfiguration();

      if (configuration.get("prefixes") == null) {
        LinkedList<String> defaultPrefixes = new LinkedList<>();
        defaultPrefixes.add("&4Admin &8| &4;proxysystem.admin");
        defaultPrefixes.add("&a;none");
        configuration.set("prefixes", defaultPrefixes);
      }

      ConfigurationProvider.getProvider(YamlConfiguration.class)
          .save(configuration, super.getFile());

      // load prefixes
      configuration.getStringList("prefixes").forEach(s -> {
        final String[] split = s.split(";");
        prefixes.put(split[1], split[0]);
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
