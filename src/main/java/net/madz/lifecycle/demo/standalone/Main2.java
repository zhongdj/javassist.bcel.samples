package net.madz.lifecycle.demo.standalone;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.BCELifier;
import org.apache.bcel.verifier.statics.StringRepresentation;

public class Main2 {

    public static void main(String[] args) throws Throwable {
        final JavaClass outerClass = Repository.lookupClass(ServiceOrder.class.getName());
        StringRepresentation s = new StringRepresentation(outerClass);
        System.out.println(s);
        BCELifier fier = new BCELifier(outerClass, System.out);
        fier.start();
        
        final JavaClass outer2Class = Repository.lookupClass(net.madz.lifecycle.solutionOne.ServiceOrder.class.getName());
        BCELifier fier2 = new BCELifier(outer2Class, System.out);
        fier2.start();
        
        System.out.println("testing ......Started ...");
        ServiceOrder so = new ServiceOrder();
        so.allocateResources(1L, 2L, 3L);
        System.out.println("testing ......Ended ...");
    }
}
