package eapli.base.event.meeting.application;

import eapli.base.event.meeting.domain.Meeting;
import eapli.base.event.meeting.repositories.MeetingRepository;
import eapli.base.event.recurringPattern.domain.RecurringPattern;
import eapli.base.event.recurringPattern.repositories.RecurringPatternRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.domain.repositories.TransactionalContext;

public class MeetingService {

    private final RecurringPatternRepository patternRepo= PersistenceContext.repositories().recurringPatterns();

    private final TransactionalContext txCtx = PersistenceContext.repositories()
            .newTransactionalContext();
    private final MeetingRepository meetTx= PersistenceContext.repositories().meetings(txCtx);

    public boolean cancelMeeting(Meeting m){
        try {
            txCtx.beginTransaction();

            m.cancel();
            meetTx.save(m);

            RecurringPattern pattern = m.pattern();
            if(!pattern.addException(pattern.startDate())){
                return false;
            }

            patternRepo.save(pattern);

            txCtx.commit();
            return true;
        }catch (ConcurrencyException | IntegrityViolationException e){
            return false;
        }
    }
}
