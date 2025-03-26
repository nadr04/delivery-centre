package lt.projectx.deliverycentre.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.entity.ParcelStatus;
import lt.projectx.deliverycentre.repository.CourierRepository;
import lt.projectx.deliverycentre.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final CourierService courierService;

    public Parcel addParcel(Parcel parcel) {
        return parcelRepository.saveAndFlush(parcel);
    }

    public void addTestParcels() {
        Courier courier = courierService.addTestCourier();

        for (int i = 1; i <= 10; i++) {
            Parcel parcel = new Parcel();
            parcel.setTrackingNumber(100000L + i);
            parcel.setWeight(1.0 + i);
            parcel.setAddress("Test Address " + i);
            parcel.setStatus(i % 3 == 0 ? ParcelStatus.DELIVERED : (i % 2 == 0 ? ParcelStatus.IN_TRANSIT : ParcelStatus.PENDING));
            parcel.setCourier(courier);

            parcelRepository.save(parcel);
        }
    }
    public void printAllParcels() {
        this.getAllParcels().forEach(System.out::println);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
    public List<Parcel> findAllByTrackingNumber(Long trackingNumber) {
        return parcelRepository.findAllByTrackingNumberContainingIgnoreCase(trackingNumber);
    }
}
