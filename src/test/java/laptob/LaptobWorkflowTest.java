package laptob;

import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowRule;
import laptob.Format;
import laptob.FormatImpl;
import laptob.LaptobWorkflow;
import laptob.LaptobWorkflowImpl;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LaptobWorkflowTest {

    @Rule
    public TestWorkflowRule testWorkflowRule =
            TestWorkflowRule.newBuilder()
                    .setWorkflowTypes(LaptobWorkflowImpl.class)
                    .setDoNotStart(true)
                    .build();

    @Test
    public void testGetGreeting() {
        testWorkflowRule.getWorker().registerActivitiesImplementations(new FormatImpl());
        testWorkflowRule.getTestEnvironment().start();

        LaptobWorkflow workflow =
                testWorkflowRule
                        .getWorkflowClient()
                        .newWorkflowStub(
                                LaptobWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        String laptop = workflow.getLaptop("Dell");
        assertEquals("laptop Dell!", laptop);
        testWorkflowRule.getTestEnvironment().shutdown();
    }

    @Test
    public void testMockedGetGreeting() {
        Format formatActivities = mock(Format.class, withSettings().withoutAnnotations());
        when(formatActivities.setLaptop(anyString())).thenReturn("laptop Dell!");
        testWorkflowRule.getWorker().registerActivitiesImplementations(formatActivities);
        testWorkflowRule.getTestEnvironment().start();

        LaptobWorkflow workflow =
                testWorkflowRule
                        .getWorkflowClient()
                        .newWorkflowStub(
                                LaptobWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        String laptop = workflow.getLaptop("Dell");
        assertEquals("laptop Dell!", laptop);
        testWorkflowRule.getTestEnvironment().shutdown();
    }
}