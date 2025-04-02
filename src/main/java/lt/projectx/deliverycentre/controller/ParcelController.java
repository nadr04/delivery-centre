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
        return ResponseEntity.ok(parcelService.addParcel(parcel));
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels() {
        return ResponseEntity.ok(parcelService.getAllParcels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getParcelById(@PathVariable Integer id) {
        return ResponseEntity.ok(parcelService.findParcelById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Parcel> updateParcelStatus(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String statusString = request.get("status");
        if (statusString == null) {
            return ResponseEntity.badRequest().body(null);
        }

        ParcelStatus newStatus;
        try {
            newStatus = ParcelStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(parcelService.patchParcelStatusById(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcelById(@PathVariable Integer id) {
        parcelService.deleteParcelById(id);
        return ResponseEntity.noContent().build();
    }
}