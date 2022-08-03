def product1(f: Int => Int)(a: Int, b: Int): Int =
  if a > b then 1 else f(a) * product1(f)(a + 1, b)

product1(x => x * x)(1, 5)

def fact(n: Int) = product1(x => x)(1, n)

fact(5)

def mapReduce(f: Int => Int, combine: ((Int, Int) => Int), zero: Int)(
    a: Int,
    b: Int
): Int =
  def recur(a: Int): Int =
    if a > b then zero
    else combine(f(a), recur(a + 1))
  recur(a)

def sum(f: Int => Int) = mapReduce(f, _ + _, 0)

def product(f: Int => Int) = mapReduce(f, _ * _, 1)

sum(fact)(1, 5)
product(identity)(1, 6)

def f(a: String)(b: Int)(c: Boolean): String =
  "(" + a + ", " + b + ", " + c + ")"

val partialApplication1 = f("Scala")

val partialApplication2 = partialApplication1(42)

val partial3 = partialApplication2(true)

val tolerance = 0.001
def abs(x: Double) = if x < 0 then -x else x

def isCloseEnough(x: Double, y: Double) =
  abs((x - y) / x) < tolerance

def fixedPoint(f: Double => Double)(firstGuess: Double): Double =
  def iterate(guess: Double): Double =
    val next = f(guess)
    if isCloseEnough(guess, next) then next
    else iterate(next)
  iterate(firstGuess)

def averageDamp(f: Double => Double)(x: Double) =
  (x + f(x)) / 2

def sqrtInitial(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)
def sqrt(x: Double) = fixedPoint(averageDamp(y => x / y))(1.0)

class Rational(x: Int, y: Int):
  private def gcd(a: Int, b: Int): Int =
    if b == 0 then a else gcd(b, a % b)
  private val g = gcd(x.abs, y.abs)
  val numer = x / g
  val denom = y / g
end Rational
