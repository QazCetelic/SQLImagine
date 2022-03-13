package sqli.parser

enum class Type {
    Str,
    Num,
    Dec,
    Date,
    Bool;

    companion object {
        /**
         * @return Wheter the given type as string is a valid type
         */
        operator fun contains(str: String): Boolean {
            return values().any { it.name.equals(str, true) }
        }

        /**
         * @param str The found type as a string
         * @return The type as a [Type]
         */
        fun fromString(str: String): Type {
            return values().find { it.name.equals(str, true) } ?: throw IllegalArgumentException("Unknown type: $str")
        }
    }
}