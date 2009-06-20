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

package org.wel4j.log4j;

import org.apache.log4j.spi.LoggingEvent;
import org.wel4j.WindowsEvent;

public class DefaultLoggingEventAdapter implements LoggingEventAdapter {

	private static final int DEFAULT_EVENT_ID = 0x1000;
	
	private static final int DEFAULT_EVENT_CATEGORY = 0x1;
	
	private LevelAdapter levelAdapter = new DefaultLevelAdapter();

	private int eventId = DEFAULT_EVENT_ID;
	
	private int eventCategory = DEFAULT_EVENT_CATEGORY;
	
	public WindowsEvent windowsEventFromLoggingEvent(LoggingEvent loggingEvent) {
		final WindowsEvent windowsEvent = new WindowsEvent();
		windowsEvent.setMessage(loggingEvent.getRenderedMessage());
		windowsEvent.setEventID(eventId);
		windowsEvent.setEventSeverity(levelAdapter.windowsEventSeverityFromLevel(loggingEvent.getLevel()));
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
