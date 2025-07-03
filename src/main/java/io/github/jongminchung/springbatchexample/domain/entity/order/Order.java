package io.github.jongminchung.springbatchexample.domain.entity.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`") // DB 예약어 처리
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id = 0L;

    @Comment("구매 확정일시")
    private LocalDateTime paidConfirmedAt = null;

    @Column(precision = 16, scale = 5)
    private BigDecimal paidPgAmount = BigDecimal.ZERO;
    @Column(precision = 16, scale = 5)
    private BigDecimal usedMileageAmount = BigDecimal.ZERO;
    @Column(precision = 16, scale = 5)
    private BigDecimal couponDiscountAmount = BigDecimal.ZERO;

    /// 주문자 정보는 X
}
