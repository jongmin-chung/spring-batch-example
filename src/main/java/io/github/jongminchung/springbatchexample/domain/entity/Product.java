package io.github.jongminchung.springbatchexample.domain.entity;

import io.github.jongminchung.springbatchexample.domain.enums.TaxType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id = 0L;

    @Column(name = "product_name")
    private String name;

    @Column(name = "seller_id")
    private long sellerId;

    /// @TODO: Enum으로 변경
    @Column(name = "category")
    private int category;

    @Column(name = "tax_type")
    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    @Column(name = "sell_price")
    @Comment("판매가")
    private BigDecimal sellPrice = BigDecimal.ZERO;

    @Column(name = "supply_price")
    @Comment("공급가")
    private BigDecimal supplyPrice = BigDecimal.ZERO;

    @Column(name = "is_active")
    @Comment("활성화 여부")
    private boolean isActive = true;
}
