<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v3="http://cio.att.com/commonheader/v3" xmlns:sch="http://bbnms.lightspeed.att.com/managewllinstallationdetails/schema">
<soapenv:Header>
<v3:WSHeader xmlns:v3="http://cio.att.com/commonheader/v3">
<v3:WSConversationId>279235703></v3:WSConversationId>
</v3:WSHeader>
<m2:Security xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m2="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
<m2:UsernameToken type="m2:UsernameTokenType">
<m2:Username>m46163t</m2:Username>
<m2:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">rM!m-B1s</m2:Password>
</m2:UsernameToken>
</m2:Security>
</soapenv:Header>
<soapenv:Body>
<sch:ManageWLLInstallationDetailsRequest xmlns:sch="http://bbnms.lightspeed.att.com/managewllinstallationdetails/schema">
  <sch:applicationId>GEARS</sch:applicationId>
  <sch:ban>${ban}</sch:ban>
  <sch:mode>${mode}</sch:mode>
  <sch:SelectionCriteria>
    <sch:CPEDetails>
      <sch:deviceType>OWA</sch:deviceType>
      <sch:imei>${imei}</sch:imei>
      <sch:simICCID>${iccid}</sch:simICCID>
      <#if subTransportType?hasContent && subTransportType=="NextGen">	
	      <sch:make>${make!"NETCOMM"}</sch:make>
	      <sch:model>${model!"OWAR1-35"}</sch:model>
      <#else>
      	  <sch:make>${make!"NETCOMM"}</sch:make>
	      <sch:model>${model!"OWAR0-35"}</sch:model>
      </#if>
	  <sch:serialNumber>${serial}</sch:serialNumber>
    </sch:CPEDetails>
  </sch:SelectionCriteria>
</sch:ManageWLLInstallationDetailsRequest>
</soapenv:Body>
</soapenv:Envelope>
