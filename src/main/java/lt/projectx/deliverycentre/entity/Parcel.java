package lt.projectx.deliverycentre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long trackingNumber;
    private double weight;
    private String address;
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;
    @ManyToOne
    private Courier courier;


}
