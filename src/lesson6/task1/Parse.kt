@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import sun.invoke.empty.Empty
import java.lang.IllegalArgumentException
import java.lang.StringBuilder


/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months: List<String> = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа",
        "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String {
    try {
        val date = str.split(" ")
        if (date.size != 3)
            return ""
        val month = months.indexOf(date[1]) + 1
        if (month !in 1..12)
            return ""
        if (date[2].toInt() < 0)
            return ""
        if (date[0].toInt() > daysInMonth(month, date[2].toInt()))
            return ""
        if (date[0].toInt() <= 0)
            return ""
        return String.format("%02d.%02d.%d", date[0].toInt(), month, date[2].toInt())
    }
    catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    try {
        if (Regex("""\d{2}\.\d{2}\.\d+""").matches(digital)) {
            var date = digital.split(".")
            var month = months.elementAt(date[1].toInt() - 1)
            if (date[0].toInt() > daysInMonth(date[1].toInt(), date[2].toInt()))
                return ""
            return String.format("%d %s %s", date[0].toInt(), month, date[2])
        }
        return ""
    }
    catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String = if (
        Regex("""\+?\s*(\([\d+(\-*)|(\s*)]\))*\s*([\d+(\-+)|(\s*)])+""")
                .matches(phone))
        Regex("""(\s)|([\-])|(\()|(\))""").replace(phone, "")
    else
        ""

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun getAllJumps(jumps: String): String = Regex("""(-)|(\s)|(%)""")
        .replace(jumps, " ").replace(Regex("""\s+"""), " ")

fun bestLongJump(jumps: String): Int {
    return try {
        var listOfJumps = getAllJumps(jumps).split(" ")
        var max = -1
        for (i in 0 until listOfJumps.size) {
            if (listOfJumps.elementAt(i) != "") {
                val element = listOfJumps.elementAt(i).toInt()
                if (max < element)
                    max = element
            }
        }
        max
    } catch (e: Exception) {
        -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */

fun getJumps(str: String): List<String> {
    val listOfSpec = listOf<Char>(' ', '%', '-', '+')
    var list = mutableListOf<String>()
    var i = 0
    while(i < str.length) {
        val sb = StringBuilder()
        while(str[i] != ' ') {
            sb.append(str[i].toString())
            i++
        }
        while(i < str.length && listOfSpec.contains(str[i])) {
            if (str[i] == '+')
                list.add(sb.toString())
            i++
        }
    }
    return list
}

fun bestHighJump(jumps: String): Int = TODO()
//    return if(Regex("""(\d+(\s*\+*%*-*)*\s*)+""").matches(jumps)) {
////        var max = -1
////        val list = getJumps(jumps)
////        for (i in list) {
////            val element = i.toInt()
////            if (max < element)
////                max = element
////        }
//        var max = 1
//        max
//    } else
//        -1
//}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val list = str.toLowerCase().split(" ")
    var index = 0;
    for (i in 0 until  list.size - 1) {
        val element1 = list.elementAt(i)
        val element2 = list.elementAt(i + 1)
        if(element1.compareTo(element2) == 0)
            return index
        index += element1.length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
        if(Regex("""(^\d+$)|(((\d+\s\+\s)|(\d+\s-\s))+\d+$)""").matches(expression)) {
            Regex("""\s*""").replace(expression, " ")
            var listOfElements = expression.split(" ")
            var sum = listOfElements.elementAt(0).toInt()
            for (i in 2 until listOfElements.size step 2) {
                if(listOfElements.elementAt(i-1) == "-")
                    sum -= listOfElements.elementAt(i).toInt()
                else
                    sum += listOfElements.elementAt(i).toInt()
            }
            return sum
        }
        else
            throw IllegalArgumentException()
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    try {
        var list = Regex(""";""").replace(description, "").split(" ")
        var max = -1.0
        var result = ""
        for (i in 0 until list.size - 1 step 2) {
            val element = list.elementAt(i + 1).toDouble()
            if(element > max) {
                max = element
                result = list.elementAt(i)
            }

        }
        return result
}
    catch (e: Exception) {
    return ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */

val romanList = listOf<String>("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V",  "IV", "I")
val decimalList = listOf<Int>(1000, 900 ,500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)

fun fromRoman(roman: String): Int {
    return if (Regex("""M*(CM)?(D|(CD))?C{0,3}(XC)?(L|(XL))?X{0,3}(IX)?(V|(IV))?I{0,3}""").matches(roman) &&
            !roman.isEmpty()) {
        var sum = 0
        var i = 0
        while (i < roman.length - 1) {
            if(romanList.indexOf(roman[i].toString()) > romanList.indexOf(roman[i + 1].toString()))
                sum -= decimalList.elementAt(romanList.indexOf(roman[i].toString()))
            else
                sum += decimalList.elementAt(romanList.indexOf(roman[i].toString()))
            i++
        }
        sum += decimalList.elementAt(romanList.indexOf(roman.last().toString()))
        sum
    } else
        -1
    return -1
}
/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
