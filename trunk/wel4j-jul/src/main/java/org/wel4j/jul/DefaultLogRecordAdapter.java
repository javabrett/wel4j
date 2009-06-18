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

import java.util.logging.LogRecord;

import org.wel4j.WindowsEvent;


public class DefaultLogRecordAdapter implements LogRecordAdapter {

	private LevelAdapter levelAdapter = new DefaultLevelAdapter();
	
	public WindowsEvent windowsEventFromLogRecord(LogRecord logRecord) {
		// TODO Auto-generated method stub
		final WindowsEvent windowsEvent = new WindowsEvent();
		windowsEvent.setMessage(logRecord.getMessage());
		windowsEvent.setEventID(0);
		windowsEvent.setEventSeverity(levelAdapter.windowsEventSeverityFromLevel(logRecord.getLevel()));
		windowsEvent.setEventCategory(0);
		
		return windowsEvent;
	}
	
	public void setLevelAdapter(LevelAdapter levelAdapter) {
		this.levelAdapter = levelAdapter;
	}
}
