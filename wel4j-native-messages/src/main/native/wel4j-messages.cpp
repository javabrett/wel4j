/**********************************************************************************
** Regsiter the wel4j-messages.dll as the message dll for use of Windows
**Event Viewer service for the wel4j Java application
**********************************************************************************/

#include "stdafx.h"

HMODULE myModule;

BOOL APIENTRY DllMain( HANDLE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
					 )
{
	myModule = (HMODULE)hModule;
    return TRUE;
}


/**********************************************************************************
** Regsiter the myMessage.dll as the message dll for use of Event Viewer service
** for the wel4j application
**********************************************************************************/
STDAPI DllRegisterServer()
{
	WCHAR szModulePath[256];
	LONG result = ERROR_SUCCESS;

	if(GetModuleFileName(myModule,szModulePath, 256) == 0)
		return ERROR_PATH_NOT_FOUND;

	HKEY hk;
    // Add wel4j source name as a subkey under the Application
    // key in the EventLog registry key.
    if(result != RegCreateKey(HKEY_LOCAL_MACHINE,
							  L"SYSTEM\\CurrentControlSet\\Services\\EventLog\\Application\\wel4j",
							  &hk))
	{
		return result;
	}

	//Add the Event Message File entry
	if(result != RegSetValueEx(hk,
							   L"EventMessageFile",
							    0,
								REG_EXPAND_SZ,
								(LPBYTE)szModulePath,
								2 * lstrlen(szModulePath) + 2))
	{
		return result;
	}

	//Add the types of events source can generate
    DWORD dwData = EVENTLOG_ERROR_TYPE | EVENTLOG_WARNING_TYPE | EVENTLOG_INFORMATION_TYPE;
    if (result != RegSetValueEx(hk,
								L"TypesSupported",
								0,
								REG_DWORD,
								(LPBYTE) &dwData,
								sizeof(DWORD)))
	{
		return result;
	}

	//Add the Category Message File
	if (result != RegSetValueEx(hk,
								L"CategoryMessageFile",
								0,
								REG_EXPAND_SZ,
								(LPBYTE)szModulePath,
								2 * lstrlen(szModulePath) + 2))
	{
		return result;
	}


	//Add the number of categories described in the message file
	DWORD dwCategoryCount = 0x00000001;
	if(result != RegSetValueEx(hk,
								L"CategoryCount",
								0,
								REG_DWORD,
								(LPBYTE)&dwCategoryCount,
								sizeof(DWORD)))
	{
		return result;
	}

    RegCloseKey(hk);

	return result;
}

/**********************************************************************************
** UnRegsiter the wel4j-messages.dll as the message dll from use of Event Viewer service
** for the wel4j application
**********************************************************************************/
STDAPI DllUnregisterServer()
{
	return RegDeleteKey(HKEY_LOCAL_MACHINE, L"SYSTEM\\CurrentControlSet\\Services\\EventLog\\Application\\wel4j");
}
