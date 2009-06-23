#include <stdio.h>
#include <windows.h>
#include <malloc.h>

#include "org_wel4j_WindowsEventLog.h"
#include "Definitions.h"

/*******************************************************************************
**This method is called by the Java application through JNI for logging events in the
**Windows Event Log
/*******************************************************************************/
JNIEXPORT void JNICALL Java_org_wel4j_WindowsEventLog_reportEvent
  (JNIEnv *env, jclass clazz, jstring sourceName, jstring message, jint eventID, jint eventSeverity, jint eventCategory)
{
	const char *source = env->GetStringUTFChars(sourceName, 0);
	const char *msg = env->GetStringUTFChars(message, 0);
	
	DWORD wEventID = (DWORD)eventID;
	WORD  wEventSeverity = (WORD)eventSeverity;
	WORD  wEventCategory = (WORD)eventCategory;		
	
	//convert const char * to WCHAR
	WCHAR *szSource;
	szSource = (WCHAR *)malloc( 2 * strlen(source) + 2);

	for(unsigned int i = 0; i < strlen(msg); i++)
	{
		szSource[i] = (WCHAR)source[i];
	}	     
	szSource[strlen(source)] = L'\0';

    //Get the handle to the event source
	HANDLE h = RegisterEventSource(NULL,  // uses local computer 
							(LPCWSTR) szSource); // source name 

    if(h != NULL)
	{
		//convert jstring into WCHAR
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
		free(szSource);
	}

	env->ReleaseStringUTFChars(message, msg);
	env->ReleaseStringUTFChars(sourceName, source);
}
