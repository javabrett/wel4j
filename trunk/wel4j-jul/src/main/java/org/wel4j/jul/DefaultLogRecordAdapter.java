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

package org.wel4j.jul;

import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.wel4j.WindowsEvent;


public class DefaultLogRecordAdapter implements LogRecordAdapter {

	private static final int DEFAULT_EVENT_ID = 0x1000;
	
	private static final int DEFAULT_EVENT_CATEGORY = 0x1;
	
	private LevelAdapter levelAdapter = new DefaultLevelAdapter();
	
	private int eventId = DEFAULT_EVENT_ID;
	
	private int eventCategory = DEFAULT_EVENT_CATEGORY;
	
	public DefaultLogRecordAdapter() {
		final LogManager logManager = LogManager.getLogManager();
		try {
			final String eventIdString = logManager.getProperty(getClass().getName() + ".eventId");
			if (eventIdString != null && eventIdString.length() > 0) {
				eventId = Integer.parseInt(eventIdString);	
			}
		} catch (NumberFormatException e) {
			System.err.println("Failed to convert eventId setting to an int, using default.");
			e.printStackTrace(System.err);
		}
		try {
			final String eventCategoryString = logManager.getProperty(getClass().getName() + ".eventCategory");
			if (eventCategoryString != null && eventCategoryString.length() > 0) {
				eventCategory = Integer.parseInt(eventCategoryString);	
			}
		} catch (NumberFormatException e) {
			System.err.println("Failed to convert eventCategory setting to an int, using default.");
			e.printStackTrace(System.err);
		}
	}
	
	public WindowsEvent windowsEventFromLogRecord(LogRecord logRecord) {
		final WindowsEvent windowsEvent = new WindowsEvent();
		windowsEvent.setMessage(logRecord.getMessage());
		windowsEvent.setEventID(eventId);
		windowsEvent.setEventSeverity(levelAdapter.windowsEventSeverityFromLevel(logRecord.getLevel()));
		windowsEvent.setEventCategory(eventCategory);
		
		return windowsEvent;
	}
	
	public void setLevelAdapter(LevelAdapter levelAdapter) {
		this.levelAdapter = levelAdapter;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public void setEventCategory(int eventCategory) {
		this.eventCategory = eventCategory;
	}
}
