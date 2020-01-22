package com.att.bbnmstest.services.dispatcher;

import java.util.Map;

import com.att.bbnmstest.services.exception.AutomationBDDServiceException;

public interface RequestDispatcher
{

   public Object sendRequest(Map orderDetailsAsMap, String template) throws AutomationBDDServiceException;
}
