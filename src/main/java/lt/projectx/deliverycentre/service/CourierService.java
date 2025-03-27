package lt.projectx.deliverycentre.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.entity.ParcelStatus;
import lt.projectx.deliverycentre.repository.CourierRepository;
import lt.projectx.deliverycentre.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final ParcelRepository parcelRepository;

    public Courier addCourier(Courier courier) {
        return courierRepository.saveAndFlush(courier);
    }

    public Courier addTestCourier() {
        Courier courier = new Courier();
        courier.setName("John");
        courier.setLastName("Doe");
        courier.setPersonalCode(123456789L);
        courier.setVehicleNumber("XYZ-1234");
        return courierRepository.save(courier);
    }

    public void printAllCouriers() {
        this.getAllCouriers().forEach(System.out::println);
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public List<Courier> findAllByPersonalCode(Long personalCode) {
        return courierRepository.findAllByPersonalCodeContainingIgnoreCase(personalCode);
    }
    public Courier findCourierById(Integer id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courier with id " + id + " not found"));
    }
    public List<Parcel> getParcelsByCourierId(Integer courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new EntityNotFoundException("Courier with id " + courierId + " not found"));

        return parcelRepository.findAllByCourierId(courierId);
    }
    public Courier patchCourierById(Integer id, Courier courierFromRequest) {
        Optional<Courier> maybeCourierFromDb = courierRepository.findById(id);
        if (maybeCourierFromDb.isEmpty()) {
            throw new EntityNotFoundException("Courier with id " + id + " not found");
        }
        Courier courierFromDb = maybeCourierFromDb.get();

        if (courierFromRequest.getPersonalCode() != null  &&
                !courierFromRequest.getPersonalCode().equals(courierFromDb.getPersonalCode())) {
            courierFromDb.setPersonalCode(courierFromRequest.getPersonalCode());
        }
        if (StringUtils.isNotBlank(courierFromRequest.getName()) &&
                !courierFromRequest.getName().equals(courierFromDb.getName())) {
            courierFromDb.setName(courierFromRequest.getName());
        }
        if (StringUtils.isNotBlank(courierFromRequest.getLastName()) &&
                !courierFromRequest.getLastName().equals(courierFromDb.getLastName())) {
            courierFromDb.setLastName(courierFromRequest.getLastName());
        }
        if (StringUtils.isNotBlank(courierFromRequest.getVehicleNumber()) &&
                !courierFromRequest.getVehicleNumber().equals(courierFromDb.getVehicleNumber())) {
            courierFromDb.setVehicleNumber(courierFromRequest.getVehicleNumber());
        }

        return courierRepository.saveAndFlush(courierFromDb);
    }

}


