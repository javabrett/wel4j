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

import java.util.logging.Level;

import org.wel4j.WindowsEventSeverity;


public class DefaultLevelAdapter implements LevelAdapter {

	public WindowsEventSeverity windowsEventSeverityFromLevel(Level level) {
		
		if (level == Level.OFF) {
			return WindowsEventSeverity.EVENTLOG_ERROR_TYPE;
		} else if (level == Level.SEVERE) {
			return WindowsEventSeverity.EVENTLOG_ERROR_TYPE;
		} else if (level == Level.WARNING) {
			return WindowsEventSeverity.EVENTLOG_WARNING_TYPE;
		} else if (level == Level.INFO) {
			return WindowsEventSeverity.EVENTLOG_INFORMATION_TYPE;
		} else if (level == Level.CONFIG) {
			return WindowsEventSeverity.EVENTLOG_INFORMATION_TYPE;
		} else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST || level == Level.ALL) {
			return null;
		} else {
			return null;			
		}
	}

}
