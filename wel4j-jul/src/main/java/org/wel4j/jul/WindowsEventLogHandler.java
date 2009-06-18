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

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.wel4j.WindowsEvent;
import org.wel4j.WindowsEventLog;


public class WindowsEventLogHandler extends Handler {

	private LogRecordAdapter logRecordAdapter = new DefaultLogRecordAdapter();
	
	@Override
	public void publish(LogRecord logRecord) {
		if (!isLoggable(logRecord)) {
			return;
		}
		
		final WindowsEvent windowsEvent = logRecordAdapter.windowsEventFromLogRecord(logRecord);
		if (windowsEvent.getEventSeverity() == null) {
			return;
		}
		WindowsEventLog.log(windowsEvent);
	}
	
	@Override
	public void flush() {
		// NOOP
	}
	
	@Override
	public void close() throws SecurityException {
		// NOOP
	}
	
	public void setLogRecordAdapter(LogRecordAdapter logRecordAdapter) {
		this.logRecordAdapter = logRecordAdapter;
	}
}
