package az.company.productms.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

     @JsonProperty(access = JsonProperty.Access.READ_ONLY)
     Long id;

     String name;

     int stock;

     double price;

     @JsonProperty(access = JsonProperty.Access.READ_ONLY)
     Character status;

     @JsonProperty(access = JsonProperty.Access.READ_ONLY)
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
     LocalDateTime update_date;
}
