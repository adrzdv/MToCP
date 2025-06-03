package com.adrzdv.mtocp.ui.viewmodel;

import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.PASSENGER_TRAIN;
import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.TICKET_OFFICE;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;
import com.adrzdv.mtocp.domain.model.order.CollectableOrder;
import com.adrzdv.mtocp.domain.model.order.Order;
import com.adrzdv.mtocp.domain.model.order.OrderFactory;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.repository.TrainRepository;

import java.time.LocalDateTime;

public class OrderViewModel extends ViewModel {

    private final MutableLiveData<Order> order = new MutableLiveData<>();
    private OrdersTypes selectedType;
    private final TrainRepository trainRepository;
    //private final TicketOfficeRepository ticketOfficeRepository;
    //private final CompanyRepository companyRepository
    private String orderNumber;
    private String objectNumber;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    public OrderViewModel(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public void setSelectedType(OrdersTypes selectedType) {
        this.selectedType = selectedType;
    }

    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void createOrder() {

        if (selectedType != null && !objectNumber.isBlank()) {
            Order newOrder = OrderFactory.createOrder(selectedType,
                    orderNumber,
                    dateStart,
                    dateEnd);

            if (newOrder instanceof CollectableOrder collectableOrder) {
                ObjectCollector collector = createCollector(objectNumber);
                if(collector != null) {
                    collectableOrder.setCollector(collector);
                }
            }

            order.setValue(newOrder);

        }
    }

    public ObjectCollector createCollector(String objectNumber) {

        if (selectedType.equals(PASSENGER_TRAIN)) {

            return trainRepository.getTrain(objectNumber);

        } else if (selectedType.equals(TICKET_OFFICE)) {
            return null;
        }

        return null;
    }
}
