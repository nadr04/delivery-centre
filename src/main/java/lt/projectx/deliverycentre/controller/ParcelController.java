package lt.projectx.deliverycentre.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.entity.ParcelStatus;
import lt.projectx.deliverycentre.service.ParcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping
    public ResponseEntity<Parcel> addParcel(@RequestBody Parcel parcel) {
        Parcel createdParcel = parcelService.addParcel(parcel);
        return ResponseEntity.ok(createdParcel);
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels() {
        List<Parcel> parcels = parcelService.getAllParcels();
        return ResponseEntity.ok(parcels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getParcelById(@PathVariable Integer id) {
        try {
            Parcel parcel = parcelService.findParcelById(id);
            return ResponseEntity.ok(parcel);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Parcel> updateParcelStatus(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String statusString = request.get("status");
        if (statusString == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ParcelStatus newStatus = ParcelStatus.valueOf(statusString.toUpperCase());
            Parcel updatedParcel = parcelService.patchParcelStatusById(id, newStatus);
            return ResponseEntity.ok(updatedParcel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelById(@PathVariable Integer id) {
        try {
            parcelService.deleteParcelById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}