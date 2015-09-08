import scala.util.parsing.combinator.RegexParsers

sealed abstract class ExpressionSymbol
case class Const(value: Int) extends ExpressionSymbol
case object X extends ExpressionSymbol
case class Add(x: ExpressionSymbol, y: ExpressionSymbol) extends ExpressionSymbol
case class Mul(x: ExpressionSymbol, y: ExpressionSymbol) extends ExpressionSymbol
case class Div(x: ExpressionSymbol, y: ExpressionSymbol) extends ExpressionSymbol
case class Pow(x: ExpressionSymbol, y: ExpressionSymbol) extends ExpressionSymbol

object ArithmaticExpressionParser extends RegexParsers {
  def expression = operationAdd
  def operationAdd: Parser[ExpressionSymbol] = operation ~ rep("+" ~ operation) ^^ {
    case op ~ list => list.foldLeft(op) {
      case (x, "+" ~ y) => Add(x, y)
    }
  }
  def operationMult: Parser[ExpressionSymbol] = operand ~ rep("*" ~ operand | "^" ~ operand | "/" ~ operand) ^^ {
    case op ~ list => list.foldLeft(op) {
      case (x, "*" ~ y) => Mul(x, y)
      case (x, "^" ~ y) => Pow(x, y)
      case (x, "/" ~ y) => Div(x, y)
    }
  }

  def operand: Parser[ExpressionSymbol] = constant | variable
  def operation: Parser[ExpressionSymbol] = operationMult
  def variable: Parser[ExpressionSymbol] = "x" ^^ { _ => X }
  def constant: Parser[ExpressionSymbol] = """-?\d+""".r ^^ { s => Const(s.toInt) }

  def apply(input: String): Option[ExpressionSymbol] = parseAll(expression, input) match {
    case Success(result, _) => Some(result)
    case NoSuccess(_, _) => None
  }
}

object ArithmaticExpressionsTest {
  def main(args: Array[String]): Unit = {
    println( ArithmaticExpressionParser.apply("2 + 3 * 5") )
    println( ArithmaticExpressionParser.apply("3 ^ 2") )
    println( ArithmaticExpressionParser.apply("3 ^ 2  / 4 ") )
    println( ArithmaticExpressionParser.apply("2 + 3 * 5 * 10") )
    println( ArithmaticExpressionParser.apply("2 + 3 * 5 * 10 * x") )
  }
}
