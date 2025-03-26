package lt.projectx.deliverycentre.repository;

import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findAllByPersonalCodeContainingIgnoreCase(Long personalCode);
}