package com.att.bbnmstest.cuke;

public class StepConstants
{

   public static final String ISSACT_REQ_INITIATE = "Initiate";
   public static final String ISSACT_REQ_PROCESS = "Process";
   public static final String ISSACT_REQ_SWAP = "Swap";
   public static final String ISSACT_REQ_CLOSEOUT = "Closeout";


   public static final String OMS_REQ_PT = "PT";
   public static final String OMS_REQ_PTC = "PTC";
   public static final String OMS_REQ_CPE = "CPE";
   public static final String OMS_REQ_BAN_TREATMENT = "BanTreatment";

   public static final String TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS = "Receive Transport Request from OMS";
   public static final String TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL = "Receive Exchange CPE Data 1st Call - CPE Data";
   public static final String TASK_SEND_DISPATCH_TICKET = "Send Dispatch Ticket";
   public static final String TASK_EXECUTE_AHL = "Execute Ahl";
   public static final String TASK_SEND_PONR = "Send Order Level PONR";
   public static final String TASK_RECEIVE_OWA_INSTALLATION = "Receive OWA Installation";
   public static final String TASK_WLL_SERVICE_ACTIVAION = "Wll Service Activation";
   public static final String TASK_WLL_SERVICE_DEACTIVAION = "Deactivate Wll Service";
   public static final String TASK_RECEIVE_NAD_INFO = "Receive NAD Info";
   public static final String TASK_RECEIVE_WORK_ORDER_COMLETTION = "Receive Work Order Completion";
   public static final String TASK_SEND_ORDER_LEVEL_PONR = "Send Order Level PONR";
   public static final String TASK_SEND_CEASE_PONR = "Wait For Cease PONR Notification";
   public static final String TASK_NOTIFY_CEASE_PONR = "Notify Move Cease Of Cease PONR";
   public static final String TASK_PURGE_ORDER = "Purge order";
   public static final String TASK_ACTIVATE_SERVICES_IN_UDAS = "Activate Services in UDAS";
   public static final String TASK_RETREIVE_WOLI_INFO_FROM_AHL = "Retrieve WOLI Info from AHL";
   public static final String TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION = "Wait For Initiate WOLI Activation";
   public static final String TASK_RECEIVE_INITIATE_WOLI_ACTIVATION = "Receive Initiate WOLI Activation";
   public static final String TASK_RECEIVE_COMPLETE_WOLI_ACTIVATION = "Receive Complete WOLI Activation";
   public static final String TASK_RECEIVE_WLL_BAN_TREATEMENT_REQ_FROM_OMS =
      "Receive Wll BAN Treatment Request from OMS";
   public static final String TASK_CROSS_PRODUCT_VALIDATION = "Cross Product Validation";
   public static final String TASK_SUSPEND_RESUME_SERVICE = "Suspend/Resume Services";
   public static final String TASK_INITITATE_SUSPEND_RESUME_REQ = "Initiate Suspend/Resume Request";
   public static final String TASK_SEND_BAN_TREATEMENT_COMPLETION_TO_OMS = "Send BAN Treatment Completion to OMS";
   public static final String TASK_HOLD_FOR_DELAY = "Hold for Delay Timer";
   public static final String TASK_WAIT_FOR_OWA_INSTALLATION = "Wait for OWA Installation";
   public static final String TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE = "provisionTransport Response";
   public static final String TASK_TRANSPORT_STATUS_EXCHANGE_CPE_DATA_CALL_RESPONSE = "exchangeCpeDataCall1 Response";
   public static final String TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_COMPLETE_RESPONSE =
      "provisionTransportComplete Response";
   public static final String TASK_COMPLETE_MOVE_CEASE_ORDER = "Complete Move Cease Order";
   public static final String TASK_QUERY_CMS_FOR_OWA_INFO = "Query CMS for OWA Info";
   public static final String TASK_RECEIVE_TRANSPORT_REQUEST_2ND_CALL = "Receive Transport Request 2nd Call - Complete";
   public static final String TASK_TRANSPORT_REQUEST_RESPONSE = "Transport Request Response";
   public static final String TASK_HOLD_FOR_DELAY_DEFINATION_ID = "T29910";
   public static final String TASK_HOLD_FOR_PONR = "Hold For PONR";
   public static final String TASK_REMOVE_WLL_PROFILE = "Remove Wll Profile";
   public static final String TASK_REMOVE_WLL_POLICIES = "Remove Wll Policies";
   public static final String TASK_DEACTIVATE_WLL_SERVICE = "Deactivate Wll Service";

   public static final String WLL_PROCESS_SUCCESS_RESPOSNE_MSG = "Request is successfully received";

   public static final String WLL_FIRST_RT_SUCCESS_RESPONSE_TAGNAME = "<tagName>productType</tagName>";
   public static final String WLL_FIRST_RT_SUCCESS_RESPONSE_TAGVALUE = "<tagValue>WDM</tagValue>";

   public static final String ORDER_TYPE_PROVIDE = "PR";
   public static final String ORDER_TYPE_CHANGE = "CH";
   public static final String ORDER_TYPE_CEASE = "CE";
   public static final String ORDER_TYPE_MOVE = "PV";

   public static final String ORDER_SUB_TYPE_NOT_APPLICABLE = "NA";

   public static final String WLL_SUB_TRANSPORT_TYPE_NEXTGEN = "NextGen";
   public static final String WLL_SUB_TRANSPORT_TYPE_BLANK = "Blank";

   public static final String ATTRIBUTE_TN = "tn";
   
   public static final String Deactivate_Policies_in_G2 = "Deactivate Policies in G2";
   public static final String Deactivate_EMS_CMS_SAM = "Deactivate EMS/CMS/SAM";
}

