package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import java.util.Observable;
import java.util.Observer;

public class EventEmitter extends Observable {
    public void next() {
        this.setChanged();
        this.notifyObservers();
    }

    public void next(Object arg) {
        this.setChanged();
        this.notifyObservers(arg);
    }

    public void subscribe(Observer observer) {
        this.addObserver(observer);
    }
}
