package lt.projectx.deliverycentre.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Courier;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.service.CourierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    public ResponseEntity<Courier> addCourier(@RequestBody Courier courier) {
        Courier createdCourier = courierService.addCourier(courier);
        return ResponseEntity.ok(createdCourier);
    }

    @GetMapping
    public ResponseEntity<List<Courier>> getAllCouriers() {
        List<Courier> couriers = courierService.getAllCouriers();
        return ResponseEntity.ok(couriers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courier> getCourierById(@PathVariable Integer id) {
        try {
            Courier courier = courierService.findCourierById(id);
            return ResponseEntity.ok(courier);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/parcels")
    public ResponseEntity<List<Parcel>> getParcelsByCourierId(@PathVariable Integer id) {
        try {
            List<Parcel> parcels = courierService.getParcelsByCourierId(id);
            return ResponseEntity.ok(parcels);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Courier> updateCourier(@PathVariable Integer id, @RequestBody Courier courier) {
        try {
            Courier updatedCourier = courierService.patchCourierById(id, courier);
            return ResponseEntity.ok(updatedCourier);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}