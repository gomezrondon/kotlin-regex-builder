
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test;

class Test {


    @Test
    fun findWordThe() {

        val temStr = """
        the fat cat ran down The street.
        it was searching for a mouse to eat.
    """.trimIndent()

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
            .letters().moreThanOne()
            .space()
            .letters().moreThanOne()
            .literal(".")
            .buildRegExp()

        val find = RegEx()
            .digit().repeat(1, 5)
            .space()
            .letters().moreThanOne()
            .space()
            .letters().moreThanOne()
            .literal(".")
            .find(temStr)


        assertEquals("""\d{1,5}\s\w+\s\w+\.""", buildRegExp.toString())
        assertEquals("""91233 Main St.""", find)

        val patron= RegEx().space().letters().moreThanOne()

        val find2 = RegEx()
            .digit().repeat(1, 5)
            .chain(patron)
            .chain(patron)
            .literal(".")
            .find(temStr)

        assertEquals("""91233 Main St.""", find)
    }


    @Test
    fun getValueBetweenParentesis() {
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
            .anyAtoZLetter().moreThanOne()
            .letter("@")
            .anyAtoZLetter().moreThanOne()
            .literal(".")
            .group(RegEx("com").or().letter("edu").or().letter("net"))
            .buildRegExp()

        val find = RegEx()
            .anyAtoZLetter().moreThanOne()
            .letter("@")
            .anyAtoZLetter().moreThanOne()
            .literal(".")
            .group(RegEx("com").or().letter("edu").or().letter("net"))
            //.printReg()
            .findAll(term)

        assertEquals("""[a-zA-Z]+@[a-zA-Z]+\.(com|edu|net)""", buildRegExp.toString())
        assertEquals(3, find.size)
    }

}