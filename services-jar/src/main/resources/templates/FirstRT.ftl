<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prov="http://lightspeed.bbnms.att.com/provisioninginfo">
<soapenv:Header>
<m2:Security xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:m2="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
<m2:UsernameToken type="m2:UsernameTokenType">
<m2:Username>m26306</m2:Username>
<m2:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">f!R5t</m2:Password>
</m2:UsernameToken>
</m2:Security>
</soapenv:Header>
<soapenv:Body>
<prov:retrieveProvisioningStatusInformationRequest>
<prov:header>
<prov:transactionId>?</prov:transactionId>
</prov:header>
<#if ban?hasContent >
<prov:ban>${ban}</prov:ban>
<#else>
<prov:omsOrderNumber>${omsOrderNumber}</prov:omsOrderNumber>
</#if>
<prov:circuitId>WDM</prov:circuitId>
</prov:retrieveProvisioningStatusInformationRequest>
</soapenv:Body>
</soapenv:Envelope>