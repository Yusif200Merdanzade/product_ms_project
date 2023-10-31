package az.company.productms.service;

import az.company.productms.config.CustomAuthorization;
import az.company.productms.entity.Product;
import az.company.productms.error.ErrorsFinal;
import az.company.productms.exception.ApplicationException;
import az.company.productms.mapper.ProductMapper;
import az.company.productms.model.ProductDto;
import az.company.productms.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final CustomAuthorization customAuthorization;

    @Override
    public ProductDto getProductById(Long id) throws JsonProcessingException {
        customAuthorization.isValid();
        Product product = productRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorsFinal.DATA_NOT_FOUND, Map.of("id", id)));
        return productMapper.entityToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto cardDto) {
        Product card = productMapper.dtoToEntity(cardDto);
        productRepository.save(card);
        return productMapper.entityToDto(card);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorsFinal.DATA_NOT_FOUND, Map.of("id", id)));
        productMapper.updateProduct(product, dto);
        product.setUpdateDate(LocalDateTime.now());
        productRepository.save(product);
        return productMapper.entityToDto(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorsFinal.DATA_NOT_FOUND, Map.of("id", id)));
        productRepository.delete(product);
    }


}
