package pl.charmas.shoppinglist.presentation.base;

import java.util.LinkedList;
import java.util.Queue;

public abstract class BasePresenter<T extends UI> implements Presenter<T> {
  private Queue<UICommand<T>> commandQueue = new LinkedList<>();
  private UICommand<T> commandToRedeliver = null;
  private T ui;
  private boolean uiAttachedBefore = false;

  @Override public void attachUI(T ui) {
    this.ui = ui;
    executeCommandQueue(true);
    if (!this.uiAttachedBefore) {
      onFirstUIAttachment();
      this.uiAttachedBefore = true;
    }
  }

  protected abstract void onFirstUIAttachment();

  @Override public void detachUI() {
    this.ui = null;
  }

  protected void execute(UICommand<T> command, boolean redeliverOnAttachment) {
    commandQueue.add(command);
    executeCommandQueue(false);
    if (redeliverOnAttachment) {
      commandToRedeliver = command;
    }
  }

  private void executeCommandQueue(boolean attachment) {
    if (this.ui != null) {
      boolean commandToRedeliverExecuted = false;
      UICommand<T> command;
      while ((command = commandQueue.poll()) != null) {
        command.execute(this.ui);
        if (command == commandToRedeliver) {
          commandToRedeliverExecuted = true;
        }
      }
      if (attachment && !commandToRedeliverExecuted && commandToRedeliver != null) {
        commandToRedeliver.execute(ui);
      }

    }
  }

  public interface UICommand<T> {
    void execute(T ui);
  }
}
