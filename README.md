#kotlin-regex-builder
## a Regular Expression builder using Kotlin

### example: 
we want to match this phone numbers
* 123-456-7890
* 123 456 7890

the Regular expresion would be something like:

\d{3}[ -?]\d{3}[ -?]\d{4}

with ".buildRegExp().toString()" you get the regExp in string so you can validate or use in other program.
 ```
val buildRegExp = RegEx()
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(4)
            .buildRegExp().toString()
 ```

with ".findAll(temStr)" you pass the text to search and get a list of matching results.

 ```
val find = RegEx()
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(3)
            .range(RegEx().letter(" -").optional())
            .digit().repeat(4)
            .findAll(temStr) // text to search
 ```

### For more example see the unit tests
