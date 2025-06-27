package org.pointyware.ember.ui

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

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
    activationFunction: ActivationFunctionIndicator,
) {
    Icon(
        imageVector = vectorResource(activationFunction.displayIcon),
        contentDescription = stringResource(activationFunction.description)
    )
}
