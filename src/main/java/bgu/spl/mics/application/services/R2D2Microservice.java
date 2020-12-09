package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    private Diary diary;
    private DeactivationEvent deactivation;
    private long duration;

    public R2D2Microservice(long duration) {
        super("R2D2");
        this.duration = duration;
        deactivation = new DeactivationEvent();
    }

    @Override
    protected void initialize() {
        diary = Diary.getInstance();
        subscribeEvent(DeactivationEvent.class, (DeactivationEvent deactivation)-> {
            this.deactivation = deactivation;
            Thread.sleep(duration);
            complete(deactivation, true);
            diary.setR2D2Deactivate(System.currentTimeMillis());
            subscribeBroadcast(TerminateBroadcast.class, (R2D2)->terminate());
        });
        l1countDown();
    }
/*
    private void handleDeactivate(DeactivationEvent deactivation) throws InterruptedException {
        //handle the deactivation event
        this.deactivation = deactivation;
        Thread.sleep(duration);
        complete(deactivation, true);
        diary.setR2D2Deactivate(System.currentTimeMillis());
        subscribeBroadcast(TerminateBroadcast, (R2D2)->terminate());
    }

 */


}
