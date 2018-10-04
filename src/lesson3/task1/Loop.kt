@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import kotlinx.html.I
import lesson1.task1.sqr
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int
{
    var ctr = 0
    var number = n
    return if (n == 0)
        1
    else{
        while (number != 0){
            ctr++
            number /= 10
        }
        ctr

    }
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var fib1 = 1
    var fib2 = 0

    for (m in 2..n)
        if (m % 2 == 0)
            fib2 += fib1
        else
            fib1 += fib2

    return when{
        n % 2 == 0 -> fib2
        else -> fib1
    }
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */


fun gcd(m: Int, n: Int): Int{
    return if (n == 0)
        m
    else
        gcd(n, m % n)
}


fun lcm(m: Int, n: Int): Int{
    return m / gcd(m, n) * n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int{
    var m = 2
    while (n % m != 0)
        m++
    return m
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)


/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean{
    var bool = true
    for (i in 2..maxOf(m,n))
    {
        if (m % i == 0 && n % i == 0)
            bool =  false
    }
    return bool
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean{
    var bool = false
    for (i in 0..sqrt(maxOf(n,m).toDouble()).toInt())
        if (i*i in m..n)
            bool = true

    return bool
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int{
    var num = x
    var ctr = 0
    while (num != 1) {
        if (num % 2 == 0)
            num/=2
        else
            num = num * 3 + 1
        ctr++
    }
    return (ctr)
}

fun pow(p: Int, n: Int): Int {
    var num = 1
    for (i in 0..p)
        num *= n
    return num
}

fun pow(p: Int, n: Double): Double{
    var num = 1.0
    for (i in 0..p)
        num *= n
    return num
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double{
    var num = x
    var sum = x
    var ctr = 3
    while (abs(num) >= eps){
        num *= -((x * x) / (ctr * (ctr - 1)))
        sum += num
        ctr += 2
    }
    return sum
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double{

    var num = 1.0
    var sum = 1.0
    var ctr = 1
    while (abs(num) > eps){
        num *= -((x * x) / (ctr * (ctr + 1)))
        sum += num
        ctr += 2
    }
    return sum
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int{
    var new = 0
    var num = n
    while (num > 0){
        new = new * 10 + num % 10
        num /= 10
   }
    return new
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = (revert(n) == n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean{
    var num = n
    var digit = num % 10
    var bool = false
    for (i in 1..digitNumber(n)-1) {
        num /= 10
        if (digit != num % 10)
            bool = true
    }
    return bool
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int{
    var sum = 0
    var num = 0
    while (n > sum){
        num++
        sum += digitNumber(num*num)
    }
    num *= num
    sum -= n
    for (i in 1..sum )
        num /= 10
    return num % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int{
    var sum = 1
    var fib1 = 1
    var fib2 = 0
    var bool = false
    while (n > sum){
            if (bool){
                fib2 += fib1
                sum += digitNumber(fib2)
                bool = false
            }
            else{
                fib1 += fib2
                sum += digitNumber(fib2)
                bool = true
            }
    }
    var num = fib1
    if (bool)
        num = fib2
    sum -= n
    if (digitNumber(num) != 1)
    for (i in 1..digitNumber(num) - sum)
        num /= 10
    return num % 10
}
