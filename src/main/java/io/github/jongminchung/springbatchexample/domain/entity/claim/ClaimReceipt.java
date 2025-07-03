package io.github.jongminchung.springbatchexample.domain.entity.claim;

import io.github.jongminchung.springbatchexample.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClaimReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_receipt_id")
    private Long id;

    private long orderId;

    private LocalDateTime completeAt;

    @Convert(converter = ClaimTypeConverter.class)
    private ClaimType claimType;

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    private ExtraFeePayer extraFeePayer;
    private int claimReason;
}

