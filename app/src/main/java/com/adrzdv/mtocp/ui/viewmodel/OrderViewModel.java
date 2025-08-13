package com.adrzdv.mtocp.ui.viewmodel;

import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.PASSENGER_TRAIN;
import static com.adrzdv.mtocp.domain.model.enums.OrdersTypes.TICKET_OFFICE;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.model.order.BaggageOrder;
import com.adrzdv.mtocp.domain.model.order.CollectableOrder;
import com.adrzdv.mtocp.domain.model.order.Order;
import com.adrzdv.mtocp.domain.model.order.OrderFactory;
import com.adrzdv.mtocp.domain.model.order.TrainOrder;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTerminal;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;
import com.adrzdv.mtocp.domain.repository.TrainRepository;
import com.adrzdv.mtocp.domain.usecase.ArchivePhotoInZipUseCase;
import com.adrzdv.mtocp.domain.usecase.CheckUncheckedObjectsUseCase;
import com.adrzdv.mtocp.domain.usecase.GetGlobalViolationStringUseCase;
import com.adrzdv.mtocp.domain.usecase.GetObjectMapSortedWithViolationsUseCase;
import com.adrzdv.mtocp.domain.usecase.MakeAdditionalParamsUseCase;
import com.adrzdv.mtocp.domain.usecase.stats.CountMainAutodoorsUseCase;
import com.adrzdv.mtocp.domain.usecase.stats.CountMainCarsUseCase;
import com.adrzdv.mtocp.domain.usecase.stats.CountTrailingAutodoorsUseCase;
import com.adrzdv.mtocp.domain.usecase.stats.CountTrailingCarsUseCase;
import com.adrzdv.mtocp.domain.usecase.stats.GenerateSmsReportUseCase;
import com.adrzdv.mtocp.util.gson.LocalDateTimeAdapter;
import com.adrzdv.mtocp.util.gson.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jspecify.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class OrderViewModel extends ViewModel {

    private final MutableLiveData<Order> order = new MutableLiveData<>();
    private final MutableLiveData<String> trainScheme = new MutableLiveData<>();
    private final MutableLiveData<String> textReportSms = new MutableLiveData<>();
    private final ArchivePhotoInZipUseCase archivePhotoInZipUseCase;
    private final GetGlobalViolationStringUseCase getGlobalViolationStringUseCase;
    private final CountMainCarsUseCase countMainCarsUseCase;
    private final CountTrailingCarsUseCase countTrailingCarsUseCase;
    private final GenerateSmsReportUseCase generateSmsReportUseCase;
    private final GetObjectMapSortedWithViolationsUseCase getObjectMapSortedWithViolationsUseCase;
    private final CheckUncheckedObjectsUseCase checkUncheckedObjectsUseCase;
    private final MakeAdditionalParamsUseCase makeAdditionalParamsUseCase;
    private final CountMainAutodoorsUseCase countMainAutodoorsUseCase;
    private final CountTrailingAutodoorsUseCase countTrailingAutodoorsUseCase;
    private OrdersTypes selectedType;
    private final TrainRepository trainRepository;
    //private final TicketOfficeRepository ticketOfficeRepository;
    //private final CompanyRepository companyRepository;
    private String orderNumber;
    private String objectNumber;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String route;
    private ExecutorService executor;

    public OrderViewModel(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
        this.archivePhotoInZipUseCase = new ArchivePhotoInZipUseCase();
        this.getGlobalViolationStringUseCase = new GetGlobalViolationStringUseCase();
        this.countMainCarsUseCase = new CountMainCarsUseCase();
        this.countTrailingCarsUseCase = new CountTrailingCarsUseCase();
        this.generateSmsReportUseCase = new GenerateSmsReportUseCase();
        this.getObjectMapSortedWithViolationsUseCase = new GetObjectMapSortedWithViolationsUseCase();
        this.checkUncheckedObjectsUseCase = new CheckUncheckedObjectsUseCase();
        this.makeAdditionalParamsUseCase = new MakeAdditionalParamsUseCase();
        this.countMainAutodoorsUseCase = new CountMainAutodoorsUseCase();
        this.countTrailingAutodoorsUseCase = new CountTrailingAutodoorsUseCase();
        executor = Executors.newSingleThreadExecutor();
    }

    public RevisionType getRevisionType() {
        RevisionType revisionType = Objects.requireNonNull(order.getValue()).getRevisionType();
        return revisionType == null ? RevisionType.IN_TRANSIT : revisionType;
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

    public void updateRevisionObject(RevisionObject object) {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            currOrder.updateRevisionObject(object);
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
            currOrder.addCrewWorker(workerDomain);
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

    public void toggleDinnerCar(boolean flag) {
        Order currOrder = order.getValue();

        if (currOrder instanceof CollectableOrder that) {
            if (!(that.getCollector() instanceof TrainDomain train)) return;
            train.setDinnerCar(flag);
        }
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
            if (train.getIsDinnerCar()) {
                builder.append(" в т.ч. 1 ВР");
            }

            trainScheme.setValue(builder.toString().trim());
        }
    }

    public void removeDinnerCar() {
        Order currOrder = order.getValue();

        if (currOrder instanceof TrainOrder that) {
            that.removeDinnerCoach();
        }
    }

    public boolean isTrainHasCamera() {
        Order currOrder = order.getValue();

        if (currOrder instanceof TrainOrder that) {
            if (that.getCollector() instanceof TrainDomain train) {
                return train.getVideo();
            }
        }
        return false;
    }

    public boolean isTrainUsingProgressive() {
        Order currOrder = order.getValue();
        if (currOrder instanceof TrainOrder that) {
            if (that.getCollector() instanceof TrainDomain train) {
                return train.getProgressive();
            }
        }
        return false;
    }

    public void makeArchive(Consumer<File> callback) {
        executor.execute(() -> {
            File file = archivePhotoInZipUseCase.execute(orderNumber);
            if (file != null) {
                callback.accept(file);
            } else {
                callback.accept(null);
            }
        });
    }

    public List<String> getGlobalViolationResult() {
        Order currentOrder = order.getValue();
        if (currentOrder instanceof CollectableOrder that) {
            return getGlobalViolationStringUseCase.execute(that.getCollector().getObjectsMap());
        } else {
            return null;
        }
    }

    public int getMainCars() {
        Order currOrder = order.getValue();
        if (currOrder instanceof TrainOrder that) {
            return countMainCarsUseCase.execute(that.getCollector().getObjectsMap());
        }
        return 0;
    }

    public int getTrailingCars() {
        Order currOrder = order.getValue();
        if (currOrder instanceof TrainOrder that) {
            return countTrailingCarsUseCase.execute(that.getCollector().getObjectsMap());
        }
        return 0;
    }

    public int getTotalViolation() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            return currOrder.countViolations();
        }
        return 0;
    }

    public int getMainViolationCount() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            if (currOrder instanceof TrainOrder that) {
                return that.countViolationsMainCar();
            }
        } else {
            return 0;
        }

        return getTotalViolation();
    }

    public int getTrailingViolationCount() {
        Order currOrder = order.getValue();
        if (currOrder != null) {
            if (currOrder instanceof TrainOrder that) {
                return that.countViolationTrailingCar();
            }
        }
        return 0;
    }

    public String getObjectNumber() {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return null;
        }

        if (currOrder instanceof CollectableOrder that) {
            return that.getCollector().toString();
        }

        return null;
    }

    public String makeJsonFromRevObjects() {
        RuntimeTypeAdapterFactory<RevisionObject> adapterFactory = RuntimeTypeAdapterFactory
                .of(RevisionObject.class, "objectType")
                .registerSubtype(PassengerCar.class, "PassengerCar")
                .registerSubtype(DinnerCar.class, "DinnerCar")
                .registerSubtype(BaggageCar.class, "BaggageCar")
                .registerSubtype(TicketTerminal.class, "TicketTerminal");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        if (order.getValue() instanceof CollectableOrder that) {
            try {
                return gson.toJson(that.getCollector().getObjectsMap());
            } catch (NullPointerException e) {
                return "{}";
            }

        } else if (order.getValue() instanceof BaggageOrder that) {
            try {
                return gson.toJson(that.getCoachMap());
            } catch (NullPointerException e) {
                return "{}";
            }
        }

        return "{}";
    }

    public void updateRevObjectMapFromJson(String json) {
        RuntimeTypeAdapterFactory<RevisionObject> adapterFactory = RuntimeTypeAdapterFactory
                .of(RevisionObject.class, "objectType")
                .registerSubtype(PassengerCar.class, "PassengerCar")
                .registerSubtype(DinnerCar.class, "DinnerCar")
                .registerSubtype(BaggageCar.class, "BaggageCar")
                .registerSubtype(TicketTerminal.class, "TicketTerminal");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Type mapType = new TypeToken<Map<String, RevisionObject>>() {
        }.getType();
        Map<String, RevisionObject> deserializedMap = gson.fromJson(json, mapType);

        Order cuurOrder = order.getValue();
        if (cuurOrder == null) {
            return;
        }

        for (RevisionObject obj : deserializedMap.values()) {
            cuurOrder.updateRevisionObject(obj);
        }

        order.setValue(cuurOrder);
    }

    public void generateReport() throws ExecutionException, InterruptedException {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return;
        }
        if (currOrder instanceof CollectableOrder that) {
            Map<String, RevisionObject> objMap = that.getCollector().getObjectsMap();
            textReportSms.postValue(generateSmsReportUseCase.execute(objMap));
        }
    }

    public LiveData<String> getSmsReport() {
        return textReportSms;
    }

    public Map<String, WorkerDomain> getCrewMap() {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return null;
        }

        if (currOrder instanceof CollectableOrder that) {
            return that.getCrewMap();
        } else {
            return null;
        }
    }

    public Set<RevisionObject> getObjMapWithViolations() {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return null;
        }
        if (currOrder instanceof CollectableOrder that) {
            return getObjectMapSortedWithViolationsUseCase.execute(that.getCollector().getObjectsMap());
        } else {
            return null;
        }
    }

    public boolean checkUncheckedObjects() {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return false;
        }

        if (currOrder instanceof CollectableOrder that) {
            return checkUncheckedObjectsUseCase.execute(that.getCollector().getObjectsMap());
        }

        return false;
    }

    public Map<String, Map<String, Pair<String, String>>> getStatsParams() {
        Order currOrder = order.getValue();
        if (currOrder == null) {
            return null;
        }
        if (currOrder instanceof CollectableOrder that) {
            return makeAdditionalParamsUseCase.execute(that.getCollector().getObjectsMap());
        }

        return null;
    }

    public int countTrailingAutodoors() {
        Order currOrder = order.getValue();
        if (currOrder instanceof CollectableOrder that) {
            return countTrailingAutodoorsUseCase.execute(that.getCollector().getObjectsMap());
        }

        return 0;
    }

    public int countMainAutodoors() {
        Order currOrder = order.getValue();
        if (currOrder instanceof CollectableOrder that) {
            return countMainAutodoorsUseCase.execute(that.getCollector().getObjectsMap());
        }

        return 0;
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
