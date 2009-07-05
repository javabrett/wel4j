/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wel4j;

public class WindowsEvent {

	private String sourceName;
	
	private String message;
	
	private int eventID;
	
	private WindowsEventSeverity eventSeverity;
	
	private int eventCategory;

	public WindowsEvent() {
	}
	
	public WindowsEvent(String sourceName, String message, int eventID,
			WindowsEventSeverity eventSeverity, int eventCategory) {
		super();
		this.sourceName = sourceName;
		this.message = message;
		this.eventID = eventID;
		this.eventSeverity = eventSeverity;
		this.eventCategory = eventCategory;
	}

	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public WindowsEventSeverity getEventSeverity() {
		return eventSeverity;
	}

	public void setEventSeverity(WindowsEventSeverity eventSeverity) {
		this.eventSeverity = eventSeverity;
	}

	public int getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(int eventCategory) {
		this.eventCategory = eventCategory;
	}
}
