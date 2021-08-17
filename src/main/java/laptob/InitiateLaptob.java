package laptob;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class InitiateLaptob {

    public static void main(String[] args) throws Exception {
        // This gRPC stubs wrapper talks to the local docker instance of the Temporal service.
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.LAPOP_MODEL_IS_EXPENSIVE)
                .build();
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        LaptobWorkflow workflow = client.newWorkflowStub(LaptobWorkflow.class, options);
        // Synchronously execute the Workflow and wait for the response.
        String laptop = workflow.getLaptop("Dell");
        System.out.println(laptop);
        System.exit(0);
    }
}