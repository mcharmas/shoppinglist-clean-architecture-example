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
    if (!this.uiAttachedBefore) {
      onFirstUIAttachment();
      this.uiAttachedBefore = true;
    }
    executeCommandQueue();
    if (commandToRedeliver != null) {
      commandToRedeliver.execute(ui);
    }
  }

  protected abstract void onFirstUIAttachment();

  @Override public void detachUI() {
    this.ui = null;
  }

  protected void execute(UICommand<T> command, boolean redeliverOnAttachment) {
    commandQueue.add(command);
    executeCommandQueue();
    if (redeliverOnAttachment) {
      commandToRedeliver = command;
    }
  }

  private void executeCommandQueue() {
    if (this.ui != null) {
      UICommand<T> command;
      while ((command = commandQueue.poll()) != null) {
        command.execute(this.ui);
      }
    }
  }

  public interface UICommand<T> {
    void execute(T ui);
  }
}
