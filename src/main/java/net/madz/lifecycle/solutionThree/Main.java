package net.madz.lifecycle.solutionThree;

import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class Main {

    public static void main(String[] argv) {
        if ( argv.length == 2 ) {
            try {
                // start by getting the class file and method
                CtClass clas = ClassPool.getDefault().get(argv[0]);
                if ( clas == null ) {
                    System.err.println("Class " + argv[0] + " not found");
                } else {
                    // add timing interceptor to the class
                    addInterceptor(clas, argv[1]);
                    clas.writeFile();
                    System.out.println("Added interceptorChain to method " + argv[0] + "." + argv[1]);
                }
            } catch (CannotCompileException ex) {
                ex.printStackTrace();
            } catch (NotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Usage: JassistTiming class method-name");
        }
    }

    private static void addInterceptor(CtClass clas, String mname) throws NotFoundException, CannotCompileException {
        // get the method information (throws exception if method with
        // given name is not declared directly by this class, returns
        // arbitrary choice if more than one with the given name)
        CtMethod mold = clas.getDeclaredMethod(mname);
        // rename old method to synthetic name, then duplicate the
        // method with original name for use as interceptor
        String nname = mname + "$Impl";
        mold.setName(nname);
        CtMethod mnew = CtNewMethod.copy(mold, mname, clas, null);
        // start the body text generation by saving the start time
        // to a local variable, then call the timed method; the
        // actual code generated needs to depend on whether the
        // timed method returns a value
        String type = mold.getReturnType().getName();
        
        clas.getClassPool().importPackage("java.lang.reflect");
        
        StringBuffer body = new StringBuffer();
        body.append("\ntry {\n");
        body.append("\n    java.lang.reflect.Method m = findMethod(\"" + mname + "\", new Class[] { Long.class, Long.class, Long.class }); \n");
        body.append("\n    preExe(m);\n");
        if ( !"void".equals(type) ) {
            body.append("\n    return " + nname + "($$);\n");
        } else {
            body.append("\n    " + nname + "($$);\n");
        }
        body.append("\n    postExe(m);\n");
        body.append("\n} catch (Throwable t) {\n");
        body.append("\n    handleException(t);\n");
        body.append("\n} finally {\n");
        body.append("\n    cleanup();\n");
        body.append("\n}\n");
        
        System.out.println(body);
        // replace the body of the interceptor method with generated
        // code block and add it to class
        mnew.setBody(body.toString());
        clas.addMethod(mnew);
        // print the generated code block just to show what was done
        System.out.println("Interceptor method body:");
        System.out.println(body.toString());
    }
}
