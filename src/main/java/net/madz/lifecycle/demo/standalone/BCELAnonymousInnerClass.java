package net.madz.lifecycle.demo.standalone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.Constants;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReturnInstruction;
import org.apache.bcel.generic.Type;
import org.apache.bcel.verifier.statics.StringRepresentation;

public class BCELAnonymousInnerClass {

    private final String outerClassName;
    private final String enclosingMethodName;
    private final Type[] enclosingMethodArguments;
    private final int innerClassSeq;
    private final String superClass;
    private final Type[] superClassTypeParameters;
    private final String interfaceClass;
    private final Type[] interfaceClassTypeParameters;
    private final String thisClassName;
    private final String sourceFile;

    public BCELAnonymousInnerClass(String outerClassName, String enclosingMethodName, Type[] enclosingMethodArguments,
            int innerClassSeq, String superClass, String interfaceClass) {
        this(outerClassName, enclosingMethodName, enclosingMethodArguments, innerClassSeq, superClass, new Type[0],
                interfaceClass, new Type[0]);
    }

    public BCELAnonymousInnerClass(String outerClassName, String enclosingMethodName, Type[] enclosingMethodArguments,
            int innerClassSeq, String superClass, Type[] superClassTypeParameters, String interfaceClass,
            Type[] interfaceClassTypeParameters) {
        super();
        this.outerClassName = outerClassName;
        this.enclosingMethodName = enclosingMethodName;
        this.enclosingMethodArguments = enclosingMethodArguments;
        this.innerClassSeq = innerClassSeq;
        this.superClass = superClass;
        this.interfaceClass = interfaceClass;
        this.thisClassName = this.outerClassName + "$" + this.innerClassSeq;
        this.sourceFile = parseSourceFileName();
        this.superClassTypeParameters = superClassTypeParameters;
        this.interfaceClassTypeParameters = interfaceClassTypeParameters;
    }

    private String parseSourceFileName() {
        int first$ = this.outerClassName.indexOf("$");
        if ( -1 < first$ ) {
            int lastDot = this.outerClassName.substring(0, first$).lastIndexOf(".");
            return this.outerClassName.substring(lastDot + 1, first$) + ".java";
        } else {
            int lastDot = this.outerClassName.lastIndexOf(".");
            return this.outerClassName.substring(lastDot + 1) + ".java";
        }
    }

    public ClassGen doGenerate() throws Throwable {
        final ClassGen cgen = new ClassGen(this.thisClassName, this.superClass, this.sourceFile,
                Constants.ACC_SYNCHRONIZED, new String[0]);
        // generate interface
        if ( null != this.interfaceClass && !this.interfaceClass.isEmpty() ) {
            cgen.addInterface(this.interfaceClass);
        }
        {// generate signature attribute
            doGenerateSignatureAttribute(cgen);
        }
        {// generate enclosing method attribute
            doGenerateEnclosingMethodAttribute(cgen);
        }
        {// generate inner classes attribute
            generateInnerClassesAttribute(cgen);
        }
        {// generate fields
            doGenerateFields(cgen);
        }
        {// generate constructor
            doGenerateConstructor(cgen);
        }
        {// generate methods
            doGenerateMethods(cgen);
        }
        // Dump
        StringRepresentation visitor = new StringRepresentation(cgen.getJavaClass());
        System.out.println(visitor.toString());
        final JavaClass outerClass = Repository.lookupClass("net.madz.lifecycle.solutionOne.ServiceOrder$1");
        StringRepresentation visitor2 = new StringRepresentation(outerClass);
        System.out.println(visitor2.toString());
        try {
            final String simpleClassName = thisClassName.substring(thisClassName.lastIndexOf(".") + 1,
                    thisClassName.length());
            String path = "/Users/Barry/Professional/Workspaces/seed/javassist.samples/target/classes/net/madz/lifecycle/demo/standalone/"
                    + simpleClassName + ".class";
            cgen.getJavaClass().dump(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cgen;
    }

    private void generateInnerClassesAttribute(final ClassGen cgen) {
        ConstantPoolGen constantPool = cgen.getConstantPool();
        int innerClasses_index = constantPool.addUtf8("InnerClasses");
        final InnerClasses inner = new InnerClasses(innerClasses_index, 10, new InnerClass[] { new InnerClass(
                constantPool.lookupClass(thisClassName), constantPool.lookupClass(outerClassName),
                // If C is anonymous (JLS §15.9.5), the value of the
                // inner_name_index item must be zero.
                0,
                // They should be set to zero in generated class files and
                // should be ignored by Java Virtual Machine
                // implementations.
                0) }, constantPool.getConstantPool());
        cgen.addAttribute(inner);
    }

    private void doGenerateEnclosingMethodAttribute(final ClassGen cgen) throws ClassNotFoundException {
        final ConstantPoolGen constantPoolGen = cgen.getConstantPool();
        int outClassIndex = constantPoolGen.addClass(new ObjectType(this.outerClassName));
        final int enclosingMethodIndex = constantPoolGen.addUtf8("EnclosingMethod");
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for ( Type t : enclosingMethodArguments ) {
            sb.append(t.getSignature());
        }
        sb.append(")");
        sb.append(lookupEnclosingMethodReturnType().getSignature());
        String enclosingMethodSignature = sb.toString();
        int enclosingMethodNameAndType = constantPoolGen.addNameAndType(this.enclosingMethodName,
                enclosingMethodSignature);
        // cgen.addAttribute(new
        // EnclosingMethodAttribute(enclosingMethodIndex, (short)
        // constantPoolGen
        // .lookupClass(this.outerClassName), (short)
        // enclosingMethodNameAndType, constantPoolGen
        // .getConstantPool()));
        // --------------------------------------------------------------------------------------
        // while parsing a Java class, parser will treat enclosingMethod as
        // unknown attribute
        byte[] bytes = new byte[] { ( (byte) ( ( outClassIndex & 0xFF00 ) >>> 8 ) ), (byte) ( outClassIndex & 0x00FF ),
                ( (byte) ( ( enclosingMethodNameAndType & 0xFF00 ) >>> 8 ) ),
                (byte) ( enclosingMethodNameAndType & 0x00FF ) };
        cgen.addAttribute(new Unknown(enclosingMethodIndex, 4, bytes, constantPoolGen.getConstantPool()));
    }

    private void doGenerateMethods(ClassGen cgen) throws ClassNotFoundException {
        createCall(cgen);
        createBridgeCall(cgen);
    }

    private void createBridgeCall(ClassGen cgen) {
        InstructionFactory ifact = new InstructionFactory(cgen);
        InstructionList iList = new InstructionList();
        final LocalVariableInstruction start = InstructionConstants.ALOAD_0;
        iList.append(start);
        iList.append(ifact.createInvoke(thisClassName, "call", new ObjectType("java.lang.Void"), Type.NO_ARGS,
                Constants.INVOKEVIRTUAL));
        ReturnInstruction end = InstructionConstants.ARETURN;
        iList.append(end);
        final MethodGen callMethodGen = new MethodGen(4161, new ObjectType("java.lang.Object"), Type.NO_ARGS,
                new String[] {}, "call", thisClassName, iList, cgen.getConstantPool());
        callMethodGen.addException("java.lang.Exception");
        addMethod(cgen, callMethodGen);
        iList.dispose();
    }

    private void createCall(ClassGen cgen) throws ClassNotFoundException {
        final InstructionFactory ifact = new InstructionFactory(cgen);
        final InstructionList iList = new InstructionList();
        final List<LocalVariable> argumentVariables = lookupEnclosingMethodArgumentVariables();
        for ( LocalVariable var : argumentVariables ) {
            iList.append(InstructionConstants.ALOAD_0);
            if ( "this".equals(var.getName()) ) {
                iList.append(ifact.createGetField(thisClassName, "this$0", convertSignature2Type(var.getSignature())));
            } else {
                iList.append(ifact.createGetField(thisClassName, "val$" + var.getName(),
                        convertSignature2Type(var.getSignature())));
            }
        }
        final Type returnType = lookupEnclosingMethodReturnType();
        final Type[] argTypes = lookupEnclosingMethodArgType();
        iList.append(ifact.createInvoke(outerClassName, "allocateResources$Impl", returnType, argTypes,
                Constants.INVOKEVIRTUAL));
        iList.append(InstructionConstants.ACONST_NULL);
        iList.append(InstructionConstants.ARETURN);
        final MethodGen callMethodGen = new MethodGen(1, new ObjectType("java.lang.Void"), Type.NO_ARGS,
                new String[] {}, "call", thisClassName, iList, cgen.getConstantPool());
        callMethodGen.addException("java.lang.Exception");
        addMethod(cgen, callMethodGen);
        iList.dispose();
    }

    private void doGenerateConstructor(ClassGen cgen) throws Throwable {
        InstructionFactory ifact = new InstructionFactory(cgen);
        InstructionList iList = new InstructionList();
        Field[] fields = cgen.getFields();
        int fieldIndex = 1;
        for ( int i = 0; i < fields.length; i++ ) {
            if ( "this$0".equals(fields[i].getName()) ) {
                iList.append(InstructionConstants.ALOAD_0);
                iList.append(InstructionConstants.ALOAD_1);
                iList.append(ifact.createPutField(this.thisClassName, "this$0", new ObjectType(this.outerClassName)));
                fieldIndex++;
            } else {
                iList.append(InstructionConstants.ALOAD_0);
                iList.append(InstructionFactory.createLoad(fields[i].getType(), fieldIndex));
                iList.append(ifact.createPutField(this.thisClassName, fields[i].getName(), fields[i].getType()));
                fieldIndex += fields[i].getType().getSize();
            }
        }
        iList.append(InstructionConstants.ALOAD_0);
        iList.append(ifact.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
        iList.append(InstructionFactory.createReturn(Type.VOID));
        final List<LocalVariable> arguments = lookupEnclosingMethodArgumentVariables();
        final ArrayList<Type> typeList = new ArrayList<Type>();
        final String[] argumentNames = new String[arguments.size()];
        for ( int i = 0; i < arguments.size(); i++ ) {
            if ( "this$0".equals(arguments.get(i).getName()) ) {
                int lastDot = this.outerClassName.lastIndexOf(".");
                String substring = this.outerClassName.substring(lastDot + 1);
                if ( substring.charAt(0) >= 'A' && substring.charAt(0) <= 'Z' ) {
                    substring = ( substring.charAt(0) + "" ).toLowerCase() + substring.substring(1);
                }
                typeList.add(convertSignature2Type(arguments.get(i).getSignature()));
                argumentNames[i] = substring;
            } else {
                typeList.add(convertSignature2Type(arguments.get(i).getSignature()));
                argumentNames[i] = arguments.get(i).getName();
            }
        }
        final MethodGen constructorGen = new MethodGen(0, org.apache.bcel.generic.Type.VOID,
                typeList.toArray(new Type[0]), argumentNames, "<init>", this.thisClassName, iList,
                cgen.getConstantPool());
        addMethod(cgen, constructorGen);
        iList.dispose();
    }

    private void addMethod(ClassGen cgen, final MethodGen method) {
        method.setMaxLocals();
        method.setMaxStack();
        method.stripAttributes(true);
        // final LocalVariableTable localVariableTable =
        // method.getLocalVariableTable(cgen.getConstantPool());
        // final LocalVariable[] local_variable_table = new LocalVariable[1];
        // final int thisIndex = cgen.getConstantPool().addUtf8("this");
        // final int signature_index = cgen.getConstantPool().lookupUtf8(new
        // ObjectType(thisClassName).getSignature());
        // local_variable_table[0] = new LocalVariable(0,
        // method.getInstructionList().getLength(), thisIndex,
        // signature_index, 0, cgen.getConstantPool().getConstantPool());
        // localVariableTable.setLocalVariableTable(local_variable_table);
        cgen.addMethod(method.getMethod());
        cgen.setMinor(0);
        cgen.setMajor(50);
    }

    private void doGenerateSignatureAttribute(final ClassGen cgen) {
        final ConstantPoolGen constantPool = cgen.getConstantPool();
        StringBuilder sb = new StringBuilder();
        sb.append(createSignature(superClass, superClassTypeParameters));
        sb.append(createSignature(interfaceClass, interfaceClassTypeParameters));
        int signature_index = constantPool.addUtf8(sb.toString());
        int sig_name_index = constantPool.addUtf8("Signature");
        final Signature sig = new Signature(sig_name_index, 2, signature_index, constantPool.getConstantPool());
        cgen.addAttribute(sig);
    }

    private String createSignature(String klass, Type[] typeParameters) {
        StringBuilder sb = new StringBuilder();
        if ( 0 < typeParameters.length ) {
            sb.append("<");
            for ( int i = 0; i < typeParameters.length; i++ ) {
                sb.append(typeParameters[i].getSignature());
                if ( i != typeParameters.length - 1 ) {
                    sb.append(",");
                }
            }
            sb.append(">");
        }
        return new ObjectType(klass + sb.toString()).getSignature();
    }

    private void doGenerateFields(final ClassGen cgen) throws ClassNotFoundException {
        final List<LocalVariable> arguments = lookupEnclosingMethodArgumentVariables();
        for ( LocalVariable localVariable : arguments ) {
            doGenField(localVariable, cgen);
        }
    }

    private void doGenField(LocalVariable localVariable, ClassGen cgen) {
        if ( localVariable.getName().equals("this") ) {
            createField("this$0", localVariable.getSignature(), cgen);
        } else {
            createField("val$" + localVariable.getName(), localVariable.getSignature(), cgen);
        }
    }

    private void createField(String fieldName, String signature, ClassGen cgen) {
        System.out.println(fieldName);
        System.out.println(signature);
        Type type = null;
        if ( "this$0".equals(fieldName) ) {
            type = new ObjectType(this.outerClassName);
        } else {
            type = convertSignature2Type(signature);
        }
        final FieldGen serviceOrderField = new FieldGen(0x1010, type, fieldName, cgen.getConstantPool());
        cgen.addField(serviceOrderField.getField());
    }

    private Type convertSignature2Type(String signature) {
        switch (signature.charAt(0)) {
            case 'Z':
                return Type.BOOLEAN;
            case 'C':
                return Type.CHAR;
            case 'F':
                return Type.FLOAT;
            case 'D':
                return Type.DOUBLE;
            case 'B':
                return Type.BYTE;
            case 'S':
                return Type.SHORT;
            case 'I':
                return Type.INT;
            case 'J':
                return Type.LONG;
            default:
                /*
                 * //process type with TypeParameters, such as
                 * Ljava.util.concurrent.Call<Ljava.lang.Void;>;
                 * if ( signature.startsWith("L") ) {
                 * signature = ";" + signature;
                 * }
                 * signature = signature.replace(";L", "");
                 * signature = signature.replace(";", "");
                 */
                if ( signature.startsWith("L") ) {
                    int firstLeftArrow = signature.indexOf("<");
                    if ( -1 == firstLeftArrow ) {
                        signature = signature.substring(1);
                    } else {
                        signature = signature.substring(1, firstLeftArrow);
                    }
                    if ( signature.endsWith(";") ) {
                        signature = signature.substring(0, signature.length() - 1);
                    }
                }
                return new ObjectType(signature);
        }
    }

    private List<LocalVariable> lookupEnclosingMethodArgumentVariables() throws ClassNotFoundException {
        final JavaClass outerClass = Repository.lookupClass(this.outerClassName);
        for ( Method method : outerClass.getMethods() ) {
            if ( !enclosingMethodName.equals(method.getName()) ) {
                continue;
            }
            if ( !argumentsMatch(method.getArgumentTypes()) ) {
                continue;
            }
            final List<LocalVariable> result = new ArrayList<LocalVariable>();
            final LocalVariableTable localVariableTable = method.getLocalVariableTable();
            if ( null != localVariableTable ) {
                for ( org.apache.bcel.classfile.LocalVariable localVariable : localVariableTable
                        .getLocalVariableTable() ) {
                    if ( localVariable.getStartPC() == 0 ) {
                        result.add(new LocalVariable(localVariable.getName(), localVariable.getSignature()));
                    }
                }
            } else {
                int i = 1;
                result.add(new LocalVariable("this", new ObjectType(outerClassName).getSignature()));
                for ( Type argumentType : method.getArgumentTypes() ) {
                    result.add(new LocalVariable("" + i++, argumentType.getSignature()));
                }
            }
            return result;
        }
        return new ArrayList<LocalVariable>();
    }

    private Type lookupEnclosingMethodReturnType() throws ClassNotFoundException {
        final JavaClass outerClass = Repository.lookupClass(this.outerClassName);
        for ( Method method : outerClass.getMethods() ) {
            if ( !enclosingMethodName.equals(method.getName()) ) {
                continue;
            }
            if ( !argumentsMatch(method.getArgumentTypes()) ) {
                continue;
            }
            return method.getReturnType();
        }
        throw new IllegalStateException("Cannot find enclosingMethod: " + enclosingMethodName);
    }

    private Type[] lookupEnclosingMethodArgType() throws ClassNotFoundException {
        final JavaClass outerClass = Repository.lookupClass(this.outerClassName);
        for ( Method method : outerClass.getMethods() ) {
            if ( !enclosingMethodName.equals(method.getName()) ) {
                continue;
            }
            if ( !argumentsMatch(method.getArgumentTypes()) ) {
                continue;
            }
            return method.getArgumentTypes();
        }
        throw new IllegalStateException("Cannot find enclosingMethod: " + enclosingMethodName);
    }

    private boolean argumentsMatch(Type[] argumentTypes) {
        if ( enclosingMethodArguments.length != argumentTypes.length ) {
            return false;
        } else {
            for ( int i = 0; i < argumentTypes.length; i++ ) {
                if ( !enclosingMethodArguments[i].equals(argumentTypes[i]) ) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Throwable {
        BCELAnonymousInnerClass c = new BCELAnonymousInnerClass("net.madz.lifecycle.demo.standalone.ServiceOrder",
                "allocateResources", new Type[] { Type.LONG,Type.LONG,Type.LONG }, 5,
                "java.lang.Object", new Type[0], "java.util.concurrent.Callable", new Type[] { new ObjectType(
                        "java.lang.Void") });
        c.doGenerate();
    }

    public static class LocalVariable {

        private final String name;
        private final String signature;

        public LocalVariable(String name, String signature) {
            super();
            this.name = name;
            this.signature = signature;
        }

        String getName() {
            return name;
        }

        String getSignature() {
            return signature;
        }
    }
}
