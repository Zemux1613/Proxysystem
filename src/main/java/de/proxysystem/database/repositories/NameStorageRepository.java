package de.proxysystem.database.repositories;

import de.proxysystem.database.SqlConnector;
import de.proxysystem.database.modells.NameResult;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class NameStorageRepository {

  private final SqlConnector sqlConnector;
  private final LinkedList<NameResult> nameResults;

  public NameStorageRepository(SqlConnector sqlConnector) {
    this.sqlConnector = sqlConnector;
    this.nameResults = new LinkedList<>();
    createTable();
    loadNameResults();
  }

  private void createTable() {
    sqlConnector.update(
        "CREATE TABLE IF NOT EXISTS nameStorage(UUID VARCHAR(64) PRIMARY KEY, Username VARCHAR(16))");
  }

  private void loadNameResults() {
    sqlConnector.query(resultSet -> {
      try {
        while (resultSet.next()) {
          this.nameResults.add(new NameResult(UUID.fromString(resultSet.getString("uuid")),
              resultSet.getString("name")));
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }, "SELECT * FROM nameStorage");
  }

  public boolean hasNameResult(UUID uuid) {
    return this.nameResults.stream()
        .anyMatch(nameResult -> nameResult.uuid().toString().equals(uuid.toString()));
  }

  public void createUnknownUser(UUID uuid, String username) {
    this.nameResults.add(new NameResult(uuid, username));
    createOrUpdateNameResult(uuid, username);
  }

  public Optional<NameResult> getNameResultByUUID(UUID uuid) {
    return this.nameResults.stream()
        .filter(nameResult -> nameResult.uuid().toString().equals(uuid.toString())).findFirst();
  }

  public Optional<NameResult> getNameResultByName(String username) {
    return this.nameResults.stream().filter(nameResult -> nameResult.username().equals(username))
        .findFirst();
  }

  public void checkForUsernameUpdate(ProxiedPlayer player) {
    getNameResultByUUID(player.getUniqueId()).ifPresent(nameResult -> {
      if (!nameResult.username().equals(player.getName())) {
        createOrUpdateNameResult(player.getUniqueId(), player.getName());
      }
    });
  }

  public void createOrUpdateNameResult(UUID uuid, String username) {
    this.sqlConnector.update(
        "INSERT INTO nameStorage(uuid,username) VALUES (?,?) ON DUPLICATED KEY username=?",
        uuid.toString(), username, username);
  }

}
