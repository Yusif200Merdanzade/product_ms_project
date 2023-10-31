package az.company.productms.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product", schema = "payment_system")
@Where(clause = "status <> '0'")
@SQLDelete(sql = "UPDATE payment_system.product SET status='0', update_date=CURRENT_TIMESTAMP WHERE id = ?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    int stock;

    double price;

    Character status = '1';

    @Column(name = "update_date")
    LocalDateTime updateDate;

}
