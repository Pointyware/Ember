package org.pointyware.artes.entities

/**
 * I am conflating skill and ability here because 1) I don't see a significant difference between
 * the two on a neural level, 2) a skill is usually considered a proficiency in some ability and
 * proficiency is our goal, and 3) I have found several instances where they are defined in terms
 * of each other.
 */
sealed interface Skill {
    object Completion: Skill
    object Edit: Skill
    object Conversation: Skill
    object Code: Skill

    object Text2Image: Skill
    object Image2Text: Skill
    interface Image2Image: Skill {
        object Inpainting: Image2Image
        object Outpainting: Image2Image
        object Upscaling: Image2Image
    }

    object Text2Audio: Skill
    object Audio2Text: Skill
    // why no audio2audio?

    object Text2Video: Skill
    object Video2Text: Skill
    // why no video2video?

    /**
     * Text-to-3D is pretty cool, but I'm pretty sure this is implemented through tool use. Need
     * to read the NVidia paper.
     *
     * "Data" here could be 3D models or any other arbitrary data, depending on the tool used.
     */
    object Text2Data: Skill

    /**
     * A general skill used by intelligent beings to model a future state of their environment.
     */
    object Anticipation: Skill

    /**
     * Some may consider this an ability, however I would like to think of it along with these skills. Given
     * some neural network's internal representation of concepts, the current perceived state, the
     * anticipated state, and some goal (or motivation), various systems are energized to act
     * presumably towards that goal.
     */
    object Energization: Skill
}
