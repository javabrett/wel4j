package org.wel4j.jul;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wel4j.WindowsEvent;
import org.wel4j.WindowsEventLog;
import org.wel4j.WindowsEventSeverity;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

public class WindowsEventLogHandlerTest {

	private WindowsEventLog mockWindowsEventLog;
	private WindowsEventLogHandler handler;
	
	@Before
	public void createTestSubjectAndMocks() {
		mockWindowsEventLog = createMock(WindowsEventLog.class);
		handler = createHandler(mockWindowsEventLog);
	}
	
	@After
	public void verifyMocks() {
		verify(mockWindowsEventLog);
	}
	
	@Test
	public void logsEventAtInfoWithDefaults() {
		final LogRecord logRecord = new LogRecord(Level.INFO, "test message");

		final Capture<WindowsEvent> windowsEventCapture = new Capture<WindowsEvent>();
		mockWindowsEventLog.log(capture(windowsEventCapture));
		
		replay(mockWindowsEventLog);
		handler.publish(logRecord);
		
		final WindowsEvent capturedWindowsEvent = windowsEventCapture.getValue();
		
		// assertions on WindowsEvent
		assertNull("Expected null sourceName for default", capturedWindowsEvent.getSourceName());
		assertEquals("Unexpected event ID", 0x1000, capturedWindowsEvent.getEventID());
		assertEquals("Unexpected event category", 0x001, capturedWindowsEvent.getEventCategory());
		assertEquals("Unexpected event severity", WindowsEventSeverity.EVENTLOG_INFORMATION_TYPE, capturedWindowsEvent.getEventSeverity());
		assertEquals("Unexpected event message", "test message", capturedWindowsEvent.getMessage());
	}
	
	
	@Test
	public void defaultHandlerLevelIsInfo() {
		replay(mockWindowsEventLog);
		assertEquals("Expected default handler level to be INFO", Level.INFO, handler.getLevel());
	}
	
	@Test
	public void defaultHandlerExcludesFineEvent() {
		final LogRecord logRecord = new LogRecord(Level.FINE, "test message");
		
		replay(mockWindowsEventLog);
		handler.publish(logRecord);
	}
	
	@Test
	public void delegatesWindowsEventCreation() {
		final LogRecord logRecord = new LogRecord(Level.INFO, "ignored for this test");
		final LogRecordAdapter mockLogRecordAdapter = createMock(LogRecordAdapter.class);
		final WindowsEvent windowsEvent = new WindowsEvent();
		windowsEvent.setEventSeverity(WindowsEventSeverity.EVENTLOG_INFORMATION_TYPE);
		
		handler.setLogRecordAdapter(mockLogRecordAdapter);
		
		expect(mockLogRecordAdapter.windowsEventFromLogRecord(logRecord)).andReturn(windowsEvent);
		mockWindowsEventLog.log(same(windowsEvent));
		
		replay(mockWindowsEventLog, mockLogRecordAdapter);
		handler.publish(logRecord);
		verify(mockLogRecordAdapter);
	}
	
	protected WindowsEventLogHandler createHandler(final WindowsEventLog windowsEventLog) {
		return new WindowsEventLogHandler() {
			@Override
			protected WindowsEventLog getWindowsEventLog() {
				return windowsEventLog;
			}
		};
	}
}
