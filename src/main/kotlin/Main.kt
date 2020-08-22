



fun main() {

    //---------------
   val term = """
        some.email@gmail.com
        1254pepepe@yahoo.edu
        corpoService@ivm.net
    """.trimIndent()

    val find = RegEx()
            .anyAtoZLetter().oneOrMore()
            .letter("@")
            .anyAtoZLetter().oneOrMore()
            .literal(".")
            .group(RegEx("com").or().letter("edu").or().letter("net"))
            .printReg()
            .findAll(term)
    println(find)
//    [a-zA-Z]+@[a-zA-Z]+\.(com|edu|net)
//    [email@gmail.com, pepepe@yahoo.edu, corpoService@ivm.net]
}

/**
 * build a regexp that find elements between to values
 */
class RegEx(value: String="" ){

    constructor(value2: RegEx) : this(value2.regExp )

   private var regExp=""

   private val DIGITS="0-9"

     companion object {
        @JvmStatic   val LETTERS_LOWER_CASE="a-z"
         @JvmStatic  val LETTERS_UPPER_CASE="A-Z"

         fun digit(): RegEx {
             return RegEx("""\d""")
         }

         fun letter(letter:String): RegEx {
             return RegEx(letter)
         }

         fun addToRange(value:RegEx):String {
             return RegEx(value).regExp
         }

         fun setBetween(start: RegEx, inside: RegEx, end: RegEx): RegEx {
             return RegEx(start).chain(inside).chain(end)
         }

         /**
          * for simple values
          */
         fun range(vararg strings: String): RegEx {
             var add = ""
             for (valor in strings)
                 add +=valor

             return RegEx("""[$add]""")
         }

         fun group(value:RegEx): RegEx {
             return RegEx("""(${value.regExp})""")
         }

         fun wordBoundary(value:RegEx): RegEx {
             return RegEx("""\b${value.regExp}\b""")
         }

         fun literal(value: String): RegEx {
             return RegEx("""\$value""")
         }

    }// end static

    init {
        this.regExp = value
    }

    fun chain(value:RegEx): RegEx {
        regExp += value.regExp
        return this
    }

    fun group(value:RegEx): RegEx {
        regExp += """(${value.regExp})"""
        return this
    }

    fun groupNumRef(num: Int): RegEx {
        regExp += """\$num"""
        return this
    }

    fun or(): RegEx {
        regExp += """|"""
        return this
    }

    fun startWith(value:RegEx): RegEx {
        regExp += """^${value.regExp}"""
        return this
    }

    fun endWith(value:RegEx): RegEx {
        regExp += """${value.regExp}$"""
        return this
    }





    /**
     * for more complex values
     */
    fun range(value:RegEx): RegEx {
        regExp += """[${value.regExp}]"""
        return this
    }

    fun anyAtoZLowCase(): RegEx {
        regExp += RegEx().range(RegEx().letter(LETTERS_LOWER_CASE)).regExp
        return this
    }

    fun anyAtoZUpperCase(): RegEx {
        regExp += RegEx().range(RegEx().letter(LETTERS_UPPER_CASE)).regExp
        return this
    }

    fun anyAtoZLetter(): RegEx {
        regExp += RegEx().range(RegEx().letter(LETTERS_LOWER_CASE+LETTERS_UPPER_CASE)).regExp
        return this
    }

    fun anyDigit(): RegEx {
        regExp += RegEx().range(RegEx().letter(DIGITS)).regExp
        return this
    }

    fun literal(value: String): RegEx {
        regExp += """\$value"""
        return this
    }

    fun digit(): RegEx {
        regExp += """\d"""
        return this
    }
    fun space(): RegEx {
        regExp += """\s"""
        return this
    }

    fun notSpace(): RegEx {
        regExp += """\S"""
        return this
    }

    fun letter(letter:String): RegEx {
        regExp += letter
        return this
    }

    fun  letters(): RegEx {
        regExp += """\w"""
        return this
    }

    fun  notLetters(): RegEx {
        regExp += """\W"""
        return this
    }

    fun oneOrMore(): RegEx {
        regExp += """+"""
        return this
    }

    fun repeat(start: Int, end:Int) : RegEx{
        regExp += """{$start,$end}"""
        return this
    }

    fun repeat(number: Int ) : RegEx{
        regExp += """{$number}"""
        return this
    }

    fun behind(value:String): RegEx {
        behind(RegEx().letter(value))
        return this
    }

    fun behind(value:RegEx): RegEx {
        regExp = """(?<=${value.regExp})"""
        return this
    }

    /**
     * for simple values
     */
    fun ahead(value:String): RegEx {
        ahead(RegEx().letter(value))
        return this
    }

    /**
     * for more complex values
     */
    fun ahead(value:RegEx): RegEx {
        regExp += """(?=${value.regExp})"""
        return this
    }

    fun wordBoundary(value:RegEx): RegEx {
        regExp += """\b${value.regExp}\b"""
        return this
    }

    fun searchAny(value:RegEx): RegEx {
        regExp += """(?:${value.regExp})"""
        return this
    }

    fun searchAnyNot(value:RegEx): RegEx {
        regExp += """(?!${value.regExp})"""
        return this
    }

    fun setBetween(start: RegEx, inside: RegEx, end: RegEx): RegEx {
        regExp += RegEx(start).chain(inside).chain(end).regExp
        return this
    }

    fun searchBetween(star: RegEx, end: RegEx): RegEx {
        regExp = behind(star)
            .anyLetter().optionalOrMore()
            .ahead(end).regExp
        return this
    }

    fun find(term: String):String {
        val value = regExp.toRegex(RegexOption.MULTILINE).find(term)!!.value
        return value
    }

    fun findAll(term: String): List<String> {
        val value = regExp.toRegex(RegexOption.MULTILINE).findAll(term).map { it.value }.toList()
        return value
    }

    fun buildRegExp(): Regex {
        return regExp.toRegex(RegexOption.MULTILINE)
    }

    fun printReg() : RegEx {
        println(regExp)
        return this
    }

    fun optional(): RegEx {
        regExp += """?"""
        return this
    }

    fun optionalOrMore(): RegEx {
        regExp += """*"""
        return this
    }

    fun anyLetter(): RegEx {
        regExp += """."""
        return this
    }


}
