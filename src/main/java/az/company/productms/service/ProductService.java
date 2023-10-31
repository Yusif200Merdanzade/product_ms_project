package az.company.productms.service;

import az.company.productms.model.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {

    ProductDto getProductById(Long id) throws JsonProcessingException;

    List<ProductDto> getAllProducts();

    ProductDto save(ProductDto cardDto);

    ProductDto update(Long id,ProductDto dto);

    void delete(Long id);


}
