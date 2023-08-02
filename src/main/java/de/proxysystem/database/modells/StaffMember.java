package de.proxysystem.database.modells;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StaffMember {

  private UUID uuid;
  private boolean teamChatState;

}
