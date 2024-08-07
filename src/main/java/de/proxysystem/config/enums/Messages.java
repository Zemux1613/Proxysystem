package de.proxysystem.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {

  PREFIX("general.prefix", "&8[&cSystem&8]&7"),
  NO_PERMISSION("general.noPerm", "%prefix% &cDu darfst das nicht&8!"),
  NOT_A_PLAYER("general.notAPlayer", "%prefix% &cDazu musst du ein Spieler sein&8!"),
  NOT_ONLINE("general.noPlayerOnlne", "%prefix% Der spieler &e%name% &7ist offline&8."),
  PING("commands.ping.message", "%prefix% Dein Ping beträgt &c%ping%&7ms&8."),
  HUB_ALREADY_JOINED("commands.hub.alreadyJoined", "%prefix% Du bist bereits in der Lobby&8."),
  NOT_HUB_FOUND("commands.hub.notHubFound",
      "%prefix% Es konnte keine Lobby für dich gefunden werden&8."),
  DATABASE_CONNECTION_CONNECTED("database.connection.connected",
      "%prefix% Es konnte sich erfolgreich zur Datenbank verbunden werden&8."),
  DATABASE_CONNECTION_FAIL("database.connection.fail",
      "%prefix% Es konnte keine Verbindung zur Datenbank hergestellt werden&8."),
  DATABASE_CONNECTION_DISCONNECT("database.connection.disconnect",
      "%prefix% Die Verbindung zur Datenbank wurde getrennt&8."),
  TEAM_CHAT_ONLINE("commands.teamChat.onlineList",
      "%prefix% Aktuell sind folgende Teammitglieder erfasst&8:"),
  TEAM_CHAT_ONLINE_FORMAT("commands.teamChat.onlineListFormat",
      "%name% &8- &e%server% &8- %status%"),
  TEAM_CHAT_LOGIN_SUCCESS("commands.teamChat.login.success",
      "%prefix% Du hast dich im Teamchat eingeloggt&8."),
  TEAM_CHAT_LOGOUT_SUCCESS("commands.teamChat.logout.success",
      "%prefix% Du hast dich im Teamchat ausgeloggt&8."),
  TEAM_CHAT_LOGIN_FAIL("commands.teamChat.login.fail",
      "%prefix% Du bist bereits im Teamchat eingeloggt&8."),
  TEAM_CHAT_LOGOUT_FAIL("commands.teamChat.logout.fail",
      "%prefix% Du bist bereits im Teamchat ausgeloggt&8."),
  TEAM_CHAT_FORMAT("commands.teamChat.chatFormat", "%name%&8: &7%message%"),
  TEAM_CHAT_JOIN_MESSAGE("commands.teamChat.joinMessage", "&8[&bTeamChat&8] &7Status&8: %status%"),
  JUMP_USAGE("commands.jump.usage", "%prefix% Bitte verwende /jump <Name>"),
  JUMP_SUCCESS("commands.jump.success", "%prefix% Du bist nun bei %name%&8!"),
  PULL_USAGE("commands.pull.usage", "%prefix% Bitte verwende /pull <Name>"),
  PULL_SUCCESS("commands.pull.success", "%prefix% Du bist nun auf %server%&8."),
  WHERE_AM_I("commands.whereami", "%prefix% Du bist auf &e%server%&8!"),
  CLEAR_CHAT_USAGE("commands.clearchat.usage", "%prefix% Bitte verwende /clearchat"),
  CLEAR_CHAT_SUCCESS("commands.clearchat.usage", "%prefix% Der Chat wurde geleert&8."),
  PROXY("commands.proxy", "%prefix% Du bist momentan auf folgender Proxy:&c %address%&8."),
  JOIN("commands.join", "%prefix% Bitte Verwende /join <ServerName>"),
  UPTIME_USAGE("commands.uptime", "%prefix% Bitte verwende /uptime"),
  UPTIME("commands.uptime", "%prefix% Das System ist seit %time% online&8."),
  BROADCAST_USAGE("commands.broadcast.usage", "%prefix% Bitte verwende /broadcast <Nachricht>"),
  BROADCAST_CHAT_FORMAT("commands.broadcast.format", "%prefix% %message%"),
  PLAYTIME_USAGE("commands.playtime.usage",
      "%prefix% &cBitte verwende /playtime [<day, week, month, all>]"),
  PLAYTIME_USAGE_CONSOLE("commands.playtime.usageConsole",
      "%prefix% &cBitte verwende /playtime <Name> [<day, week, month, all>]"),
  PLAYTIME_SELF_ALL("commands.playtime.self.all",
      "%prefix% &aDu bist bereits &e%time% &aauf unseren Netzwerk&8."),
  PLAYTIME_SELF_DAILY("commands.playtime.self.day",
      "%prefix% &aDu bist bereits &e%time% &aauf unseren Netzwerk heute gewesen&8."),
  PLAYTIME_SELF_WEAKLY("commands.playtime.self.week",
      "%prefix% &aDu bist bereits &e%time% &aauf unseren Netzwerk diese Woche gewesen&8."),
  PLAYTIME_SELF_MONTHLY("commands.playtime.self.month",
      "%prefix% &aDu bist bereits &e%time% &aauf unseren Netzwerk diesen Monat gewesen&8."),
  PLAYTIME_OTHER_ALL("commands.playtime.other.all",
      "%prefix% &e%player% &aist bereits &e%time% &aauf unseren Netzwerk&8."),
  PLAYTIME_OTHER_DAILY("commands.playtime.other.day",
      "%prefix% &e%player% &aist bereits &e%time% &aauf unseren Netzwerk heute gewesen&8."),
  PLAYTIME_OTHER_WEAKLY("commands.playtime.other.week",
      "%prefix% &e%player% &aist bereits &e%time% &aauf unseren Netzwerk diese Woche gewesen&8."),
  PLAYTIME_OTHER_MONTHLY("commands.playtime.other.month",
      "%prefix% &e%player% &aist bereits &e%time% &aauf unseren Netzwerk diesen Monat gewesen&8."),
  PLAYTIME_TOP("commands.playtime.top", "%prefix% &7Top 10 der Onlinezeit&8:"),
  PLAYTIME_TOP_LINE("commands.playtime.topLine", "§c%rank%§8. §e%name% §8- §c%time%"),
  PLAYTIME_ERROR("commands.playtime.error",
      "%prefix% &cEs ist ein Fehler aufgetreten. Bitte versuche es erneut.");

  private final String key;
  private final String defaultValue;

}
