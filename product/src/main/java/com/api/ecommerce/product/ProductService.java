package com.api.ecommerce.product;

import com.api.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;


    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }




    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {

        //do products exist in a database? --
        var productIds = request // if user request products 1 2 3
                .stream()
                .map(ProductPurchaseRequest::productId).toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds); // and we have in database products 1 2 only

        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("one or more products does not exist");
        }
        //--


        //extarct products ids
        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();


        //quantity check for products
        var purchasedProductsResponse = new ArrayList<ProductPurchaseResponse>();
        var purchasedProducts = new ArrayList<Product>();
        for (int i = 0; i < productIds.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity())
                throw new ProductPurchaseException("not enough quantity of product " + product.getId());
            product.setAvailableQuantity(product.getAvailableQuantity() - productRequest.quantity());
            purchasedProducts.add(product);
            purchasedProductsResponse.add(mapper.toProductPurchasedResponse(product,productRequest.quantity()));
        }

        //reducing quantity in database
        repository.saveAll(purchasedProducts);

        return purchasedProductsResponse;
    }

    public ProductResponse findById(Integer productId) {
     return  repository.findById(productId).map(mapper::toProductResponse).orElseThrow(() -> new EntityNotFoundException());
    }
    public List<ProductResponse> findAll() {
        return  repository.findAll().stream().map(mapper::toProductResponse).toList();
    }
}
