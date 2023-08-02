package de.proxysystem.database.repositories;

import de.proxysystem.database.SqlConnector;
import de.proxysystem.database.modells.StaffMember;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;
import lombok.Getter;

public class StaffMemberRepository {

  private final SqlConnector sqlConnector;
  @Getter
  private final LinkedList<StaffMember> staffMembers;

  public StaffMemberRepository(SqlConnector sqlConnector) {
    this.sqlConnector = sqlConnector;
    staffMembers = new LinkedList<>();
    createTable();
    loadStaffMembers();
  }

  private void createTable() {
    sqlConnector.update("CREATE TABLE IF NOT EXISTS staffMembers(uuid VARCHAR(64) NOT NULL,"
        + "teamChatState INT NOT NULL DEFAULT 1, FOREIGN KEY (uuid) REFERENCES nameStorage(uuid))");
  }

  private void loadStaffMembers() {
    sqlConnector.query(resultSet -> {
      try {
        while (resultSet.next()) {
          this.staffMembers.add(new StaffMember(UUID.fromString(resultSet.getString("uuid")),
              resultSet.getInt("teamChatState") == 1));
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }, "SELECT * FROM staffMembers");
  }

  /**
   * @param uuid target player uuid
   * @return known staffMember or null
   */
  public StaffMember getStaffMember(final UUID uuid) {
    return this.staffMembers.stream()
        .filter(staffMember -> staffMember.getUuid().toString().equals(uuid.toString())).findFirst()
        .orElse(null);
  }

  public void createStaffMember(final UUID uuid) {
    this.staffMembers.add(new StaffMember(uuid, true));
    createOrUpdateStaffMember(uuid, true);
  }

  public void updateStaffMember(UUID uuid, boolean teamChatState) {
    final StaffMember staffMember = getStaffMember(uuid);
    if (staffMember == null) {
      createStaffMember(uuid);
      updateStaffMember(uuid, teamChatState);
      return;
    }
    staffMember.setTeamChatState(teamChatState);
    createOrUpdateStaffMember(uuid, teamChatState);
  }

  private void createOrUpdateStaffMember(UUID uuid, boolean teamChatState) {
    sqlConnector.update(
        "INSERT INTO staffMembers (uuid, teamChatState) VALUES (?,?) ON DUPLICATE KEY UPDATE teamChatState=?",
        uuid.toString(), (teamChatState ? 1 : 0), (teamChatState ? 1 : 0));
  }
}
