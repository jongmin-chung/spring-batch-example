package io.github.jongminchung.springbatchexample.domain.entity;

import io.github.jongminchung.springbatchexample.domain.enums.SellType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private long id = 0L;

    @Column(name = "seller_name")
    @Comment("""
            셀러명
            """)
    private String name;

    @Column(name = "business_no")
    @Comment("셀러의 사업자번호")
    private int businessNo;

    @Column(name = "sell_type")
    @Enumerated(EnumType.STRING)
    @Comment("""
            셀러의 판매 유형
            - ONLINE: 온라인 판매
            - OFFLINE: 오프라인 판매
            """)
    private SellType sellType = SellType.CONSIGNMENT;

    @Embedded
    @Comment("셀러의 계좌 정보")
    private Account account;

    @Column(name = "is_active")
    @Comment("셀러의 활성화 여부")
    private boolean isActive = true;

    @Column(name = "default_delivery_fee")
    @Comment("기본 배송 금액")
    @Builder.Default
    private int defaultDeliveryFee = 3000;

    @Column(name = "commission")
    @Comment("수수료")
    @Builder.Default
    private int commission = 5;

    @Getter
    @Embeddable
    public static class Account {

        @Column(name = "account_no")
        @Comment("계좌번호")
        private String accountNo;


        @Column(name = "account_owner_name")
        @Comment("계좌주")
        private String name;
    }
}
