#include <stdio.h>
#include <windows.h>
#include <malloc.h>

#include "org_wel4j_WindowsEventLog.h"
#include "Definitions.h"

/*******************************************************************************
**This method is called by the Java application through JNI for loggine events in the
**Windows Event Viewer
/*******************************************************************************/
JNIEXPORT void JNICALL Java_org_wel4j_WindowsEventLog_reportEvent
  (JNIEnv *env, jclass clazz, jstring message, jint eventID, jint eventSeverity, jint eventCategory)
{
	const char *msg = env->GetStringUTFChars(message, 0);

	DWORD wEventID = (DWORD)eventID;
	WORD  wEventSeverity = (WORD)eventSeverity;
	WORD  wEventCategory = (WORD)eventCategory;		
	
    //Get the handle to the event source
	HANDLE h = RegisterEventSource(NULL,  // uses local computer 
							APPLICATION_NAME); // source name 

    if(h != NULL)
	{
		//convert jstring inot WCHAR
		WCHAR *szMsg[1];
		szMsg[0] = (WCHAR *)malloc( 2 * strlen(msg) + 2);

		for(unsigned int i = 0; i < strlen(msg); i++)
		{
			szMsg[0][i] = (WCHAR)msg[i];
		}	     
		szMsg[0][strlen(msg)] = L'\0';

		//Log the event in the EventLog
		ReportEvent(h,           // event log handle 
					wEventSeverity,  // event type 
					wEventCategory,   // category zero 
					wEventID,        // event identifier 
					NULL,                 // no user security identifier 
					1,                    // one substitution string 
					0,                    // no data 
					(LPCWSTR *) szMsg,     // pointer to string array 
					NULL);                // pointer to data 
		
		DeregisterEventSource(h); 
		free(szMsg[0]);
	}

	env->ReleaseStringUTFChars(message, msg);
}
