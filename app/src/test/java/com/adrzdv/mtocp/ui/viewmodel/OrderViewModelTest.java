package com.adrzdv.mtocp.ui.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import com.adrzdv.mtocp.domain.model.departments.BranchDomain;
import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;
import com.adrzdv.mtocp.domain.model.enums.RevisionObjectType;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.model.enums.WorkerTypes;
import com.adrzdv.mtocp.domain.model.order.BaggageOrder;
import com.adrzdv.mtocp.domain.model.order.CollectableOrder;
import com.adrzdv.mtocp.domain.model.order.Order;
import com.adrzdv.mtocp.domain.model.order.TrainOrder;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.TicketTerminal;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.BaggageCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.ObjectCollector;
import com.adrzdv.mtocp.domain.model.revisionobject.collectors.TrainDomain;
import com.adrzdv.mtocp.domain.model.violation.StaticsParam;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.OuterWorkerDomain;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;
import com.adrzdv.mtocp.domain.repository.TrainRepository;
import com.adrzdv.mtocp.util.gson.CustomGson;
import com.adrzdv.mtocp.util.gson.LocalDateAdapter;
import com.adrzdv.mtocp.util.gson.LocalDateTimeAdapter;
import com.adrzdv.mtocp.util.gson.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class OrderViewModelTest {

    private static PassengerCar car;
    private static PassengerCar newcar;
    private static DinnerCar dinnerCar;
    private static TrainOrder order;
    private static TrainOrder otherTrainOrder;

    @Test
    public void shouldSerializeAndDeserializeObject() {
        car = new PassengerCar("000-12345");
        dinnerCar = new DinnerCar("001-66666");
        BranchDomain branch = new BranchDomain(1, "Branch", "Br");
        DepotDomain depot = new DepotDomain(1,
                "Depot", "dep", "70000000000", branch,
                true, false);
        CompanyDomain company = new CompanyDomain(123456L, "DinnerCo",
                true, "123", LocalDate.now(),
                branch, true);
        ObjectCollector train = new TrainDomain("001", "Route", depot,
                true, true, true);
        ObjectCollector otherTrain = new TrainDomain("001", "Route", depot,
                true, true, true);
        WorkerDomain worker = new InnerWorkerDomain(1, "In Worker", depot, WorkerTypes.CONDUCTOR);
        WorkerDomain outWorker = new OuterWorkerDomain(1, "Out Worker", company, WorkerTypes.OUTSOURCE);
        order = new TrainOrder("001", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2L), "Order Route");
        otherTrainOrder = new TrainOrder("001", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2L), "Order Route");
        ViolationDomain violation1 = new ViolationDomain(1, "Violation", "Viol");
        ViolationDomain violation2 = new ViolationDomain(2, "ViolationTwo", "Viol2");
        StaticsParam statParam = new StaticsParam(1, "Additional param", true, "Note");

        car.setCoachType(PassengerCoachType.COMPARTMENT);
        car.setDepotDomain(depot);
        car.setWorker(worker);
        car.setRevisionDateStart(LocalDateTime.now());
        car.setRevisionDateEnd(LocalDateTime.now().plusHours(2L));
        Map<Integer, ViolationDomain> violationMap1 = new HashMap<>();
        violationMap1.put(violation1.getCode(), violation1);
        car.setViolationMap(violationMap1);
        Map<String, StaticsParam> statMap1 = new HashMap<>();
        statMap1.put(statParam.getName(), statParam);
        car.setAdditionalParams(statMap1);
        car.setTrailing(true);
        car.setCoachRoute("Coach route");

        newcar = new PassengerCar("000-22222");
        newcar.setCoachType(PassengerCoachType.COMPARTMENT);
        newcar.setDepotDomain(depot);
        newcar.setWorker(worker);
        newcar.setRevisionDateStart(LocalDateTime.now());
        newcar.setRevisionDateEnd(LocalDateTime.now().plusHours(2L));
        Map<Integer, ViolationDomain> violationMap2 = new HashMap<>();
        violationMap2.put(violation2.getCode(), violation2);
        newcar.setViolationMap(violationMap2);
        Map<String, StaticsParam> statMap2 = new HashMap<>();
        statMap2.put(statParam.getName(), statParam);
        newcar.setAdditionalParams(statMap2);
        newcar.setTrailing(true);
        newcar.setCoachRoute("Coach route");

        dinnerCar.setType(DinnerCarsType.BISTRO);
        dinnerCar.setCompanyDomain(company);
        Map<Integer, ViolationDomain> violationMap3 = new HashMap<>();
        violationMap3.put(violation2.getCode(), violation2);
        dinnerCar.setViolationMap(violationMap3);
        dinnerCar.setRevisionDateStart(LocalDateTime.now());
        dinnerCar.setRevisionDateEnd(LocalDateTime.now().plusHours(2L));
        dinnerCar.setWorker(outWorker);

        Map<String, RevisionObject> revMap1 = new HashMap<>();
        revMap1.put(car.getNumber(), car);
        train.setObjectsMap(revMap1);
        train.setAdditionalParams(statMap1);

        Map<String, RevisionObject> otherMap = new HashMap<>();
        otherMap.put(newcar.getNumber(), newcar);
        otherTrain.setObjectsMap(otherMap);
        otherTrain.setAdditionalParams(statMap2);

        order.setCollector(train);
        otherTrainOrder.setCollector(otherTrain);

        String json = makeJsonFromRevObjects(otherTrainOrder);
        updateRevObjectMapFromJson(json, order);

        assertTrue(order.getCollector().getObjectsMap().containsValue(newcar));
    }

    @Test
    public void shouldUpdateRevisionMap() {

    }

    private String makeJsonFromRevObjects(Order order) {

        Gson gson = CustomGson.create();

        if (order instanceof CollectableOrder that) {
            try {
                return gson.toJson(that.getCollector().getObjectsMap());
            } catch (NullPointerException e) {
                return "{}";
            }

        } else if (order instanceof BaggageOrder that) {
            try {
                return gson.toJson(that.getCoachMap());
            } catch (NullPointerException e) {
                return "{}";
            }
        }

        return "{}";
    }

    private void updateRevObjectMapFromJson(String json, Order orderForUpdate) {
        Gson gson = CustomGson.create();

        Type mapType = new TypeToken<Map<String, RevisionObject>>() {
        }.getType();
        Map<String, RevisionObject> deserializedMap = gson.fromJson(json, mapType);

        if (orderForUpdate == null) {
            return;
        }

        for (RevisionObject obj : deserializedMap.values()) {
            orderForUpdate.updateRevisionObject(obj);
        }
    }

}