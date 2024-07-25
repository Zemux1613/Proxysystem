package de.proxysystem.database.repositories;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.proxysystem.ProxySystem;
import de.proxysystem.database.modells.PlayerPlaytime;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlaytimeRepository {

  private static final HashMap<UUID, Long> playtime = Maps.newHashMap();

  public PlaytimeRepository() {
    ProxySystem.getInstance().getSqlConnector().update(
        "CREATE TABLE IF NOT EXISTS player_playtime (uuid VARCHAR(36) NOT NULL,playtime BIGINT NOT NULL,timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY (uuid, timestamp))");
  }

  public void handleNetworkJoin(final UUID uuid) {
    playtime.put(uuid, System.currentTimeMillis());
  }

  public void handleNetworkQuit(final UUID uuid) {
    if (!playtime.containsKey(uuid)) {
      return;
    }
    final Long reamingTime = System.currentTimeMillis() - playtime.get(uuid);
    savePlaytime(uuid, reamingTime);
    playtime.remove(uuid);
  }

  public void savePlaytime(UUID uuid, long playtime) {
    ProxySystem.getInstance().getSqlConnector()
        .update("INSERT INTO player_playtime (uuid, playtime, timestamp) VALUES (?, ?, ?)",
            uuid.toString(), playtime, Timestamp.valueOf(LocalDateTime.now()));
  }

  public long getPlaytime(UUID uuid) {
    return ProxySystem.getInstance().getSqlConnector().query(resultSet -> {
          return getPlayTime(uuid, resultSet);
        }, "SELECT SUM(playtime) AS total_playtime FROM player_playtime WHERE uuid = ?",
        uuid.toString()).orElse(-1L);
  }

  public List<PlayerPlaytime> getTopTenAllTime() {
    return ProxySystem.getInstance().getSqlConnector().query(resultSet -> {
              final LinkedList<@Nullable PlayerPlaytime> topPlayers = Lists.newLinkedList();
              while (resultSet.next()) {
                final UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                if (!playtime.containsKey(uuid)) {
                  topPlayers.add(new PlayerPlaytime(
                      ProxySystem.getInstance().getNameStorageRepository().getNameResultByUUID(uuid),
                      resultSet.getLong("total_playtime")));
                } else {
                  topPlayers.add(new PlayerPlaytime(
                      ProxySystem.getInstance().getNameStorageRepository().getNameResultByUUID(uuid),
                      resultSet.getLong("total_playtime") + (System.currentTimeMillis() - playtime.get(
                          uuid))));
                }
              }
              return topPlayers;
            },
            "SELECT SUM(playtime) AS total_playtime, uuid FROM player_playtime GROUP BY uuid ORDER BY total_playtime DESC LIMIT 10")
        .orElse(new LinkedList<>());
  }

  @SneakyThrows
  private long getPlayTime(UUID uuid, ResultSet resultSet) {
    if (!playtime.containsKey(uuid)) {
      return resultSet.getLong("total_playtime");
    } else {
      return resultSet.getLong("total_playtime") + (System.currentTimeMillis() - playtime.get(
          uuid));
    }
  }

  public long getWeeklyPlaytime(UUID uuid) {
    final LocalDateTime startOfWeek = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY)
        .toLocalDate().atStartOfDay();
    return ProxySystem.getInstance().getSqlConnector().query(resultSet -> {
          return getPlayTime(uuid, resultSet);
        },
        "SELECT SUM(playtime) AS total_playtime FROM player_playtime WHERE uuid = ? AND timestamp >= ?",
        uuid.toString(), Timestamp.valueOf(startOfWeek)).orElse(-1L);
  }

  public long getMonthlyPlaytime(UUID uuid) {
    final LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate()
        .atStartOfDay();
    return ProxySystem.getInstance().getSqlConnector().query(resultSet -> {
          return getPlayTime(uuid, resultSet);
        },
        "SELECT SUM(playtime) AS total_playtime FROM player_playtime WHERE uuid = ? AND timestamp >= ?",
        uuid.toString(), Timestamp.valueOf(startOfMonth)).orElse(-1L);
  }

  public long getDailyPlaytime(final UUID uuid) {
    return ProxySystem.getInstance().getSqlConnector().query(resultSet -> {
          return getPlayTime(uuid, resultSet);
        },
        "SELECT SUM(playtime) AS daily_playtime FROM player_playtime WHERE uuid = ? AND DATE(timestamp) = CURDATE()",
        uuid.toString()).orElse(-1L);
  }
}
