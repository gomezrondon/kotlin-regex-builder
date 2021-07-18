import com.gomezrondon.RegEx
import com.gomezrondon.RegEx.static.LETTERS_LOWER_CASE
import com.gomezrondon.RegEx.static.LETTERS_UPPER_CASE
import com.gomezrondon.RegEx.static.addToRange
import com.gomezrondon.RegEx.static.anyLetter
import com.gomezrondon.RegEx.static.digit
import com.gomezrondon.RegEx.static.group
import com.gomezrondon.RegEx.static.letter
import com.gomezrondon.RegEx.static.literal
import com.gomezrondon.RegEx.static.range
import com.gomezrondon.RegEx.static.setBetween
import com.gomezrondon.RegEx.static.wordBoundary
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test;

class Test2 {

    @Test
    @DisplayName("find beginning and end of brackets")
    fun findBrackets() {

        val strTest = """plugins { id 'org.jetbrains.kotlin.jvm' version '1.5.20' id 'application' } group = 'com.gomezrondon' version = '1.0-SNAPSHOT' repositories { mavenCentral() maven { url 'https://alphacephei.com/maven/' } } dependencies { implementation group: 'net.java.dev.jna', name: 'jna', version: '5.7.0' implementation group: 'com.alphacephei', name: 'vosk', version: '0.3.30+' implementation group: 'org.jetbrains.kotlinx', name: 'kotlinx-coroutines-core', version: '1.3.5' } """

        var count = 0

        for (char in strTest) {

            if (char.equals('{')) {
                count++
            }

            print(char)
            if (count > 0 && char.equals('}')) {
                count--

                if (count == 0) {
                    print("#9#")
                }

            }


        }



    }


    @Test
    fun findRemoveSpaces() {
        val temStr ="""
            plugins {
                id 'org.jetbrains.kotlin.jvm' version '1.5.20'
                id 'application'
            }

            group = 'com.gomezrondon'
            version = '1.0-SNAPSHOT'

            repositories {
                mavenCentral()
                maven {
                    url 'https://alphacephei.com/maven/'
                }
            }
            dependencies {
                implementation group: 'net.java.dev.jna', name: 'jna', version: '5.7.0'
                implementation group: 'com.alphacephei', name: 'vosk', version: '0.3.30+'
                implementation group: 'org.jetbrains.kotlinx', name: 'kotlinx-coroutines-core', version: '1.3.5'

            }
        """.trimIndent()

        val buildRegExp = RegEx()
            .group(RegEx().space().oneOrMore())
            .buildRegExp()

        val find = RegEx(buildRegExp.toString())
            .replaceAll(temStr," ")

        println(find)
    }


}