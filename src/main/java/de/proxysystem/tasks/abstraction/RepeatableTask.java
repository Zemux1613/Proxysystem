package de.proxysystem.tasks.abstraction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class RepeatableTask implements Runnable {

  private final long delay;
  private final long initDelay;

}
