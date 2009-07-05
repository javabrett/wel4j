package org.wel4j;

public interface WindowsEventLog {

	public void log(String sourceName, String eventMessage, int eventId, int eventSeverity, int eventCategory);
	
	public void log(WindowsEvent windowsEvent);
	
}
