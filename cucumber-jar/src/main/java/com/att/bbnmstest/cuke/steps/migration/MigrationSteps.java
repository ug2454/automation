package com.att.bbnmstest.cuke.steps.migration;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.steps.BaseStep;

import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.BBNMSLsMigrationService;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class MigrationSteps extends BaseStep {
	@Autowired
	@Qualifier("BBNMSLsMigrationService")
	private BBNMSLsMigrationService migrationService;

	private String maxTimeout = "120";

	@Given("^bulk migration gui was successfully launched$")
	public void checkAccess() throws AutomationBDDServiceException {
		migrationService.loginGui("LaunchGui");
		migrationService.bulkMigrationNavigate();
	}

	@When("^user adds a new rehome migration$")
	public void navigateGui() throws AutomationBDDServiceException, InterruptedException {
		assertTrue(migrationService.checkNodeStatus("Before"));
		migrationService.addMigration();
	}

	@Then("^the add migration is successfull$")
	public void validateGuiFirstPage() throws AutomationBDDServiceException {
		assertTrue(migrationService.validateAddMigration());
	}

	@Given("^rehome move was existing$")
	public void moveIdExist() throws AutomationBDDServiceException {
		assertTrue(migrationService.validateMoveId());
	}

	@When("^move id is searched in gui$")
	public void searchMigrationScreen() throws AutomationBDDServiceException {
		migrationService.searchMigration();
	}

	@Then("^(.+) step is successfull$")
	public void validateMigStepStatus(String stepName) throws AutomationBDDServiceException, InterruptedException {
		if (stepName.equalsIgnoreCase("Validate"))
			assertTrue(migrationService.validateStepStatus("VALIDATE", "SUCCEEDED", maxTimeout));
		else if (stepName.equalsIgnoreCase("CIM_DATA_SETUP"))
			assertTrue(migrationService.validateStepStatus("CIM_DATA_SETUP", "SUCCEEDED", maxTimeout));
		else if (stepName.equalsIgnoreCase("CREATE_SAPS")) {
			assertTrue(migrationService.validateStepStatus("CREATE_SAPS", "SUCCEEDED", maxTimeout));
			assertTrue(migrationService.stepTwoDbStatus("After"));
		} else if (stepName.equalsIgnoreCase("CREATE_ASE_SAPS"))
			assertTrue(migrationService.validateStepStatus("CREATE_ASE_SAPS", "SUCCEEDED", maxTimeout));
		else if (stepName.equalsIgnoreCase("GENERATE_IPADDRESS")) {
			assertTrue(migrationService.validateStepStatus("GENERATE_IPADDRESS", "SUCCEEDED", maxTimeout));
		} else if (stepName.equalsIgnoreCase("COLLECT_IPADDRESS")) {
			assertTrue(migrationService.validateStepStatus("COLLECT_IPADDRESS", "SUCCEEDED", maxTimeout));
			assertTrue(migrationService.validateStepStatus("ASSIGN_IPADDRESS", "SUCCEEDED", maxTimeout));
		} else if (stepName.equalsIgnoreCase("DELETE_ASE_SDP_BINDINGS"))
			assertTrue(migrationService.validateStepStatus("DELETE_ASE_SDP_BINDINGS", "SUCCEEDED", maxTimeout));
		else if (stepName.equalsIgnoreCase("CREATE_ASE_SDP_BINDINGS"))
			assertTrue(migrationService.validateStepStatus("CREATE_ASE_SDP_BINDINGS", "SUCCEEDED", maxTimeout));
	}

	@When("^user starts (.*) step from gui$")
	public void preStep(String stepName) throws AutomationBDDServiceException, InterruptedException {
		if (stepName.equalsIgnoreCase("CIM_DATA_SETUP")) {
			assertTrue(migrationService.stepTwoDbStatus("Before"));
			assertTrue(migrationService.preStepTwo());
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("CREATE_SAPS")) {
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("CREATE_ASE_SAPS")) {
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("GENERATE_IPADDRESS")) {
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("COLLECT_IPADDRESS")) {
			assertTrue(migrationService.preStepSix());
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("DELETE_ASE_SDP_BINDINGS")) {
			assertTrue(migrationService.startStep());
		} else if (stepName.equalsIgnoreCase("CREATE_ASE_SDP_BINDINGS")) {
			assertTrue(migrationService.startStep());
		}

	}

	@When("^user performs rollback from gui$")
	public void rollbackStep() throws AutomationBDDServiceException, InterruptedException {
		assertTrue(migrationService.startRollbackStep());
	}

	@Then("^rollback migration should be successfull$")
	public void rollbackStepValidate() throws AutomationBDDServiceException, InterruptedException {
		assertTrue(migrationService.validateStepStatus("VALIDATE", "READY_TO_START", "130"));
	}

	@Then("^add migration gui tags are correct$")
	public void addMigTagValidate() throws AutomationBDDServiceException, InterruptedException {
		assertTrue(migrationService.validateAddMigTags());
	}

	@Then("^migration details screen has updated tags$")
	public void migStatusTagValidate() throws AutomationBDDServiceException, InterruptedException {
		assertTrue(migrationService.validateSearchMigTags());
	}
}
