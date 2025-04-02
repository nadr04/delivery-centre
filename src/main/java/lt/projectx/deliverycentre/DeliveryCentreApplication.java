package lt.projectx.deliverycentre;

import lombok.RequiredArgsConstructor;
import lt.projectx.deliverycentre.entity.Parcel;
import lt.projectx.deliverycentre.service.CourierService;
import lt.projectx.deliverycentre.service.ParcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DeliveryCentreApplication {
    private final ParcelService parcelService;
    private final CourierService courierService;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryCentreApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        parcelService.addTestParcels();
        courierService.addTestCouriers();

    }
}
