package lt.projectx.deliverycentre.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.entity.ParcelStatus;
import lt.projectx.deliverycentre.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Parcel findParcelById(Integer id) {
        return parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcel with id " + id + " not found"));
    }

    public Parcel patchParcelStatusById(Integer id, ParcelStatus newStatus) {
        Optional<Parcel> maybeParcelFromDb = parcelRepository.findById(id);
        if (maybeParcelFromDb.isEmpty()) {
            throw new EntityNotFoundException("Parcel with id " + id + " not found");
        }

        Parcel parcelFromDb = maybeParcelFromDb.get();

        if (newStatus != null && !newStatus.equals(parcelFromDb.getStatus())) {
            parcelFromDb.setStatus(newStatus);
        }

        return parcelRepository.saveAndFlush(parcelFromDb);
    }
    public void deleteParcelById(Integer id) {
        Optional<Parcel> maybeParcelFromDb = parcelRepository.findById(id);
        if (maybeParcelFromDb.isEmpty()) {
            throw new EntityNotFoundException("Parcel with id " + id + " not found");
        }
        parcelRepository.deleteById(id);
    }


}
