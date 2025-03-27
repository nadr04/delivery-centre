package lt.projectx.deliverycentre.repository;

import lt.projectx.deliverycentre.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
    List<Parcel> findAllByTrackingNumberContainingIgnoreCase(Long TrackingNumber);

    List<Parcel> id(int id);
    List<Parcel> findAllByCourierId(Integer courierId);
}
