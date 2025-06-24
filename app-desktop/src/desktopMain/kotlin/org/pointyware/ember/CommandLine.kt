package org.pointyware.ember

/**
 */
interface Command {
    fun matches(input: String): Boolean
    fun accept(args: Iterator<String>)
}

abstract class LenientCommand(val name: String): Command {
    override fun matches(input: String): Boolean {
        return name.uppercase() == input.uppercase()
    }
}

inline fun lenientCommand(name: String, crossinline acceptor: (Iterator<String>)->Unit) = object: LenientCommand(name) {
    override fun accept(args: Iterator<String>) {
        acceptor(args)
    }
}
