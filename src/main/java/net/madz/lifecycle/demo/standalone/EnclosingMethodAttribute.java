package net.madz.lifecycle.demo.standalone;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.classfile.Visitor;

/**
 * EnclosingMethod_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 class_index;
 * u2 method_index;
 * }
 * 
 * @author Barry
 * 
 */
public class EnclosingMethodAttribute extends Attribute {

    private static final long serialVersionUID = 5019341665826317898L;
    private final short class_index;
    private final short method_index;

    protected EnclosingMethodAttribute(int attribute_name_index, short class_index, short method_index,
            ConstantPool constantPool) {
        super((byte) 0x01, attribute_name_index, 4, constantPool);
        this.class_index = class_index;
        this.method_index = method_index;
    }

    @Override
    public void accept(Visitor v) {
        byte[] bytes = new byte[] { ( (byte) ( ( class_index & 0xFF00 ) >>> 8 ) ), (byte) ( class_index & 0x00FF ),
                ( (byte) ( ( method_index & 0xFF00 ) >>> 8 ) ), (byte) ( method_index & 0x00FF ) };
        v.visitUnknown(new Unknown(this.name_index, 4, bytes, this.constant_pool));
    }

    public final void dump(DataOutputStream file) throws IOException {
        super.dump(file);
        file.writeShort(class_index);
        file.writeShort(method_index);
    }

    @Override
    public EnclosingMethodAttribute copy(ConstantPool _constant_pool) {
        EnclosingMethodAttribute clone = (EnclosingMethodAttribute) this.clone();
        clone.constant_pool = _constant_pool;;
        return clone;
    }
}
