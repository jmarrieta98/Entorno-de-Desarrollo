import expr_aritmetica
import calculadora
import validador_expr_aritmetica


class Supercalculadora:
    def __init__(self, parser, validador):  # añadido parámetro validador
        self.calc = calculadora.Calculadora()
        self.parser = parser
        self.validador = validador

    def __operar__(self, expr_descompuesta):
        i = None
        res_intermedio = 0
        # intercambiar / y * por el error de la división
        if '*' in expr_descompuesta['Operadores']:
            i = expr_descompuesta['Operadores'].index('*')
            res_intermedio = self.calc.multiplicar(
                expr_descompuesta['Operandos'][i],
                expr_descompuesta['Operandos'][i + 1])
        elif '/' in expr_descompuesta['Operadores']:  # n
            i = expr_descompuesta['Operadores'].index('/')  # n
            res_intermedio = self.calc.dividir(
                expr_descompuesta['Operandos'][i],
                expr_descompuesta['Operandos'][i + 1])  # n
        elif '-' in expr_descompuesta['Operadores']:
            i = expr_descompuesta['Operadores'].index('-')
            res_intermedio = self.calc.restar(
                expr_descompuesta['Operandos'][i],
                expr_descompuesta['Operandos'][i + 1])
        elif '+' in expr_descompuesta['Operadores']:
            i = expr_descompuesta['Operadores'].index('+')
            res_intermedio = self.calc.sumar(
                expr_descompuesta['Operandos'][i],
                expr_descompuesta['Operandos'][i + 1])
        else:
            # Es un error, tenemos que decidir que hacer en los test
            # siguientes
            # Forzamos el error para que no haya problemas luego
            assert False
        return (i, res_intermedio)

    def __simplificar__(self, expr_descompuesta):
        if expr_descompuesta['Operadores'] == []:
            return expr_descompuesta

        (i, res_intermedio) = self.__operar__(expr_descompuesta)
        expr_simplificada = expr_descompuesta
        expr_simplificada['Operadores'].pop(i)
        expr_simplificada['Operandos'].pop(i)
        expr_simplificada['Operandos'].pop(i)
        expr_simplificada['Operandos'].insert(i, res_intermedio)

        return self.__simplificar__(expr_simplificada)

    def calcular(self, expresion):
        if not self.validador.validar(expresion):  # añadido para mock
            raise SyntaxError("La expresion no es valida")

        return str(self.__simplificar__(
            self.parser.parse(expresion))['Operandos'][0])
