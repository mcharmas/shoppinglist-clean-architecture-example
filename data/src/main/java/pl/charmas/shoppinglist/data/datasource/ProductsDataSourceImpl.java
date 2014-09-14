package pl.charmas.shoppinglist.data.datasource;


import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.data.entity.ProductEntity;
import pl.charmas.shoppinglist.data.entity.mappers.ProductEntityMapper;
import pl.charmas.shoppinglist.data.store.ProductEntityStore;
import pl.charmas.shoppinglist.domain.datasource.ProductsDataSource;
import pl.charmas.shoppinglist.domain.entities.Product;

public class ProductsDataSourceImpl implements ProductsDataSource {
    private final ProductEntityStore productEntityStore;
    private final ProductEntityMapper mapper;

    @Inject
    public ProductsDataSourceImpl(ProductEntityStore productEntityStore, ProductEntityMapper mapper) {
        this.productEntityStore = productEntityStore;
        this.mapper = mapper;
    }

    @Override
    public Product createProduct(String name, boolean isBought) {
        ProductEntity productEntity = productEntityStore.createProductEntity(name, isBought);
        return mapper.toProduct(productEntity);
    }

    @Override
    public int removeProduct(long id) {
        return productEntityStore.removeProductEntity(id);
    }

    @Override
    public Product updateProduct(Product productToUpdate) {
        ProductEntity entity = mapper.toEntity(productToUpdate);
        ProductEntity updatedEntity = productEntityStore.updateProductEntity(entity);
        return mapper.toProduct(updatedEntity);
    }

    @Override
    public List<Product> listAll() {
        return mapper.toProduct(productEntityStore.listAll());
    }

    @Override
    public Product getProduct(long id) {
        return mapper.toProduct(productEntityStore.getProduct(id));
    }
}
