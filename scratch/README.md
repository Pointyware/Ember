# Module :scratch
This module is a small attempt to recreate some machine learning primitives from scratch in Kotlin.

## Features
- Tensors
- Activation Functions
  - ReLU
  - Sigmoid
  - Tanh
- Layers
  - Linear (Fully Connected)
  - Exp: Convolutional
- Networks
  - Sequential Networks
- Loss Functions
  - Mean Squared Error
  - Cross Entropy
- Optimizers
  - Gradient Descent
    - Stochastic
- Training
  - Sequential Trainer
  - Exp: Organic Trainer

```mermaid
classDiagram
    class Tensor {
        dimensions: List~Int~
        get(indices: List~Int~): Double
    }
    class ActivationFunction {
        calculate(value: Double): Double
    }
    class Layer {
        weights: Tensor
        biases: Tensor
        activation: ActivationFunction
    }
    Layer *--> Tensor
    Layer *--> ActivationFunction

    note for Network "A neural network composed of neurons."
    class Network
    class SequentialNetwork {
        layers: List~Layer~
    }
    SequentialNetwork *--> "1..*" Layer
    Network <|-- SequentialNetwork

    class Loss {
        calculate(expected: Tensor, actual: Tensor): Double
    }
    note for Optimizer "An optimizer is responsible for <br>adjusting the weights and biases <br>of a layer based on the error <br>gradient."
    class Optimizer {
    }

    note for StudyCase "A study case associates an <br>input with an expected output."
    class StudyCase {
        input: Tensor
        output: Tensor
    }

    note for SequentialTrainer "A trainer presents cases to <br>a network and tracks gradients <br>for back-propagation."
    class SequentialTrainer {
        network: SequentialNetwork
        cases: StudyCase
        lossFunction: Loss
        optimizer: Optimizer
    }
    SequentialTrainer *--> SequentialNetwork
    SequentialTrainer *--> "1.." StudyCase
    SequentialTrainer *--> Loss
    SequentialTrainer *--> Optimizer

    class LearningTensor
    class SimpleTensor
    Tensor <|-- LearningTensor
    Tensor <|-- SimpleTensor

    class ReLU
    class Sigmoid
    class Linear
    ActivationFunction <|-- ReLU
    ActivationFunction <|-- Sigmoid
    ActivationFunction <|-- Linear

    class MeanSquaredError {
    }
    Loss <|-- MeanSquaredError

    class StochasticGradientDescent {
    }
    Optimizer <|-- StochasticGradientDescent

```
