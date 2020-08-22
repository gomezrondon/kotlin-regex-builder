fun main() {
    val temStr = "implementing('this is what i want');"
    val regExp = """(?<=').*(?=')""".toRegex(RegexOption.MULTILINE)
//    regExp.findAll(temStr).map { it.value }.forEach { println(it) }
//
//    val value = regExp.find(temStr)!!.value
//    println(value)

//    val find = regEx().from("'").to("'").find(temStr)
//    println(find)
    //---------------
    val temStr2 = "9991233 Main St."
    val find = regEx().digit()
            //.repeat(1, 5)
            .repeat(5)
            .space().word().space().word().explicit(".").find(temStr2)
    println(find)
}

/**
 * build a regexp that find elements between to values
 */
class regEx(){

   private var regExp=""
   private val bucarTodo= ".*"

    fun explicit(value: String): regEx {
        regExp += """\$value"""
        return this
    }

    fun digit(): regEx {
        regExp += """\d"""
        return this
    }
    fun space(): regEx {
        regExp += """\s"""
        return this
    }

    fun  char(): regEx {
        regExp += """\w"""
        return this
    }

    fun word(): regEx {
        char()
        regExp += """+"""
        return this
    }

    fun repeat(start: Int, end:Int) : regEx{
        regExp += """{$start,$end}"""
        return this
    }

    fun repeat(number: Int ) : regEx{
        regExp += """{$number}"""
        return this
    }

    fun from(valor: String): regEx {
        regExp = """(?<=$valor)$bucarTodo"""
        return this
    }

    fun to(valor: String): regEx {
        regExp += """(?=$valor)"""
        return this
    }

    fun find(term: String):String {
        val value = regExp.toRegex(RegexOption.MULTILINE).find(term)!!.value
        return value
    }

    fun buildRegExp(): Regex {
        return regExp.toRegex(RegexOption.MULTILINE)
    }

    fun printReg() {
        println(regExp)
    }

}
