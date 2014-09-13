package pl.charmas.shoppinglist.model.mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.shoppinglist.domain.entities.Product;
import pl.charmas.shoppinglist.model.ProductViewModel;

public class ProductToViewModelMapper {
    @Inject
    public ProductToViewModelMapper() {
    }

    public List<ProductViewModel> toViewModel(List<Product> products) {
        ArrayList<ProductViewModel> viewModels = new ArrayList<>(products.size());
        for (Product product : products) {
            viewModels.add(toViewModel(product));
        }
        return viewModels;
    }

    private ProductViewModel toViewModel(Product product) {
        return new ProductViewModel(product.getId(), product.getName(), product.isBought());
    }
}
