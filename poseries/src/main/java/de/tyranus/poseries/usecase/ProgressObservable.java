package de.tyranus.poseries.usecase;

import java.util.Observable;

public class ProgressObservable extends Observable {
	public void updateProgress() {
		setChanged();
		notifyObservers();
	}
}
