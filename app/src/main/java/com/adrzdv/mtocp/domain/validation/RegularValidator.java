package com.adrzdv.mtocp.domain.validation;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject;
import com.adrzdv.mtocp.domain.model.revisionobject.basic.coach.Coach;
import com.adrzdv.mtocp.domain.model.workers.WorkerDomain;

import java.util.regex.Pattern;

public abstract class RegularValidator {

    public final void addRevisionObject(RevisionObject o) {
        if (o instanceof Coach c) {
            validateCoach(c);
        } else {
            validateTerminal(o);
        }
        doAddRevisionObject(o);
    }

    public final void addCreWorker(WorkerDomain worker) {
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

    private void validateTerminal(RevisionObject o) {
        String terminal = "\\d{3}[А-ЯЁ]\\d{2}";
        if (!Pattern.matches(terminal, o.getNumber())) {
            throw new IllegalArgumentException(MessageCodes.PATTERN_MATCHES_ERROR.toString());
        }
    }
}
