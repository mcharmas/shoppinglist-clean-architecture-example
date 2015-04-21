package pl.charmas.shoppinglist.domain.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductList implements Iterable<Product> {
  private final List<Product> products;
  private long lastId;

  public ProductList(List<Product> products) {
    this.products = products == null ? new ArrayList<Product>() : new ArrayList<>(products);
    initializeLastId();
  }

  private void initializeLastId() {
    this.lastId = 0;
    for (Product product : this.products) {
      this.lastId = Math.max(product.getId(), this.lastId);
    }
  }

  public Product addProduct(String productName) {
    Product product = new Product(++lastId, productName, false);
    products.add(product);
    return product;
  }

  public int removeBoughtProducts() {
    Iterator<Product> iterator = products.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      if (iterator.next().isBought()) {
        iterator.remove();
        count++;
      }
    }
    return count;
  }

  public Product getProduct(long id) {
    for (Product product : products) {
      if (product.getId() == id) {
        return product;
      }
    }
    return null;
  }

  @Override public Iterator<Product> iterator() {
    return products.iterator();
  }

  public int size() {
    return products.size();
  }
}
