package net.madz.bcel.extension;

import org.apache.bcel.classfile.ConstantMethodHandle;

public class ConstantPoolGen extends org.apache.bcel.generic.ConstantPoolGen {

    private static final long serialVersionUID = 8082749102697626594L;

    public ConstantPoolGen(org.apache.bcel.generic.ConstantPoolGen constantPoolGen) {
        super(constantPoolGen.getConstantPool());
    }

    public int addMethodHandle(ConstantMethodHandle methodHandle) {
        int ret;
        if ( ( ret = lookupMethodHandle(methodHandle) ) != -1 ) {
            return ret; // Already in CP
        }
        adjustSize();
        ConstantMethodHandle c = new ConstantMethodHandle(methodHandle.getReference_kind(),
                methodHandle.getReference_index());
        ret = index;
        constants[index++] = c;
        return ret;
    }

    public int lookupMethodHandle(ConstantMethodHandle methodHandle) {
        for ( int i = 1; i < index; i++ ) {
            if ( constants[i] instanceof ConstantMethodHandle ) {
                ConstantMethodHandle me = (ConstantMethodHandle) constants[i];
                if ( me.equals(methodHandle) ) return i;
            }
        }
        return -1;
    }
}
