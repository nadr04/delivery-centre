package lt.projectx.deliverycentre.service;

import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;

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
}


