package net.madz.lifecycle.demo.standalone;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class Main3 {

    public static void main(String[] argv) throws Throwable {
        if ( argv.length == 2 && argv[0].endsWith(".class") ) {
            try {
                JavaClass jclas = new ClassParser(argv[0]).parse();
                ClassGen cgen = new ClassGen(jclas);
                Method[] methods = jclas.getMethods();
                int index;
                for ( index = 0; index < methods.length; index++ ) {
                    if ( methods[index].getName().equals(argv[1]) ) {
                        break;
                    }
                }
                if ( index < methods.length ) {
                    int innerClassSeq = 1;
                    Attribute[] attributes = cgen.getAttributes();
                    for ( Attribute attribute : attributes ) {
                        if ( attribute instanceof InnerClasses ) {
                            InnerClasses icAttr = (InnerClasses) attribute;
                            innerClassSeq += icAttr.getInnerClasses().length;
                        }
                    }
                    Method interceptingMethod = methods[index];
                    doGenerateAll(cgen, innerClassSeq, interceptingMethod);
                    FileOutputStream fos = new FileOutputStream(argv[0]);
                    cgen.getJavaClass().dump(fos);
                    fos.close();
                    
                    Main2.main(new String[]{});
                } else {
                    System.err.println("Method " + argv[1] + " not found in " + argv[0]);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        } else {
            System.out.println("Usage: BCELMethodInterceptor class-file method-name");
        }
        
    }

    private static void doGenerateAll(ClassGen cgen, int innerClassSeq, Method interceptingMethod) throws Throwable {
        BCELAnonymousInnerClass c = new BCELAnonymousInnerClass(cgen.getClassName(), interceptingMethod.getName(),
                interceptingMethod.getArgumentTypes(), innerClassSeq, Object.class.getName(), new Type[0],
                java.util.concurrent.Callable.class.getName(), new Type[] { new ObjectType(Void.class.getName()) });
        c.doGenerate();
        BCELMethodInterceptor.addWrapper(cgen, interceptingMethod, innerClassSeq);
    }
}
