package com.att.bbnmstest.services.util;

public class QueryConstants
{

   public static final String Query_Key_AbstractFlow_Max_FlowableId = "query.bbnms.abstractflowable.max.flowableid";
   public static final String Query_Key_HistroyMessage_Max_ServiceOrder =
      "query.bbnms.historymessage.max.serviceordernumber";
   public static final String Query_Key_EihAll_Max_OrderNumber = "query.bbnms.eihall.max.ordernumber";
   public static final String Query_Key_Uverse_Service_Order_Status = "query.bbnms.service.order.status";
   public static final String Query_Key_Uverse_Order_Status = "query.bbnms.order.status";
   public static final String Query_Key_Uverse_Transport_Status = "query.bbnms.transport.status";
   public static final String Query_Key_Uverse_Csiwf_Prna = "query.bbnms.wifi.createfiberreq.prna";
   public static final String Query_Key_Uverse_L1_Ack = "query.uverse.granite.l1.ack";
   public static final String Query_key_get_mpc_flowbale_id_by_ban = "query.uverse.ol.mpc.fowableId.by.ban";
   public static final String Query_Key_DTV_LineItem_Activation_Status_IsValid_by_order ="query.dtv.lineitem.activation.status.isvalid.by.order";
   public static final String Query_Key_DTV_LineItem_Activation_Status_by_order ="query.dtv.lineitem.activation.status.by.order";
   public static final String Query_Key_DTV_LineItem_Identifier_by_order = "query.dtv.lineitem.idenifier.by.order";
	public static final String Query_Key_DTV_LineItem_Identifier_Productline_by_order = "dtv.lineitem.idenifier.productline.by.order";
	public static final String Query_Key_DTV_LineItem_Identifier_Productline__Apid_No_Wvb_by_order = "dtv.lineitem.idenifier.productline.apid.no.wvb.by.order";
	public static final String Query_Key_DTV_LineItem_Identifier_Productline__Apid_by_order = "dtv.lineitem.idenifier.productline.apid.by.order";
	 public static final String Query_Key_DTV_LineItem_Identifier_Productline_Apid_ActionIndicator_M_by_order = "dtv.lineitem.idenifier.productline.apid.ActionIndicator.M.by.order";
   public static final String Query_Key_DTV_LineItem_Identifier_Productline_Apid_ActionIndicator_by_order = "dtv.lineitem.idenifier.productline.apid.ActionIndicator.by.order";
   public static final String Query_Key_DTV_Issac_ErrorMessage = "query.dtv.Status.description";
   public static final String Query_GUI_SET_BAN = "query.setban.from.gui";

   public static final String Query_Key_GetWllBanFromOl = "query.wll.ban.from.oldb";
   public static final String Query_Key_GetWllBanFromOlForSubtransNull = "query.wll.ban.from.oldb.subtransport.null";

   public static final String Query_key_insert_rec_csinsm = "query.insert.csinsm";
   public static final String Query_key_insert_rec_csisc = "query.insert.switchcontrol";
   public static final String Query_key_update_rec_csinms_A = "query.update.csinsm_a";
   public static final String Query_key_update_rec_csinms_U = "query.update.csinsm_u";
   public static final String Query_key_update_rec_csisc = "query.update.switchcontrol";

   public static final String Query_key_Get_Unique_Iccid = "query.unique.iccid";
   public static final String Query_key_Update_Unique_Iccid = "query.update.unique.iccid";
   public static final String Query_key_Get_Unique_Imei = "query.unique.imei";
   public static final String Query_key_Update_Unique_Imei = "query.update.unique.imei";
   public static final String Query_key_Get_Unique_Imsi = "query.unique.imsi";
   public static final String Query_key_Update_Unique_Imsi = "query.update.unique.imsi";
   public static final String Query_Ban_Cleanup_Automation = "bbnms.bancleanup";

   public static final String Query_Key_GetWllSuspendBanFromOl = "query.wll.suspended.ban";
   public static final String Query_Key_GetWllSubscriberStatus = "query.wll.subscriber.status";

   public static final String Query_key_get_eih_with_blank_msgtag = "query.eih.blank.msgtag";
   public static final String Query_key_get_eih_element = "query.eih";

   public static final String Query_Key_Get_Uverse_Ban_From_ol = "query.uverse.ban.ol";
   public static final String Query_Key_Get_Uverse_Ban_From_ol_GPON = "query.uverse.ban.ol.gpon";
   public static final String Query_Key_Get_Uverse_Ban_From_ol_gpon = "query.uverse.ban.ol.gpon";
   public static final String Query_Key_Get_Uverse_Ban_From_ol_ipco = "query.uverse.ban.ol.ipco";

   public static final String Query_Key_Uverse_PubRG_Status = "query.uverse.pubrg.status";
   public static final String Query_Key_Update_Eligible_Time_For_Task = "query.update.task.eligible.time";
   public static final String Query_Key_Get_Uverse_Ban_From_ol_fbs = "query.uverse.ban.ol.fbs";

   public static final String Query_Key_FBS_update_ext_node_ciena_emux_authstatus = "query.update.authstatus";
   public static final String Query_Key_FBS_update_ext_port = "query.update.extport";
   public static final String Query_Key_FBS_segments = "query.update.segments";
   public static final String Query_Key_FBS_update_ext_service_fbs = "query.update.extservicefbs";
   public static final String Query_Key_FBS_update_dimnumber = "query.update.dimnumber";
   public static final String Query_Key_FBS_Update_ext_node_ciena_emux_rmonstatus = "query.update.rmonstatus";
   public static final String Query_Key_FBS_Update_ext_port_at_gigabiteth = "query.update.gigabiteth";
   public static final String Query_Key_FBS_Update_ext_port_rmonstatus = "query.update.extportrmonstatus";

   public static final String Query_Key_Fetch_DTV_Woli_Orbotal1 = "query.dtv.orbital1.validate";
   public static final String Query_Key_Fetch_DTV_Woli_Mobile_DVR = "query.dtv.MobileDvr.validate";
   public static final String Query_Key_Fetch_DTV_Woli_InternalWvb = "query.dtv.InternalWvb.validate";
   public static final String Query_Key_Mdu_Connected_Property = "query.dtv.mdu.connected.property";
   public static final String Query_Key_Fetch_DTV_DelayedRemove_Value = "query.dtv.DelayedRemove.value";
   public static final String Query_Key_Fetch_DTV_DelayedRemove_Column = "query.dtv.DelayedRemove.column";
   public static final String Query_Key_Fetch_DTV_LargeCustomer_Column = "query.dtv.LargeCustomer.column";
   
   

   public static final String Query_Key_Fetch_DTV_BAN = "query.dtv.ban.fetch";
   public static final String Query_Key_Update_Csinsm_For_Sim_Iccid = "query.update.csinsm.for.sim.iccid";
   public static final String Query_Key_Update_Csinsm_Imsi_For_Sim_Iccid = "query.update.csinsm.imsi.for.sim.iccid";
   public static final String Query_Key_Wll_Get_Imei_Iccid_Imsi_For_Ban = "query.wll.get.imei.iccid.imsi.for.ban";
   public static final String Query_Key_Remove_From_Csics_By_Tn = "query.wll.delete.from.csics.by.tn";
   public static final String Query_Key_Uverse_UpdateCMS = "query.update.cms";

   public static final String Query_Key_Subtransport_Type_From_Subscription =
      "query.wll.subtrasporttype.from.subscription";
   
   public static final String Query_Key_Get_Status_Description = "query.key.get.status.description";
   public static final String Query_Key_Get_Service_Status_From_Subscription_Search = "query.key.get.service.status.subscription.search";

   public static final String Query_Key_Get_Imei_From_Owa_By_Imei = "query.get.imei.from.owa.by.imei";
   public static final String Query_Key_Get_Iccid_From_Owa_By_Iccid = "query.get.iccid.from.owa.by.iccid";
   public static final String Query_Key_Get_Imsi_From_Owa_By_Imsi = "query.get.imsi.from.owa.by.imsi";
   public static final String Query_Key_Getwfid_Ol = "query.getwfid.ol";
   public static final String Query_Key_GetMilestoneData_Ol = "query.getMilestoneData.ol";

   public static final String Query_Validate_Make_Model_From_Wllproductvalidationinfo =
      "query.wll.validate.make.model.from.wllproductvalidationinfo";

   public static final String Query_Validate_Make_Model_From_Wllproductvalidationinfo_By_Subtransporttype =
      "query.wll.validate.make.model.from.wllproductvalidationinfo.by.subtransporttype";

   public static final String Query_Key_Get_Wll_Process_Req_Reject_Reason =
      "query.get.wll.process.reject.reason.by.origorder";

   public static final String Query_Key_Update_Ol_Settings = "query.update.ol.settings";
   public static final String Query_Wll_Eih = "query.wll.eih";

   public static final String Query_Key_DTV_MANUFACTURER_WVB = "query.dtv.wvb.manufacturer";
   public static final String Query_Key_DTV_MODEL_WVB = "query.dtv.wvb.model";
   public static final String Query_Key_GETBAN_OL = "query.getBan.ol";
   public static final String Query_VALIDATE_MIGRATION_STEPSTATUS_OL = "query.migrationstepstatus.ol";
   public static final String Query_VALIDATE_MIGRATION_G2STATUS_CIM = "query.migrationg2status.cim";
   public static final String Query_MIGRATION_NODESTATUS_CIM = "query.migration.nodestatus.cim";
   public static final String Query_MIGRATION_OLDSAP_CIM = "query.migration.oldsap.cim";
   public static final String Query_MIGRATION_NEWSAP_CIM = "query.migration.newsap.cim";
   public static final String Query_MIGRATION_FAILMESSAGE_OL = "query.migration.failmessage.ol";
    public static final String Query_Key_DTV_Multiple_DSWM30 = "query.dtv.productline";
	public static final String Query_Key_DTV_Rec_ID = "query.dtv.client.receiverId";
	public static final String Query_Key_DTV_Rec_ID_CH_order = "query.dtv.client.receiverId.CHorder";
	public static final String Query_Key_DTV_GenieReceiver = "query.dtv.GenieReceiver";
	public static final String Query_Key_DTV_Genie_Flag = "query.dtv.GenieFlag";
	public static final String Query_Key_SwapAction_for_D_receiver = "query.dtv.swapaction";
	public static final String Query_Key_DTV_HardRestore_SU_orders = "query.dtv.hardrestore";
	public static final String Query_Key_DTV_HardSuspend_SU_orders = "query.dtv.hardsuspend";
	public static final String Query_Key_rollback_NoOp_SU_RS_orders = "query.dtv.messagetag_eihall";
	public static final String Query_Key_WOLI_Details = "query.dtv.reassign_woli";
	public static final String Query_Key_MVSR_request = "query.dtv.receiver_action";
	public static final String Query_Key_Fetch_CVOIP_FAILED_ORDERNO = "query.key.fetch.cvoip.failed.orderno";
	public static final String Query_Key_Fetch_InProgress_Order = "query.key.fetch.inprogress.order";
	public static final String Query_Key_Get_Dealer_Code_Receiver = "query.key.get.dealer.code.receiver";
	public static final String Query_Key_Get_Dealer_Code_Hardware = "query.key.get.dealer.code.hardware";
	public static final String Query_Key_Get_Dealer_Asset = "query.key.get.dealer.asset";
	public static final String Query_Key_Get_Nffl_Indicator = "query.key.get.nffl.indicator";
	public static final String Query_Key_Get_Dealer_Code_Swap ="query.key.get.dealer.code.swap";
	public static final String Query_Key_Get_Prov_Ban_Dtv = "query.dtv.prov.ban";
	public static final String Query_Key_Get_Ban = "query.key.get.ban";
	public static final String Query_Key_Get_Orderno = "query.key.get.orderno";
	public static final String Query_Key_Get_BbnmsOrderNo = "query.key.get.bbnmsorderno";
	public static final String Query_Key_Get_Date = "query.key.get.date";
	public static final String Query_Key_Get_Order_Status = "query.key.get.order.status";
	public static final String Query_Key_Get_Subscription_Info_Values = "query.key.get.subscriptioninfo.values";
	public static final String Query_Key_Get_Closeout_Validation = "query.key.get.closeout.validation";
	public static final String Query_Key_Get_EihCount = "query.eihcount.ol";
	
	public static final String Query_Check_RA_SDND_UNAVAILABLE = "query.check.ra.sdnd.unavail";
	public static final String Query_Check_RA_SDND_UNAVAILABLE_MINS = "query.check.ra.sdnd.unavail.mins";
	public static final String Query_GET_RESOURCEHIERARCHY = "query.get.resourcehierarchy";
	}
