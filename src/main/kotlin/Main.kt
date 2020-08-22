



fun main() {

    //---------------
    val term = """
        123-456-7890
        123 456 7890
        (123) 456 7890
    """.trimIndent()
    val separator = RegEx().letter(" -").optional()
    val areaCodeOpen = RegEx().literal("(").optional()
    val areaCodeClose = RegEx().literal(")").optional()
    val ThreeDigits = RegEx().digit().repeat(3)
    val fourDigits = RegEx().digit().repeat(4)

    val find =
            RegEx(RegEx().setBetween(areaCodeOpen,ThreeDigits,areaCodeClose))
            .range(separator)
            .chain(ThreeDigits)
            .range(separator)
            .chain(fourDigits)
            .printReg()
            .findAll(term)
    println(find)
//       \d{3}[ -?]\d{3}[ -?]\d{4}
 //   \(?\d{3}\)?[ -?]\d{3}[ -?]\d{4}
//    [123-456-7890, 123 456 7890]
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
        regExp += range(RegEx().letter(LETTERS_LOWER_CASE)).regExp
        return this
    }

    fun anyAtoZUpperCase(): RegEx {
        regExp += range(RegEx().letter(LETTERS_UPPER_CASE)).regExp
        return this
    }

    fun anyAtoZLetter(): RegEx {
        regExp += range(RegEx().letter(LETTERS_LOWER_CASE+LETTERS_UPPER_CASE)).regExp
        return this
    }

    fun anyDigit(): RegEx {
        regExp += range(RegEx().letter(DIGITS)).regExp
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

    fun word(): RegEx {
        letters()
        regExp += """+"""
        return this
    }

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


//    val temStr = "implementing('this is what i want');"
//    val regExp = """(?<=').*(?=')""".toRegex(RegexOption.MULTILINE)
//    regExp.findAll(temStr).map { it.value }.forEach { println(it) }
//
//    val value = regExp.find(temStr)!!.value
//    println(value)

//    val find = regEx().from("'").to("'").find(temStr)
//    println(find)
//---------------
//    val temStr2 = "9991233 Main St."
//    val find = regEx().digit()
//            //.repeat(1, 5)
//            .repeat(5)
//            .space().word().space().word().explicit(".").find(temStr2)
//    println(find)
//---------------
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .group(RegEx().letter("t").or().letter("T"))
            .letter("he")
            .printReg()
            .findAll(temStr2)
            //.forEach { println(it) }
    println(find)*/
//---------------
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .group(RegEx().letter("t").or().letter("e").or().letter("r"))
            .repeat(2, 3)
            .printReg()
            .findAll(temStr2)
     println(find)
    (t|e|r){2,3}
    [tre, et]*/
//---------------
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .endWith(RegEx().literal("."))
            .printReg()
            .findAll(temStr2)
    println(find)*/
//---------------
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .behind(RegEx().range(RegEx().letter("tT")).letter("he"))
            .anyLetter()
            .printReg()
            .findAll(temStr2)
    println(find)
    (?<=[tT]he).
    [ ,  ]*/

//--------------- look behind
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            //.behind(RegEx().range(RegEx().letter("tT")).letter("he"))
            .behind(RegEx().range("tT").letter("he")) // simple version
            .anyLetter()
            .printReg()
            .findAll(temStr2)
    println(find)
//    (?<=[tT]he).
//    [ ,  ]*/

/*    //--------------- look behind
    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .anyLetter()
            .ahead(RegEx().letter("at")) // more complex values
            //.ahead("at") // for simple values
            .printReg()
            .findAll(temStr2)
    println(find)
            .(?=at)
    [f, c, e]*/
//--------------- look ahead
/*    val temStr2 = """
        the fat cat ran down the street.
        it was searching for a mouse to eat.
    """.trimIndent()
    val find = RegEx()
            .anyLetter()
            .ahead("at")
            .printReg()
            .findAll(temStr2)
    println(find)*/
//---------------
/*
    val temStr2 = """
        123-456-7890
        123 456 7890
    """.trimIndent()
    val find = RegEx()
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(4)
            .printReg()
            .findAll(temStr2)
    println(find)
//    \d{3}[ -?]\d{3}[ -?]\d{4}
//    [123-456-7890, 123 456 7890]
*/