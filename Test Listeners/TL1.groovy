import org.openqa.selenium.WebDriver

import com.kazurayam.subprocessj.docker.ContainerFinder;
import com.kazurayam.subprocessj.docker.ContainerFinder.ContainerFindingResult;
import com.kazurayam.subprocessj.docker.ContainerRunner;
import com.kazurayam.subprocessj.docker.ContainerRunner.ContainerRunningResult;
import com.kazurayam.subprocessj.docker.ContainerStopper;
import com.kazurayam.subprocessj.docker.ContainerStopper.ContainerStoppingResult;
import com.kazurayam.subprocessj.docker.model.ContainerId;
import com.kazurayam.subprocessj.docker.model.DockerImage;
import com.kazurayam.subprocessj.docker.model.PublishedPort;
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.nio.file.Files

class TL1 {
	
	private static final int HOST_PORT = 80;
	private static final PublishedPort publishedPort = new PublishedPort(HOST_PORT, 8080);
	private static final DockerImage image = new DockerImage("kazurayam/flaskr-kazurayam:1.1.0");
	
	@BeforeTestCase
	def beforeTestTestCase(TestCaseContext testCaseContext) {
		if (testCaseContext.getTestCaseId().contains("flaskr")) {
			println testCaseContext.getTestCaseId()
			File directory = Files.createTempDirectory("TL1").toFile()
			ContainerRunner runner =
					new ContainerRunner.Builder(image)
							.directory(directory)
							.publishedPort(publishedPort)
							.build()
			ContainerRunningResult crr = runner.run()
			if (crr.returncode() != 0) {
				KeywordUtil.markFailedAndStop(crr.toString())
			}
			WebUI.delay(3)   // wait 3 seconds for the server to come up
		}
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		if (testCaseContext.getTestCaseId().contains("flaskr")) {
			println testCaseContext.getTestCaseId()
			ContainerFindingResult cfr = ContainerFinder.findContainerByHostPort(publishedPort)
			if (cfr.returncode() == 0) {
				ContainerId containerId = cfr.containerId()
				ContainerStoppingResult csr = ContainerStopper.stopContainer(containerId)
				if (csr.returncode() != 0) {
					KeywordUtil.markFailedAndStop(csr.toString())
				}
			} else {
				KeywordUtil.markFailedAndStop(cfr.toString())
			}
		}
	}

}