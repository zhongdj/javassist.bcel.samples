package net.madz.lifecycle.demo.standalone;

import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public class BCELClassCreator {

    private static final String ENCLOSING_METHOD_INDEX = "EnclosingMethod";
    private static final String VAL$PLANT_RESOURCE_ID = "val$plantResourceId";
    private static final String THIS$0 = "this$0";
    private static final String VAL$TRUCK_RESOURCE_ID = "val$truckResourceId";
    private static final String VAL$SUMMARY_PLAN_ID = "val$summaryPlanId";
    private static final String FILE_NAME = "ServiceOrder$1.class";
    private static final String TARGET_CLASS = "net.madz.lifecycle.demo.standalone.ServiceOrder$1";
    private static final String ORDER_CLASS = "net.madz.lifecycle.demo.standalone.ServiceOrder";
    private static final String ENCLOSING_METHOD = "allocateResources";

    public static void main(String[] args) {
        // set up the construction tools
        // String class_name, String super_class_name, String file_name,
        // int access_flags, String[] interfaces
        //createInnerClass();
        
        
    }

    private static void createInnerClass() {
        final ClassGen cgen = new ClassGen(TARGET_CLASS, "java.lang.Object", FILE_NAME, Constants.ACC_PUBLIC,
                new String[0]);
        ConstantPoolGen constantPool = cgen.getConstantPool();
        constantPool.addClass(TARGET_CLASS);
        constantPool.addClass(ORDER_CLASS);
        /*
         * InnerClasses_attribute {
         * u2 attribute_name_index;
         * u4 attribute_length;
         * u2 number_of_classes;
         * { u2 inner_class_info_index;
         * u2 outer_class_info_index;
         * u2 inner_name_index;
         * u2 inner_class_access_flags;
         * } classes[number_of_classes];
         * }
         * 00 01
         * 00 04
         * 00 08
         * FF FF
         * 00 00
         */
        int innerClasses_index = constantPool.addUtf8("InnerClasses");
        final InnerClasses inner = new InnerClasses(innerClasses_index, 10, new InnerClass[] { new InnerClass(
                constantPool.lookupClass(TARGET_CLASS), constantPool.lookupClass(ORDER_CLASS),
                constantPool.lookupUtf8(TARGET_CLASS.replaceAll("\\.", "/")), 0) }, constantPool.getConstantPool());
        cgen.addAttribute(inner);
        constantPool.addUtf8(ENCLOSING_METHOD_INDEX);
        int enclosingMethodNameAndType = constantPool.addNameAndType(ENCLOSING_METHOD, "(JJJ)V");
        /*
         * 
         * /**
         * 
         * @param name_index Index in constant pool to CONSTANT_Utf8
         * 
         * @param length Content length in bytes
         * 
         * @param signature_index Index in constant pool to CONSTANT_Utf8
         * 
         * @param constant_pool Array of constants
         * public Signature(int name_index, int length, int signature_index,
         * ConstantPool constant_pool) {
         */
        int signature_index = constantPool
                .addUtf8("Ljava/lang/Object;Ljava/util/concurrent/Callable<Ljava/lang/Void;>;");
        int sig_name_index = constantPool.addUtf8("Signature");
        final Signature sig = new Signature(sig_name_index, 2, signature_index, constantPool.getConstantPool());
        cgen.addAttribute(sig);
        // cgen.addAttribute(ema.);
        cgen.addAttribute(new EnclosingMethodAttribute(constantPool.lookupUtf8(ENCLOSING_METHOD_INDEX),
                (short) constantPool.lookupClass(ORDER_CLASS), (short) enclosingMethodNameAndType, constantPool
                        .getConstantPool()));
        cgen.setAccessFlags(Constants.ACC_SUPER);
        cgen.addInterface("java.util.concurrent.Callable<java.lang.Void>");
        final FieldGen serviceOrderField = new FieldGen(0x1010, new ObjectType(ORDER_CLASS), THIS$0, constantPool);
        cgen.addField(serviceOrderField.getField());
        final FieldGen summaryPlanIdField = new FieldGen(0x1012, Type.LONG, VAL$SUMMARY_PLAN_ID, constantPool);
        cgen.addField(summaryPlanIdField.getField());
        final FieldGen truckResourceIdField = new FieldGen(0x1012, Type.LONG, VAL$TRUCK_RESOURCE_ID, constantPool);
        cgen.addField(truckResourceIdField.getField());
        final FieldGen plantResourceId = new FieldGen(0x1012, Type.LONG, VAL$PLANT_RESOURCE_ID, constantPool);
        cgen.addField(plantResourceId.getField());
        {
            createConstructor(cgen);
        }
        {
            createCall(cgen);
        }
        {
            createBridgeCall(cgen);
        }
        try {
            String path = "/Users/Barry/Professional/Workspaces/seed/javassist.samples/target/classes/net/madz/lifecycle/demo/standalone/"
                    + FILE_NAME;
            cgen.getJavaClass().dump(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createBridgeCall(ClassGen cgen) {
        InstructionFactory ifact = new InstructionFactory(cgen);
        InstructionList iList = new InstructionList();
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createInvoke(TARGET_CLASS, "call", new ObjectType("java.lang.Void"), Type.NO_ARGS,
                Constants.INVOKEVIRTUAL));
        iList.append(InstructionConstants.ARETURN);
        final MethodGen callMethodGen = new MethodGen(4161, new ObjectType("java.lang.Object"), Type.NO_ARGS,
                new String[] {}, "call", TARGET_CLASS, iList, cgen.getConstantPool());
        callMethodGen.addException("java.lang.Exception");
        addMethod(cgen, callMethodGen);
        iList.dispose();
    }

    private static void createCall(ClassGen cgen) {
        InstructionFactory ifact = new InstructionFactory(cgen);
        InstructionList iList = new InstructionList();
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createGetField(TARGET_CLASS, THIS$0, new ObjectType(ORDER_CLASS)));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createGetField(TARGET_CLASS, VAL$SUMMARY_PLAN_ID, Type.LONG));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createGetField(TARGET_CLASS, VAL$TRUCK_RESOURCE_ID, Type.LONG));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createGetField(TARGET_CLASS, VAL$PLANT_RESOURCE_ID, Type.LONG));
        iList.append(ifact.createInvoke(ORDER_CLASS, "allocateResources$Impl", Type.VOID, new Type[] { Type.LONG,
                Type.LONG, Type.LONG }, Constants.INVOKEVIRTUAL));
        iList.append(InstructionConstants.ACONST_NULL);
        iList.append(InstructionConstants.ARETURN);
        final MethodGen callMethodGen = new MethodGen(1, new ObjectType("java.lang.Void"), Type.NO_ARGS,
                new String[] {}, "call", TARGET_CLASS, iList, cgen.getConstantPool());
        callMethodGen.addException("java.lang.Exception");
        addMethod(cgen, callMethodGen);
        iList.dispose();
    }

    private static void createConstructor(ClassGen cgen) {
        /*
         * int access_flags, Type return_type, Type[] arg_types,
         * String[] arg_names, String method_name, String class_name,
         * InstructionList il, ConstantPoolGen cp
         */
        InstructionFactory ifact = new InstructionFactory(cgen);
        InstructionList iList = new InstructionList();
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(InstructionConstants.ALOAD_1);
        iList.append(ifact.createPutField(TARGET_CLASS, THIS$0, new ObjectType(ORDER_CLASS)));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(InstructionFactory.createLoad(Type.LONG, 2));
        iList.append(ifact.createPutField(TARGET_CLASS, VAL$SUMMARY_PLAN_ID, Type.LONG));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(InstructionFactory.createLoad(Type.LONG, 4));
        iList.append(ifact.createPutField(TARGET_CLASS, VAL$TRUCK_RESOURCE_ID, Type.LONG));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(InstructionFactory.createLoad(Type.LONG, 6));
        iList.append(ifact.createPutField(TARGET_CLASS, VAL$PLANT_RESOURCE_ID, Type.LONG));
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
        iList.append(InstructionFactory.createReturn(Type.VOID));
        final MethodGen constructorGen = new MethodGen(0, org.apache.bcel.generic.Type.VOID,
                new org.apache.bcel.generic.Type[] { new ObjectType(ORDER_CLASS), org.apache.bcel.generic.Type.LONG,
                        org.apache.bcel.generic.Type.LONG, org.apache.bcel.generic.Type.LONG }, new String[] {
                        "serviceOrder", "summaryPlanId", "truckResourceId", "plantResourceId" }, "<init>",
                TARGET_CLASS, iList, cgen.getConstantPool());
        addMethod(cgen, constructorGen);
        iList.dispose();
    }

    private static void addMethod(ClassGen cgen, final MethodGen constructorGen) {
        constructorGen.setMaxLocals();
        constructorGen.setMaxStack();
        // constructorGen.stripAttributes(true);
        cgen.addMethod(constructorGen.getMethod());
        cgen.setMinor(0);
        cgen.setMajor(50);
    }
}
