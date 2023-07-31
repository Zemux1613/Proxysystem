package de.proxysystem.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {

    PREFIX("general.prefix", "&8[&cSystem&8]&7 "),
    NO_PERMISSION("general.noPerm", "%prefix% &cDu darfst das nicht!");

    private final String key;
    private final String defaultValue;

}
