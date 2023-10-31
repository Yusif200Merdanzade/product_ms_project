package az.company.productms.mapper;


import az.company.productms.entity.Product;
import az.company.productms.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

     ProductDto entityToDto(Product product);

     Product dtoToEntity(ProductDto productDto);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "name", ignore = true)
     @Mapping(target = "status", ignore = true)
     @Mapping(target = "updateDate", ignore = true)
     void updateProduct(@MappingTarget Product product,ProductDto dto);

}
