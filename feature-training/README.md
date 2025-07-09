# Module Feature Training
This module provides the Training feature of the Ember application.
It includes functionality for training models, managing training data, 
and monitoring training progress.

## Trainer Workflow
A trainer is initialized with everything it needs to complete training epochs. For each training period, a trainer follows the same general workflow: for each epoch, sample batches are created; for each batch, the network is evaluated for each sample, with gradients averaged across the batch.

```mermaid

stateDiagram
    onEpochStart : onEpochStart()
    onBatches : onBatchesCreated(List&ltList&ltSampleData&gt&gt)
    onBatchStart : onBatchStart(List&ltSampleData&gt)
    onSampleStart : onSampleStart(SampleData)
    onCost : onCostFound(Float)
    onGradient : onGradientFound(Tensor)
    onSampleEnd : onSampleEnd()
    onBatchEnd : onBatchEnd()
    onEpochEnd : onEpochEnd()
    

    [*] --> onEpochStart
    onEpochStart --> onBatches : Prepare Sample Batches
    onBatches --> onBatchStart : select batch
    onBatchStart --> onSampleStart : select sample
    onSampleStart --> onCost : forward pass
    onCost --> onGradient : back pass
    onGradient --> onSampleEnd : accumulate gradient
    onSampleEnd --> onBatchEnd : update parameters
    onBatchEnd --> onEpochEnd : report batch
    onEpochEnd --> [*] : No more epochs

    onSampleEnd --> onSampleStart : More samples?
    onBatchEnd --> onBatchStart : More batches?
    onEpochEnd --> onEpochStart : More Epochs?

```
