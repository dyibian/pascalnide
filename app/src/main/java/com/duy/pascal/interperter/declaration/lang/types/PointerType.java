package com.duy.pascal.interperter.declaration.lang.types;

import android.support.annotation.NonNull;

import com.duy.pascal.interperter.ast.expressioncontext.ExpressionContext;
import com.duy.pascal.interperter.ast.runtime.ObjectBasedPointer;
import com.duy.pascal.interperter.ast.runtime.references.PascalReference;
import com.duy.pascal.interperter.ast.runtime.value.RuntimeValue;
import com.duy.pascal.interperter.exceptions.parsing.index.NonArrayIndexed;

public class PointerType extends TypeInfo {

    public Type pointedToType;

    public PointerType(Type pointedToType) {
        this.pointedToType = pointedToType;
    }

    @Override
    public RuntimeValue convert(RuntimeValue runtimeValue, ExpressionContext context)
            throws Exception {
        RuntimeType other = runtimeValue.getRuntimeType(context);
        if (this.equals(other.declType)) {
            return runtimeValue;
        }
        return null;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Object initialize() {
        return new ObjectBasedPointer(null);
    }

    @NonNull
    @Override
    public Class<?> getTransferClass() {
        return PascalReference.class;
    }

    @Override
    public boolean equals(Type otherType) {
        if (otherType instanceof PointerType) {
            return this.pointedToType.equals(((PointerType) otherType).pointedToType);
        }
        return false;
    }

    // The pointer itself contains no mutable information.
    @Override
    public RuntimeValue cloneValue(final RuntimeValue value) {
        return value;
    }

    @NonNull
    @Override
    public RuntimeValue generateArrayAccess(RuntimeValue array,
                                            RuntimeValue index) throws NonArrayIndexed {
        throw new NonArrayIndexed(array.getLineNumber(), this);
    }

    @NonNull
    @Override
    public Class<?> getStorageClass() {
        return getTransferClass();
    }

    @NonNull
    @Override
    public String getEntityType() {
        return "pointer type";
    }

    @Override
    public String toString() {
        return "^" + pointedToType.getName();
    }

    public void setPointerToType(Type pointerToType) {
        this.pointedToType = pointerToType;
    }
}
