package pl.charmas.shoppinglist.domain.entities;

public class Product {
  private final long id;
  private final String name;
  private boolean isBought;

  public Product(long id, String name, boolean isBought) {
    this.id = id;
    this.name = name;
    this.isBought = isBought;
  }

  public long getId() {
    return id;
  }

  public void markBought() {
    this.isBought = true;
  }

  public void markNotBought() {
    this.isBought = false;
  }

  public String getName() {
    return name;
  }

  public boolean isBought() {
    return isBought;
  }
}
