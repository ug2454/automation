<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.sender.bbnms.att.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:sendMessageToQueue>
         <MessageSenderRequest_V2>
            <environment>${ENV}</environment>
            <queue>${queue}</queue>
		  <message><![CDATA[${message}]]></message>
         </MessageSenderRequest_V2>
      </ws:sendMessageToQueue>
   </soapenv:Body>
</soapenv:Envelope>

