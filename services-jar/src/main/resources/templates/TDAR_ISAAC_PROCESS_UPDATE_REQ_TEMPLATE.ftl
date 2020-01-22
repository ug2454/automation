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
			<sch:referenceId>2-${originalOrderActionId}</sch:referenceId>
			<!--Optional:-->
			<sch:orderNumber>${originalOrderActionId}</sch:orderNumber>
			<sch:orderActionMode>${orderActionMode}</sch:orderActionMode>
			<!--Optional:-->
			<sch:activityId>${originalOrderActionId}</sch:activityId>
			<!--Optional:-->
			<sch:applicationId>ISAAC</sch:applicationId>
			<!--Optional:-->
			<sch:propertyType>Own</sch:propertyType>
			
			<!--Optional:-->
			<sch:residentialCustomerIndicator>true</sch:residentialCustomerIndicator>
			
			<sch:updateIndicator>true</sch:updateIndicator>
			<!--Optional:-->
			<sch:customerSelectedProtectionPlan>protectionplan</sch:customerSelectedProtectionPlan>
			<sch:sourceSystemURL>http:</sch:sourceSystemURL>
			<#if orderActionMode == "Process">
			<sch:LineItemDetails>
				<!--Optional:-->
				<sch:LineItemType>
					<!--Optional:-->
					<sch:lineItemId>${lineItemID}</sch:lineItemId>
				</sch:LineItemType>
				<sch:serialNumber>${serialnumber}</sch:serialNumber>
				<#if productTypeIdentifier == "Receiver">
				<sch:make>${makeReceiver}</sch:make>
				<sch:model>${modelReceiver}</sch:model>
				<sch:accessCardId>${accessCardId}</sch:accessCardId>
				</#if>
				<#if productTypeIdentifier == "Client">
				<sch:make>${makeClient}</sch:make>
				<sch:model>${modelClient}</sch:model>
				</#if>
				<#if productTypeIdentifier == "Hardware">
				<sch:make>${makeNIRD}</sch:make>
				<sch:model>${modelNIRD}</sch:model>
				</#if>
				<sch:orderLineItemAction>${orderLineItemAction}</sch:orderLineItemAction>
				<sch:location>Room${lineItemID}</sch:location>
				<#if productTypeIdentifier == "Receiver">
				<sch:productName>${productNameReceiver}</sch:productName>
				<sch:productLine>${productLineReceiver}</sch:productLine>
				</#if>
				<#if productTypeIdentifier == "Client">
				<sch:productName>${productNameClient}</sch:productName>
				<sch:productLine>${productLineClient}</sch:productLine>
				</#if>
				<#if productTypeIdentifier == "Hardware">
				<sch:productName>${productNameNIRD}</sch:productName>
				<sch:productLine>${productLineNIRD}</sch:productLine>
				</#if>
				<sch:noSerialIndicator>true</sch:noSerialIndicator>
				<#if productTypeIdentifier == "Receiver" || productTypeIdentifier == "Client">
				<sch:actionType>${actionType}</sch:actionType>
				</#if>
				<#if productTypeIdentifier == "Hardware">
			<sch:actionType>${actionTypeNIRD}</sch:actionType>
				</#if>
				<sch:ResidentialCustomerLineItemDetails>
					<sch:equipmentOwnership>${equipmentOwnership}</sch:equipmentOwnership>
					<sch:productTypeIdentifier>${productTypeIdentifier}</sch:productTypeIdentifier>
					<sch:receiverConnectionType>${receiverConnectionType}</sch:receiverConnectionType>
					<sch:technicianVehicleId>127915</sch:technicianVehicleId>
					<sch:waiverCode>123</sch:waiverCode>
					<sch:technicianId>vv026d</sch:technicianId>
					<sch:CapabilityDetails>
						<sch:capabilityName>ATSC</sch:capabilityName>
						<sch:capabilityValue>True</sch:capabilityValue>
					</sch:CapabilityDetails>
				</sch:ResidentialCustomerLineItemDetails>
				<#if productTypeIdentifier == "Client" && productLineNIRD == "WIRELESS VIDEO BRIDGE"> 
				<sch:macAddress>${macaddress}</sch:macAddress> 
				</#if>
				<sch:receiverId>${receiverID}</sch:receiverId>
				<sch:SwapDetails> 
				<sch:failureReason>Update</sch:failureReason> 
				<sch:swapReceiverId>${swapreceiverID}</sch:swapReceiverId> 
				<#if productTypeIdentifier == "Receiver">
				<sch:swapAccessCardId>${swapaccessCardId}</sch:swapAccessCardId> 
				<sch:swapSerialNumber>${swapSerailnumber}</sch:swapSerialNumber> 
				</#if>
				<#if productTypeIdentifier == "Client">
				<sch:swapMacAddress>${swapMacAddress}</sch:swapMacAddress> 
				</#if>
				</sch:SwapDetails>
			</sch:LineItemDetails>	
			<#if propertyContractType?hasContent || nfflInd?hasContent>			
			<sch:dealerId>${dealerCode}</sch:dealerId>
			</#if>
			</#if>
	</sch:SubmitSatelliteCPERequest>
	</soapenv:Body>
</soapenv:Envelope>