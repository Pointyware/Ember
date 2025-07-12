# Module Feature Simulation
This module provides the Simulation feature of the Ember application.
It includes functionality for extended training and/or testing in simulated environments.

## Environments
Obviously, the ideal environment would simulate a real, physical world 
as accurately as possible; however, even if we were able to reproduce an
entirely faithful recreation of our reality, a simple model would not be
able to capture the detail, and there would therefore be no benefit.
Following that thinking, we aim to provide a variety of environments that
provide only the necessary details to train some scale of model.

As our `:feature-training` module implements larger and more complex
architectures, we will provide increasingly complex, and realistic
environments to match the representational capacity of those networks.
