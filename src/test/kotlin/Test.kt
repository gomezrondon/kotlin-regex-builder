

import RegEx.Companion.addToRange
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test;

class Test {

    val temStr = """
        the fat cat ran down The street.
        it was searching for a mouse to eat.
    """.trimIndent()

    val temStr2 = """
        123-456-7890
        123 456 7890
        (123) 456 7890
    """.trimIndent()

    @Test
    fun testingWordBoundry() {
        val temStr ="""paris in the the spring"""

        val buildRegExp = RegEx()
            .wordBoundary(RegEx().group(RegEx().letters().oneOrMore()).space().groupNumRef(1)) // (group1)(group2)  \1\2
            .buildRegExp()

        val find = RegEx(buildRegExp.toString())
            .findAll(temStr)

        assertEquals("""\b(\w+)\s\1\b""", buildRegExp.toString())
        assertEquals("""[the the]""", find.toString())
    }


    @Test
    fun getFomBeginingUptoAcharacter() {
//        Desde el inicio hasta la N ocurrencia de un caracters
//        ^(?:.*?\\){3}

        val temStr ="""C:\temp\test\kotlin-js\something\more"""

        val buildRegExp = RegEx()
            .startWith(RegEx().searchAny(RegEx().anyLetter().optionalOrMore().optional().literal("""\""")))
            .repeat(3)
            .buildRegExp()

        val find = RegEx(buildRegExp.toString())
            .findAll(temStr)

        assertEquals("""^(?:.*?\\){3}""", buildRegExp.toString())
        assertEquals("""[C:\temp\test\]""", find.toString())

    }

    @Test
    fun validateURLs() {
        val temStr = """
            https://www.google.com
            http://coreyms.com
            https://youtube.com
        """.trimIndent()

        val buildRegExp = RegEx()
            .letter("http").letter("s").optional()
            .letter("://")
            .group(RegEx("www").literal(".")).optional()
            .letters().oneOrMore()
            .literal(".")
            .letters().oneOrMore()
            .buildRegExp()

        val find = RegEx(buildRegExp.toString())
            .findAll(temStr)

        assertEquals("""https?://(www\.)?\w+\.\w+""", buildRegExp.toString())
        assertEquals("""[https://www.google.com, http://coreyms.com, https://youtube.com]""", find.toString())

    }


    @Test
    fun testChainExpression() {
        val separator = RegEx().letter(" -").optional()
        val areaCodeOpen = RegEx().literal("(").optional()
        val areaCodeClose = RegEx().literal(")").optional()
        val ThreeDigits = RegEx().digit().repeat(3)
        val fourDigits = RegEx().digit().repeat(4)

        val buildRegExp =
            RegEx(RegEx().setBetween(areaCodeOpen,ThreeDigits,areaCodeClose))
            .range(separator)
            .chain(ThreeDigits)
            .range(separator)
            .chain(fourDigits)
            .buildRegExp()

        val find =
            RegEx(RegEx().setBetween(areaCodeOpen,ThreeDigits,areaCodeClose))
            .range(separator)
            .chain(ThreeDigits)
            .range(separator)
            .chain(fourDigits)
            .findAll(temStr2)

        assertEquals("""\(?\d{3}\)?[ -?]\d{3}[ -?]\d{4}""", buildRegExp.toString())
        assertEquals("""[123-456-7890, 123 456 7890, (123) 456 7890]""", find.toString())
    }

    @Test
    fun validatingTelephonesNumbers() {

        val buildRegExp = RegEx()
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(4)
            .buildRegExp()

        val find = RegEx()
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(4)
            .findAll(temStr2)

        assertEquals("""\d{3}[ -?]\d{3}[ -?]\d{4}""", buildRegExp.toString())
        assertEquals("""[123-456-7890, 123 456 7890]""", find.toString())
    }


    @Test
    fun testLookAhead_2() {
        // match any word that ends with a coma
        val temStr = """
            "Man is his own star; and the soul that can
            Render an honest and a perfect man,
            Commands all light, all influence, all fate;
            Nothing to him falls early or too late.
            Our acts our angels are, or good or ill,
            Our fatal shadows that walk by us still."
               Epilogue to Beaumont and Fletcher's Honest Man's Fortune
            Cast the bantling on the rocks,
            Suckle him with the she-wolf's teat"
            Wintered with the hawk and fox,
        """.trimIndent()


        val buildRegExp = RegEx()
            .wordBoundary(RegEx().range(RegEx.LETTERS_UPPER_CASE + RegEx.LETTERS_LOWER_CASE + addToRange(RegEx("'"))).oneOrMore())
            .ahead(",")
            .buildRegExp()

        val find = RegEx(buildRegExp.toString())
            .findAll(temStr)

        assertEquals("""\b[A-Za-z']+\b(?=,)""", buildRegExp.toString())
        assertEquals("""[man, light, influence, are, ill, rocks, fox]""", find.toString())
    }

    @Test
    fun testLookAhead() {

        val buildRegExp = RegEx()
            .anyLetter()
            .ahead("at")
            .buildRegExp()

        val find = RegEx()
            .anyLetter()
            .ahead("at")
            .findAll(temStr)

        assertEquals(""".(?=at)""", buildRegExp.toString())
        assertEquals("""[f, c, e]""", find.toString())
    }

    @Test
    fun testLookBehind() {

        val buildRegExp = RegEx()
            .behind(RegEx().range("tT").letter("he"))
            .anyLetter()
            .buildRegExp()

        val find = RegEx()
            .behind(RegEx().range("tT").letter("he"))
            .anyLetter()
            .findAll(temStr)

        assertEquals("""(?<=[tT]he).""", buildRegExp.toString())
        assertEquals("""[ ,  ]""", find.toString())
    }


    @Test
    fun findLastPeriodAtTheEnd() {

        val buildRegExp = RegEx()
            .endWith(RegEx().literal("."))
            .buildRegExp()

        val find = RegEx()
            .endWith(RegEx().literal("."))
            .findAll(temStr)

        assertEquals("""\.$""", buildRegExp.toString())
        assertEquals("""[., .]""", find.toString())
    }

    @Test
    fun findWordTheThatRepeats() {

        val buildRegExp = RegEx()
            .group(RegEx("t").or().letter("e").or().letter("r"))
            .repeat(2,3)
            .buildRegExp()

        val find = RegEx()
            .group(RegEx("t").or().letter("e").or().letter("r"))
            .repeat(2,3)
            .findAll(temStr)

        assertEquals("""(t|e|r){2,3}""", buildRegExp.toString())
        assertEquals("""[tre, et]""", find.toString())
    }

    @Test
    fun findWordThe() {

          val buildRegExp = RegEx()
            .group(RegEx("t").or().letter("T"))
            .letter("he")
            .buildRegExp()

        val find = RegEx()
            .group(RegEx("t").or().letter("T"))
            .letter("he")
            .findAll(temStr)

        assertEquals("""(t|T)he""", buildRegExp.toString())
        assertEquals("""[the, The]""", find.toString())
    }


    @Test
    fun validateAddress() {
        val temStr = "9991233 Main St."

        val  buildRegExp =  RegEx()
            .digit().repeat(1, 5)
            .space()
            .letters().oneOrMore()
            .space()
            .letters().oneOrMore()
            .literal(".")
            .buildRegExp()

        val find = RegEx()
            .digit().repeat(1, 5)
            .space()
            .letters().oneOrMore()
            .space()
            .letters().oneOrMore()
            .literal(".")
            .find(temStr)


        assertEquals("""\d{1,5}\s\w+\s\w+\.""", buildRegExp.toString())
        assertEquals("""91233 Main St.""", find)

        val patron= RegEx().space().letters().oneOrMore()

        val find2 = RegEx()
            .digit().repeat(1, 5)
            .chain(patron)
            .chain(patron)
            .literal(".")
            .find(temStr)

        assertEquals("""91233 Main St.""", find)
    }


    @Test
    fun getValueBetweenParenthesis_2() {
        val temStr = "implementing('this is what i want');"

        val  buildRegExp =  RegEx()
            .searchBetween(RegEx("'"),RegEx("'"))
            .buildRegExp()

        val find = RegEx()
            .searchBetween(RegEx("'"),RegEx("'"))
            .find(temStr)


        assertEquals("""(?<=').*(?=')""", buildRegExp.toString())
        assertEquals("""this is what i want""", find)
    }

    @Test
    fun getValueBetweenParenthesis() {
      val temStr = "implementing('this is what i want');"

      val  buildRegExp =  RegEx().behind("'")
            .anyLetter().optionalOrMore()
            .ahead("'")
          .buildRegExp()

        val find = RegEx().behind("'")
            .anyLetter().optionalOrMore()
            .ahead("'")
            .find(temStr)


        assertEquals("""(?<=').*(?=')""", buildRegExp.toString())
        assertEquals("""this is what i want""", find)
    }


    @Test
    fun emailValidations() {

        val term = """
        some.email@gmail.com
        1254pepepe@yahoo.edu
        corpoService@ivm.net
    """.trimIndent()

        val buildRegExp = RegEx()
            .anyAtoZLetter().oneOrMore()
            .letter("@")
            .anyAtoZLetter().oneOrMore()
            .literal(".")
            .group(RegEx("com").or().letter("edu").or().letter("net"))
            .buildRegExp()

        val find = RegEx()
            .anyAtoZLetter().oneOrMore()
            .letter("@")
            .anyAtoZLetter().oneOrMore()
            .literal(".")
            .group(RegEx("com").or().letter("edu").or().letter("net"))
            //.printReg()
            .findAll(term)

        assertEquals("""[a-zA-Z]+@[a-zA-Z]+\.(com|edu|net)""", buildRegExp.toString())
        assertEquals(3, find.size)
    }

}