package pl.charmas.shoppinglist.data.store;

import java.util.List;

import pl.charmas.shoppinglist.data.entity.ProductEntity;

public interface ProductEntityStore {
    ProductEntity createProductEntity(String name, boolean isBought);

    int removeProductEntity(long id);

    ProductEntity updateProductEntity(ProductEntity productToUpdate);

    List<ProductEntity> listAll();

    ProductEntity getProduct(long id);
}
