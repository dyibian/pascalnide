package com.duy.pascal.interperter.ast.runtime_value.operators.number;

import com.duy.pascal.interperter.ast.expressioncontext.CompileTimeContext;
import com.duy.pascal.interperter.ast.expressioncontext.ExpressionContext;
import com.duy.pascal.interperter.ast.runtime_value.operators.UnaryOperatorEval;
import com.duy.pascal.interperter.ast.runtime_value.value.RuntimeValue;
import com.duy.pascal.interperter.ast.runtime_value.value.access.ConstantAccess;
import com.duy.pascal.interperter.linenumber.LineInfo;
import com.duy.pascal.interperter.exceptions.parsing.ParsingException;
import com.duy.pascal.interperter.exceptions.runtime.PascalArithmeticException;
import com.duy.pascal.interperter.exceptions.runtime.internal.InternalInterpreterException;
import com.duy.pascal.interperter.declaration.lang.types.BasicType;
import com.duy.pascal.interperter.declaration.lang.types.OperatorTypes;
import com.duy.pascal.interperter.declaration.lang.types.RuntimeType;

public class BoolUniOperatorEval extends UnaryOperatorEval {

    public BoolUniOperatorEval(RuntimeValue operon, OperatorTypes operator, LineInfo line) {
        super(operon, operator, line);
    }

    @Override
    public RuntimeType getRuntimeType(ExpressionContext f) throws ParsingException {
        return new RuntimeType(BasicType.Boolean, false);
    }

    @Override
    public Object operate(Object value) throws PascalArithmeticException, InternalInterpreterException {
        switch (operator) {
            case NOT:
                return !(boolean) value;
            default:
                throw new InternalInterpreterException(line);
        }
    }

    @Override
    public RuntimeValue compileTimeExpressionFold(CompileTimeContext context) throws ParsingException {
        Object val = this.compileTimeValue(context);
        if (val != null) {
            return new ConstantAccess<>(val, line);
        } else {
            return new BoolUniOperatorEval(operon.compileTimeExpressionFold(context), operator,
                    line);
        }
    }

}
