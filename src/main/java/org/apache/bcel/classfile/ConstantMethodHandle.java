package org.apache.bcel.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 
 * 
 * CONSTANT_MethodHandle_info {
 * u1 tag;
 * u1 reference_kind;
 * u2 reference_index;
 * }
 * 
 * @author Barry
 * 
 */
public class ConstantMethodHandle extends Constant {

    private static final long serialVersionUID = 3219344597693002711L;
    private byte reference_kind;
    private short reference_index;

    public ConstantMethodHandle() {
        super(ExtConstants.CONSTANT_METHODHANDLE);
    }

    @Override
    public void accept(Visitor v) {
    }

    public ConstantMethodHandle(byte reference_kind, short reference_index) {
        super(ExtConstants.CONSTANT_METHODHANDLE);
        this.reference_kind = reference_kind;
        this.reference_index = reference_index;
    }

    @Override
    public void dump(DataOutputStream file) throws IOException {
        file.writeByte(reference_kind);
        file.writeShort(reference_index);
    }

    public byte getReference_kind() {
        return reference_kind;
    }

    public void setReference_kind(byte reference_kind) {
        this.reference_kind = reference_kind;
    }

    public short getReference_index() {
        return reference_index;
    }

    public void setReference_index(short reference_index) {
        this.reference_index = reference_index;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + reference_index;
        result = prime * result + reference_kind;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( !super.equals(obj) ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ConstantMethodHandle other = (ConstantMethodHandle) obj;
        if ( reference_index != other.reference_index ) return false;
        if ( reference_kind != other.reference_kind ) return false;
        return true;
    }
}
