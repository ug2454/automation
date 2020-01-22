<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v3="http://cio.att.com/commonheader/v3" xmlns:sch="http://bbnms.lightspeed.att.com/csibbnmssscpet/submitsatellitecperequest/schema"> 
<soapenv:Header> 
<m2:Security xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m2="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"> 
<m2:UsernameToken type="m2:UsernameTokenType"> 
<m2:Username>m46163t</m2:Username> 
<m2:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">rM!m-B1s</m2:Password> 
</m2:UsernameToken> 
</m2:Security> 
</soapenv:Header> 
<soapenv:Body> 
<p:SubmitSatelliteCPERequest xmlns:p="http://bbnms.lightspeed.att.com/csibbnmssscpet/submitsatellitecperequest/schema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 
<p:uverseBAN>${ban}</p:uverseBAN> 
<p:referenceId>2-${originalOrderActionId}</p:referenceId> 
<p:orderNumber>${originalOrderActionId}</p:orderNumber> 
<p:orderActionMode>Closeout</p:orderActionMode> 
<p:applicationId>ISAAC</p:applicationId> 
<p:propertyType>Own</p:propertyType> 
<p:residentialCustomerIndicator>true</p:residentialCustomerIndicator> 
<p:customerSelectedProtectionPlan>protectionplan</p:customerSelectedProtectionPlan> 
<p:sourceSystemURL>http:</p:sourceSystemURL> 
<p:CompatibilityDetails> 
<p:CompatibilityName>MRV</p:CompatibilityName> 
<p:CompatibilityValue>Y</p:CompatibilityValue> 
</p:CompatibilityDetails>
</p:SubmitSatelliteCPERequest> 
</soapenv:Body> 
</soapenv:Envelope>