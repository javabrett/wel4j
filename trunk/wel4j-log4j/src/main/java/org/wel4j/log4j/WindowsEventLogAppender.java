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

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.wel4j.WindowsEvent;
import org.wel4j.WindowsEventLog;


public class WindowsEventLogAppender extends AppenderSkeleton implements
		Appender {

	private LoggingEventAdapter loggingEventAdapter = new DefaultLoggingEventAdapter();
	
	private Integer eventId = null;
	
	private Integer eventCategory = null;
	
	@Override
	protected void append(LoggingEvent loggingEvent) {
		
		if (!isAsSevereAsThreshold(loggingEvent.getLevel())) {
			return;
		}
		
		final WindowsEvent windowsEvent = loggingEventAdapter.windowsEventFromLoggingEvent(loggingEvent);
		
		if (windowsEvent.getEventSeverity() == null) {
			return;
		}
		
		if (eventId != null) {
			windowsEvent.setEventID(eventId);
		}
		
		if (eventCategory != null) {
			windowsEvent.setEventCategory(eventCategory);
		}
		
		WindowsEventLog.log(windowsEvent);
	}

	public void close() {
		// NOOP
	}

	public boolean requiresLayout() {
		return false;
	}
	
	public void setLoggingEventAdapter(LoggingEventAdapter loggingEventAdapter) {
		this.loggingEventAdapter = loggingEventAdapter;
	}
	
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public void setEventCategory(Integer eventCategory) {
		this.eventCategory = eventCategory;
	}
}
