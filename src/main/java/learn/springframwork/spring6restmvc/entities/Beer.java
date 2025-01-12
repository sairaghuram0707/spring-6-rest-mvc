package learn.springframwork.spring6restmvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import learn.springframwork.spring6restmvc.model.BeerStyle;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36,columnDefinition = "varchar", nullable = false, updatable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50)  // Size Annotation validates before persisting the Data.
    @Column(length = 50)
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
