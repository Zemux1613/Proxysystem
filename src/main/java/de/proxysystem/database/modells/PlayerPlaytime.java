package de.proxysystem.database.modells;

import java.util.Optional;
import lombok.Data;

@Data
public class PlayerPlaytime {

  private final Optional<NameResult> nameResult;
  private final long playtime;
}

