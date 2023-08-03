package de.proxysystem.config.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneralConfig {

    DATABASE_HOST("database.host", "localhost"),
    DATABASE_PORT("database.port", "3306"),
    DATABASE_DATABASE("database.database", "myDatabase"),
    DATABASE_PASSWORD("database.password" , "changeMe"),
    DATABASE_USERNAME("database.username", "admin"),
    DISPLAY_NAME_PREFIXING("general.customPrefixes", "true"),
    GENERAL_ENABLE_HUB_COMMAND("general.hub.enableCustomCommand", "false"),
    GENERAL_HUB_GROUP_NAME("general.hub.serverGroup", "Lobby"),
    BROADCAST_WITH_WHITESPACES("general.broadcastWithWhitespaces", "true");

    private String key;
    private String defaultValue;

}
