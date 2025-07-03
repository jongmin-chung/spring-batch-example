package io.github.jongminchung.springbatchexample.domain.entity.order;

import io.github.jongminchung.springbatchexample.domain.entity.Product;
import io.github.jongminchung.springbatchexample.domain.entity.Seller;
import io.github.jongminchung.springbatchexample.domain.enums.TaxType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_snapshot_id")
    private long id = 0L;

    private BigDecimal sellPrice = BigDecimal.ZERO;
    private BigDecimal supplyPrice = BigDecimal.ZERO;
    private BigDecimal promotionAmount = BigDecimal.ZERO;
    private BigDecimal defaultDeliveryAmount = BigDecimal.valueOf(3000);
    private BigDecimal mileageUsageAmount = BigDecimal.ZERO;

    /// TODO: Enum으로 변경
    private int itemCategory;
    private int taxRate = 3;

    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "seller_id", insertable = false, updatable = false)
    private long sellerId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name ="product_id", insertable = false, updatable = false)
    private long productId;
}
