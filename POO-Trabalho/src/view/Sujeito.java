package view;

import java.util.Observer;

public interface Sujeito {
	
	public void addObserver(ObservaSujeito o);
    public void removeObserver(ObservaSujeito o);
    public void notificaObservers(int i);

}
