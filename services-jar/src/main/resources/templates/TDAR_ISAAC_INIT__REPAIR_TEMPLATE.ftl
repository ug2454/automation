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
<sch:SubmitSatelliteCPERequest>
<sch:uverseBAN>${ban}</sch:uverseBAN>
<sch:referenceId>2-000000100</sch:referenceId>
<sch:orderNumber>${originalOrderActionId}</sch:orderNumber>
<sch:orderActionMode>Initiate</sch:orderActionMode>
<sch:activityId>${originalOrderActionId}</sch:activityId>
<sch:applicationId>ISAAC</sch:applicationId>
<sch:propertyType>Own</sch:propertyType>
<sch:residentialCustomerRepairIndicator>true</sch:residentialCustomerRepairIndicator>
<sch:residentialCustomerIndicator>true</sch:residentialCustomerIndicator>
<sch:customerSelectedProtectionPlan>protectionplan</sch:customerSelectedProtectionPlan>
<sch:sourceSystemURL>http:</sch:sourceSystemURL>
</sch:SubmitSatelliteCPERequest>
</soapenv:Body>
</soapenv:Envelope>