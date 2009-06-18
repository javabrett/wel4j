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

import org.wel4j.WindowsEvent;

public class WindowsEventLog {

	static {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") != -1) {
			if (System.getProperty("os.arch").indexOf("64") != -1) {
				System.loadLibrary("JNIEventLog64");
			} else {
				System.loadLibrary("JNIEventLog32");		
			}
		}
	}
	
	private native static void reportEvent(String eventMessage, int eventId, int eventSeverity, int eventCategory);
	
	public static void log(String eventMessage, int eventId, int eventSeverity, int eventCategory) {
		reportEvent(eventMessage, eventId, eventSeverity, eventCategory);
	}
	
	public static void log(WindowsEvent windowsEvent) {
		log(windowsEvent.getMessage(), windowsEvent.getEventID(), windowsEvent.getEventSeverity().getSeverityCode(), windowsEvent.getEventCategory());
	}
}
	