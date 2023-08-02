package de.proxysystem.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {

    PREFIX("general.prefix", "&8[&cSystem&8]&7 "),
    NO_PERMISSION("general.noPerm", "%prefix% &cDu darfst das nicht!"),
    NOT_A_PLAYER("general.notAPlayer", "%prefix% &cDazu musst du ein Spieler sein!"),
    PING("commands.ping.message", "%prefix% Dein Ping beträgt &c%ping%&7ms&8."),
    HUB_ALREADY_JOINED("commands.hub.alreadyJoined", "%prefix% Du bist bereits in der Lobby&8."),
    NOT_HUB_FOUND("commands.hub.notHubFound", "%prefix% Es konnte keine Lobby für dich gefunden werden&8.");

    private final String key;
    private final String defaultValue;

}
