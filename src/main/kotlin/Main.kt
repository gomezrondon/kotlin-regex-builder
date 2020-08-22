



fun main() {

    //---------------
   val term = """
        some.email@gmail.com
        1254pepepe@yahoo.edu
        corpoService@ivm.net
    """.trimIndent()

    val find = RegEx()
            .anyAtoZLetter().moreThanOne()
            .letter("@")
            .anyAtoZLetter().moreThanOne()
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
   private val bucarTodo= ".*"
   private val LETTERS_LOWER_CASE="a-z"
   private val LETTERS_UPPER_CASE="A-Z"
   private val DIGITS="0-9"

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
     * for simple values
     */
    fun range(value:String): RegEx {
        range(RegEx().letter(value))
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

//    fun word(): RegEx {
//        letters()
//        regExp += """+"""
//        return this
//    }

    fun moreThanOne(): RegEx {
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

    fun setBetween(start: RegEx, inside: RegEx, end: RegEx): RegEx {
        regExp += RegEx(start).chain(inside).chain(end).regExp
        return this
    }

/*    fun between(from: String, to: String ): RegEx {
        regExp = """(?<=$from)$bucarTodo"""
        regExp += """(?=$to)"""
        return this
    }*/


    fun find(term: String):String {
        val value = regExp.toRegex(RegexOption.MULTILINE).find(term)!!.value
        return value
    }

    //.findAll(temStr).map { it.value }

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
