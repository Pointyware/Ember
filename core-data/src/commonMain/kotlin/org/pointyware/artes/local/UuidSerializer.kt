@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.artes.local

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
class UuidSerializer: KSerializer<Uuid> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Uuid", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uuid {
        return Uuid.parseHex(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Uuid) {
        encoder.encodeString(value.toHexString())
    }
}
