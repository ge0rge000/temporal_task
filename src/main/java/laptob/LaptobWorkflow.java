package laptob;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface LaptobWorkflow {

    @WorkflowMethod
    String getLaptop(String name);
}