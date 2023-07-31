package de.proxysystem.config;

import de.proxysystem.ProxySystem;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BasicFileConfiguration extends AbstractConfiguration {

    public BasicFileConfiguration() {
        super(new File(ProxySystem.getInstance().getDataFolder(), "config.yml"));
    }

    @Override
    protected void init() {
        try {
            Configuration configuration = super.getConfiguration();

            initValue(configuration, "database.username", "admin");
            initValue(configuration, "database.password", "admin");
            initValue(configuration, "database.databaseName", "myDatabase");
            initValue(configuration, "database.host", "localhost");
            initValue(configuration, "database.port", "3306");

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, super.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
