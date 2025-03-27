package lt.projectx.deliverycentre.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long personalCode;
    private String name;
    private String lastName;
    private String vehicleNumber;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Parcel> parcels;
}
