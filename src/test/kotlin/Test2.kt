import com.gomezrondon.RegEx
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test;

class Test2 {

    @Test
    @DisplayName("find beginning and end of brackets")
    fun findBrackets() {

        val strTest = """plugins { id 'org.jetbrains.kotlin.jvm' version '1.5.20' id 'application' } group = 'com.gomezrondon' version = '1.0-SNAPSHOT' repositories { mavenCentral() maven { url 'https://alphacephei.com/maven/' } } dependencies { implementation group: 'net.java.dev.jna', name: 'jna', version: '5.7.0' implementation group: 'com.alphacephei', name: 'vosk', version: '0.3.30+' implementation group: 'org.jetbrains.kotlinx', name: 'kotlinx-coroutines-core', version: '1.3.5' } test { useJUnitPlatform() } jar { manifest { attributes 'Main-Class': 'com.gomezrondon.RegEx' } // This line of code recursively collects and copies all of a project's files // and adds them to the JAR itself. One can extend this task, to skip certain // files or particular types at will from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } } }
"""
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
    fun getIndexForNER() { // (52, 58, "PLUGVERSION"), (61, 97, "PLUGID"),  (0, 7,"PLUGIN"),(10, 48,"PLUGID"),(49, 75,"PLUGVERSION")
       // val term =" I ordered this from ShopClues" //length = 29
//         val term =" Who is Shaka Khan?" //length = 29 //(7, 17, "PERSON") (0, 7, "PERSON")
//        val term ="_plugins { id 'org.jetbrains.kotlin.jvm' version '1.4.21' id 'io.spring.dependency-management' version '1.0.10.RELEASE'  } " //length = 29 //(7, 17, "PERSON")
        val line ="plugins { id ' org.jetbrains.kotlin.jvm ' version ' 1.4.21 ' id ' io.spring.dependency-management ' version ' 1.0.10.RELEASE '  }" //length = 29 //(7, 17, "PERSON")

        val term = listOf<String>("plugins","id ' org.jetbrains.kotlin.jvm '","version ' 1.4.21 '","id ' io.spring.dependency-management '","version ' 1.0.10.RELEASE '")
//(0, 7,"PLUGIN"),(10, 41,"PLUGID"),(42, 60,"PLUGVERSION"),(61, 99,"PLUGID"),(100, 126,"PLUGVERSION")
        term.forEach {
            val indexOf = line.indexOf(it)
            if (indexOf >= 0) {
                val ends = indexOf + it.length
                println(it)
                println(""",($indexOf, $ends,"X")""")
            }

        }

//        term.mapIndexed { index, c -> println("i: $index letter: $c") }

        println("length: ${line.length}")
    }

    @Test
    fun findRemoveSpaces() {
        val temStr ="""
plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.5.20'
    id 'application'
    id 'org.springframework.boot' version '2.5.2'
}

plugins {
    id 'org.jetbrains.kotlin.jvm' version '1.4.0'
}

plugins {
    id 'org.springframework.boot' version '2.4.3'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
    id 'org.jetbrains.kotlin.jvm' version '1.4.30'
}

plugins {
	id 'org.springframework.boot' version '2.5.0'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
	id 'org.springframework.experimental.aot' version '0.10.0-SNAPSHOT'
}

plugins {
    id 'java'
}

plugins {
	id 'org.springframework.boot' version '2.5.0-SNAPSHOT'
	id 'io.spring.dependency-management' version '1.0.10.RELEASE'
	id 'java'
	id 'org.jetbrains.kotlin.jvm' version '1.4.21'
}

plugins {
    id 'java'
    id 'io.quarkus'
}

plugins {
    id 'java'
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