@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson6.task1.decimalList
import java.io.BufferedReader
import java.io.File
import java.lang.StringBuilder
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    var result: MutableMap<String, Int> = mutableMapOf()
    var str = StringBuilder()
    val reader = File(inputName).bufferedReader()
    for (line in reader.readLines())
        str.append(line + "\n")
    for (word in substrings) {
        var index = 0
        var res = 0
        while(str.indexOf(word, index, true) != -1) {
            res++
            index = str.indexOf(word, index, true) + 1
        }
        result[word] = res
    }
    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
val listRus: List<Pair<String, String>> = listOf(
        "ЖЫ" to "ЖИ", "ЖЯ" to "ЖА", "ЖЮ" to "ЖУ", "ЧЫ" to "ЧИ", "ЧЯ" to "ЧА", "ЧЮ" to "ЧУ",
        "ШЫ" to "ШИ", "ШЯ" to "ША", "ШЮ" to "ШУ", "ЩЫ" to "ЩИ", "ЩЯ" to "ЩА", "ЩЮ" to "ЩУ",
        "жЫ" to "жИ", "жЯ" to "жА", "жЮ" to "жУ", "чЫ" to "чИ", "чЯ" to "чА", "чЮ" to "чУ",
        "шЫ" to "шИ", "шЯ" to "шА", "шЮ" to "шУ", "щЫ" to "щИ", "щЯ" to "щА", "щЮ" to "щУ",
        "Жы" to "Жи", "Жя" to "Жа", "Жю" to "Жу", "Чы" to "Чи", "Чя" to "Ча", "Чю" to "Чу",
        "Шы" to "Ши", "Шя" to "Ша", "Шю" to "Шу", "Щы" to "Щи", "Щя" to "Ща", "Щю" to "Щу",
        "жы" to "жи", "жя" to "жа", "жю" to "жу", "чы" to "чи", "чя" to "ча", "чю" to "чу",
        "шы" to "ши", "шя" to "ша", "шю" to "шу", "щы" to "щи", "щя" to "ща", "щю" to "щу")

fun sibilants(inputName: String, outputName: String) {
    var string = File(inputName).readLines() as MutableList<String>
    var writer = File(outputName).printWriter()
    for (i in 0 until string.size) {
        for (j in listRus)
            string[i] = string[i].replace(j.first, j.second)
        writer.println(string[i])
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var list: MutableList<String> = mutableListOf()
    for (i in File(inputName).readLines())
        list.add(i.trim())
    var maxLen = -1
    for (i in list)
        if(maxLen < i.length)
            maxLen = i.length
    for (i in 0 until list.size) {
        var s = ""
        for (j in 0 until (maxLen - list[i].length)/2)
            s+=" "
        list[i] =  s + list[i]
    }
    var writer = File(outputName).printWriter()
    for (i in 0 until list.size)
        writer.println(list[i])
    writer.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
//fun getAllWords(str: String): List<String> {
//    var res = mutableListOf<String>()
//    var i = 0
//    while(i < str.length) {
//        var word = ""
//        while ((str[i] in 'а'..'я' || str[i] in 'А'..'Я' || str[i] in 'a'..'z'
//                        || str[i] in 'A'..'Z' || str[i] == 'Ё' || str[i] == 'ё')
//                && i < str.length) {
//            word += str[i]
//            i++
//        }
//        if(!word.isEmpty())
//            res.add(word.toLowerCase())
//        while (i < str.length && str[i] !in 'а'..'я' && str[i] !in 'А'..'Я' && str[i] !in 'a'..'z'
//                && str[i] !in 'A'..'Z' && str[i] != 'Ё' && str[i] != 'ё')
//            i++
//    }
//    return res
//}

fun top20Words(inputName: String): Map<String, Int> = TODO()
//    var str = ""
//    var resMap = mutableMapOf<String, Int>()
//    for (i in File(inputName).readLines())
//        str += i
//    var listOfWords = getAllWords(str)
//    var setOfWords = listOfWords.toSet()
//    return if (setOfWords.size <= 20) {
//        for (i in setOfWords) {
//            var ctr = 0
//            for(j in listOfWords)
//                if (j == i)
//                    ctr++
//            if(ctr > 1)
//            resMap[i] = ctr
//        }
//        resMap
//    }
//    else
//    {
//        var tempMap = mutableMapOf<String, Int>()
//        for (i in setOfWords) {
//            tempMap[i] = 0
//        }
//        for((key, value) in tempMap) {
//            var ctr = 0
//            for(j in listOfWords)
//                if (j == key)
//                    ctr++
//            tempMap[key] = ctr
//        }
//        for(i in 0..19) {
//            var max = -1
//            var k = ""
//            for((key,value) in tempMap) {
//                if(value > max) {
//                    max = value
//                    k = key
//                }
//            }
//            if(max > 1)
//            resMap[k] = max
//            tempMap.remove(k)
//        }
//        resMap
//    }
//}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    var str = ""
    var map = mutableMapOf<Char, String>()
    for (i in File(inputName).readLines())
        str += i + "\n"
    try {
        val setOfKeys = dictionary.keys
        for (i in setOfKeys) {
            map[i.toLowerCase()] = dictionary.getOrDefault(i, "").toLowerCase()
            map[i.toUpperCase()] = dictionary.getOrDefault(i, "").substring(0, 1).toUpperCase() +
                    dictionary.getOrDefault(i, "").substring(1).toLowerCase()
            if (i in 'a'..'z' || i in 'A'..'Z' || i in 'а'..'я' || i in 'А'..'Я') {
                str = str.replace(i.toString().toLowerCase(),
                        map.getOrDefault(i.toLowerCase(), ""), false)
                str = str.replace(i.toString().toUpperCase(),
                        map.getOrDefault(i.toUpperCase(), ""), false)
            } else
                str = str.replace(i.toString(), map.getOrDefault(i, ""), false)
        }
        File(outputName).printWriter().use {
            it.print(str)
        }
    }
    catch (e: Exception) {
        File(outputName).printWriter().use {
            it.print(str)
        }
    }
}


/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
        var list = File(inputName).readLines() as MutableList
        if (list.isEmpty())
            File(outputName).printWriter().use {
                it.print("")
            }
        list.removeIf { it.length != it.toLowerCase().toSet().size }
        var maxLen = -1
        var size = list.size

        for (i in 0 until size)
            if (list[i].length > maxLen)
                maxLen = list[i].length
    list.removeIf { it.length < maxLen}
        File(outputName).printWriter().use {
            if (list.size > 1) {
                for (i in 0 until list.size - 1)
                    it.print(list[i] + ", ")
                it.print(list.last())
            } else
                it.print(list.last())
        }
    }

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Яблоки
        <ol>
          <li>Красные</li>
          <li>Зелёные</li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

