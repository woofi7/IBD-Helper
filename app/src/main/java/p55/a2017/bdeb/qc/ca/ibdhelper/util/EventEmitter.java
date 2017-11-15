package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import java.util.Observable;
import java.util.Observer;

public class EventEmitter extends Observable {
    public void next() {
        this.setChanged();
        this.notifyObservers();
    }

    public void subscribe(Observer observer) {
        this.addObserver(observer);
    }
}
