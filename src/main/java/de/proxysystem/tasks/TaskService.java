package de.proxysystem.tasks;

import de.proxysystem.ProxySystem;
import de.proxysystem.tasks.abstraction.RepeatableTask;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;

public class TaskService {

  private final static LinkedList<RepeatableTask> tasks = new LinkedList<>();

  public void registerTask(RepeatableTask task) {
    tasks.add(task);
  }

  public void startAllTasks() {
    tasks.forEach(repeatableTask -> {
      ProxyServer.getInstance().getScheduler()
          .schedule(ProxySystem.getInstance(), repeatableTask, repeatableTask.getInitDelay(),
              repeatableTask.getDelay(), TimeUnit.SECONDS);
    });
  }

}
