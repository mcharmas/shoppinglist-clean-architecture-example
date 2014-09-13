package pl.charmas.shoppinglist.data.entity.mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.data.entity.ProductEntity;
import pl.charmas.shoppinglist.domain.entities.Product;

public class ProductEntityMapper {

    @Inject
    public ProductEntityMapper() {
    }

    public Product toProduct(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.isBought());
    }

    public ProductEntity toEntity(Product productToUpdate) {
        return new ProductEntity(productToUpdate.getId(), productToUpdate.getName(), productToUpdate.isBought());
    }

    public List<Product> toProduct(List<ProductEntity> entities) {
        ArrayList<Product> products = new ArrayList<>(entities.size());
        for (ProductEntity entity : entities) {
            products.add(toProduct(entity));
        }
        return products;
    }
}
