package com.adrzdv.mtocp.ui.viewmodel;

import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.PASSENGER_TRAIN;
import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.TICKET_OFFICE;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;
import com.adrzdv.mtocp.domain.model.order.CollectableOrder;
import com.adrzdv.mtocp.domain.model.order.Order;
import com.adrzdv.mtocp.domain.model.order.OrderFactory;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;
import com.adrzdv.mtocp.domain.repository.TrainRepository;

import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;

public class OrderViewModel extends ViewModel {

    private final MutableLiveData<Order> order = new MutableLiveData<>();
    private final MutableLiveData<String> trainScheme = new MutableLiveData<>();
    private OrdersTypes selectedType;
    private final TrainRepository trainRepository;
    //private final TicketOfficeRepository ticketOfficeRepository;
    //private final CompanyRepository companyRepository
    private String orderNumber;
    private String objectNumber;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String route;
    private ExecutorService executor;

    public OrderViewModel(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
        executor = Executors.newSingleThreadExecutor();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getRoute() {
        return route;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public OrdersTypes getSelectedType() {
        return selectedType;
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

    public void setRoute(String route) {
        this.route = route;
    }

    public LiveData<String> getTrainScheme() {
        return trainScheme;
    }

    public void createOrder() {

        if (selectedType != null && !objectNumber.isBlank()) {
            Order newOrder = OrderFactory.createOrder(selectedType,
                    orderNumber,
                    dateStart,
                    dateEnd,
                    route);

            if (newOrder instanceof CollectableOrder collectableOrder) {
                ObjectCollector collector = createCollector(objectNumber);
                if (collector != null) {
                    collectableOrder.setCollector(collector);
                }
            }
            order.setValue(newOrder);
        }
    }

    public void clearRevisionObjects() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.clearRevisionObjects();
            order.setValue(currOrder);
        }
    }

    public void clearCrew() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.clearCrewWorkers();
            order.setValue(currOrder);
        }
    }

    public void deleteCrewWorker(WorkerDomain worker) {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.deleteCrewWorker(worker);
            order.setValue(currOrder);
        }
    }

    public void deleteRevisionObject(RevisionObject o) {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.deleteRevisionObject(o);
            order.setValue(currOrder);
        }
    }

    public void addRevisionObject(RevisionObject revisionObject) {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.addRevisionObject(revisionObject);
            order.setValue(currOrder);
        }
    }

    public void addCrewWorker(WorkerDomain workerDomain) {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.addCreWorker(workerDomain);
            order.setValue(currOrder);
        }
    }

    public void addQualityPassport(boolean isQualityPassport) {
        Order currOrder = order.getValue();

        if (currOrder != null) {
            if (currOrder instanceof CollectableOrder that) {
                that.setIsQualityPassport(isQualityPassport);
                order.setValue(currOrder);
            }
        }
    }

    public boolean checkCrew() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            return currOrder.checkCrew();
        }
        return false;
    }

    public @Nullable ObjectCollector getCollector() {
        Order currOrder = order.getValue();
        if (currOrder instanceof CollectableOrder that) {
            return that.getCollector();
        }
        return null;
    }

    public void updateTrainScheme() {

        Order currOrder = order.getValue();

        if (currOrder instanceof CollectableOrder that) {
            if (!(that.getCollector() instanceof TrainDomain train)) return;

            StringBuilder builder = new StringBuilder();
            builder.append("ВСЕГО: ")
                    .append(train.countObjects())
                    .append(" (");

            for (PassengerCoachType type : PassengerCoachType.values()) {
                int count = train.countPassCoachType(type);
                if (count > 0) {
                    builder.append(type.getPassengerCoachTitle()).append("-").append(count).append(" ");
                }
            }

            builder.append(")");
            trainScheme.setValue(builder.toString().trim());
        }
    }

    private ObjectCollector createCollector(String objectNumber) {

        if (selectedType.equals(PASSENGER_TRAIN)) {
            Future<TrainDomain> future = executor.submit(
                    () -> {
                        int index = objectNumber.indexOf(" ");

                        String number = objectNumber.substring(0, index);
                        return trainRepository.getTrain(number);
                    }
            );

            try {
                TrainDomain train = future.get();
                if (train != null) {
                    return train;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else if (selectedType.equals(TICKET_OFFICE)) {
            return null;
        }
        return null;
    }
}
