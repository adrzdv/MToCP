package com.adrzdv.mtocp.domain.validation;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.domain.model.enums.DinnerCarsType;
import com.adrzdv.mtocp.domain.model.enums.PassengerCoachType;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.DinnerCar;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.PassengerCar;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class RegularValidator {

    public final void addRevisionObject(RevisionObject o) {
        if (o instanceof Coach c) {
            validateCoach(c);
            validateCoachType(c);
        } else {
            validateTerminal(o);
        }
        doAddRevisionObject(o);
    }

    public final void addCrewWorker(WorkerDomain worker) {
        validateWorker(worker);
        doAddWorker(worker);
    }

    protected abstract void doAddWorker(WorkerDomain workerDomain);

    protected abstract void doAddRevisionObject(RevisionObject o);

    private void validateWorker(WorkerDomain workerDomain) {
        String regFull = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$";
        String regShort = "^[А-ЯЁ][а-яё]+ [А-ЯЁ].+[А-ЯЁ].+$";
        String regShortSpaces = "^[А-ЯЁ][а-яё]+ [А-ЯЁ].+ [А-ЯЁ].+$";
        if (!Pattern.matches(regFull, workerDomain.getName())
                && !Pattern.matches(regShort, workerDomain.getName())
                && !Pattern.matches(regShortSpaces, workerDomain.getName())) {
            throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
        }
    }

    private void validateCoach(RevisionObject o) {
        String typicalCoach = "\\d{3}-\\d{5}";
        String suburbCoach = "\\d{5}";
        if (!Pattern.matches(typicalCoach, o.getNumber())
                && !Pattern.matches(suburbCoach, o.getNumber())) {
            throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
        }
    }

    private void validateCoachType(RevisionObject o) {
        Map<PassengerCoachType, List<Integer>> patterns = Map.of(
                PassengerCoachType.LUXURY, List.of(0),
                PassengerCoachType.FIRST_CLASS_SLEEPER, List.of(0),
                PassengerCoachType.COMPARTMENT, List.of(0, 1),
                PassengerCoachType.OPEN_CLASS_SLEEPING, List.of(2),
                PassengerCoachType.INTERREGIONAL, List.of(3));

        Map<DinnerCarsType, List<Integer>> dinnerPatterns = Map.of(
                DinnerCarsType.BISTRO, List.of(6),
                DinnerCarsType.RESTAURANT, List.of(6)
        );

        String number = o.getNumber();
        int firstDigit = Integer.parseInt(String.valueOf(number.charAt(number.indexOf("-") + 1)));
        if (o instanceof PassengerCar c && !o.getNumber().isEmpty()) {
            PassengerCoachType type = c.getCoachType();
            if (!patterns.get(type).contains(firstDigit)
                    && !(type == PassengerCoachType.INTERREGIONAL && o.getNumber().length() == 5)) {
                throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
            }
        } else if (o instanceof DinnerCar c && !c.getNumber().isEmpty()) {
            DinnerCarsType type = c.getType();
            if (!dinnerPatterns.get(type).contains(firstDigit)) {
                throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
            }
        } else {
            throw new ClassCastException(MessageCodes.CAST_ERROR.toString());
        }
    }

    private void validateTerminal(RevisionObject o) {
        String terminal = "\\d{3}[А-ЯЁ]\\d{2}";
        if (!Pattern.matches(terminal, o.getNumber())) {
            throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
        }
    }
}
