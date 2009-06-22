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

	private static final String BASE_DLL_NAME = "wel4j";
	
	private static final String X86_DLL_NAME = BASE_DLL_NAME + "32";
	
	private static final String X64_DLL_NAME = BASE_DLL_NAME + "64";
	
	static {
		try {
			if (System.getProperty("os.name").toLowerCase().indexOf("win") != -1) {
				if (System.getProperty("os.arch").indexOf("64") != -1) {
					System.loadLibrary(X64_DLL_NAME);
				} else {
					System.loadLibrary(X86_DLL_NAME);		
				}
			}
		} catch (Exception e) {
			System.err.println("Failed to load native JNI library.");
			e.printStackTrace();
		}
	}
	
	private native static void reportEvent(String sourceName, String eventMessage, int eventId, int eventSeverity, int eventCategory);
	
	public static void log(String sourceName, String eventMessage, int eventId, int eventSeverity, int eventCategory) {
		if (sourceName == null || sourceName.length() == 0) {
			sourceName = BASE_DLL_NAME;
		}
		reportEvent(sourceName, eventMessage, eventId, eventSeverity, eventCategory);
	}
	
	public static void log(WindowsEvent windowsEvent) {
		log(windowsEvent.getSourceName(), windowsEvent.getMessage(), windowsEvent.getEventID(), windowsEvent.getEventSeverity().getSeverityCode(), windowsEvent.getEventCategory());
	}
}
	