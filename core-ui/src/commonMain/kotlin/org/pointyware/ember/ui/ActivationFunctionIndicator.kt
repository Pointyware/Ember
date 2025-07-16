package org.pointyware.ember.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.pointyware.ember.entities.activations.ActivationFunction
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.entities.activations.Logistic

enum class ActivationFunctionIndicator(
    val displayName: StringResource,
    val description: StringResource,
    val displayIcon: DrawableResource,
) {
    RELU(
        displayName = Res.string.label_relu,
        description = Res.string.description_sigmoid,
        displayIcon = Res.drawable.agent_24 // TODO: replace with actual assets
    ),
    SIGMOID(
        displayName = Res.string.label_sigmoid,
        description = Res.string.description_sigmoid,
        displayIcon = Res.drawable.agent_24
    ),
    UNKNOWN(
        displayName = Res.string.label_sigmoid,
        description = Res.string.description_sigmoid,
        displayIcon = Res.drawable.agent_24
    )
}

@Composable
fun ActivationFunctionIndicatorView(
    value: ActivationFunction
) {
    val indicator: ActivationFunctionIndicator = remember(value) { when (value) {
        is Logistic -> ActivationFunctionIndicator.SIGMOID
        is ReLU -> ActivationFunctionIndicator.RELU
        else -> ActivationFunctionIndicator.UNKNOWN
    } }
    Icon(
        imageVector = vectorResource(indicator.displayIcon),
        contentDescription = stringResource(indicator.description)
    )
}
