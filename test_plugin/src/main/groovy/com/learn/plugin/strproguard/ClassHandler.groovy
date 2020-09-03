package com.learn.plugin.strproguard
import com.xcm.test_plugin.ClassConst
import com.xcm.test_plugin.GuardClassVisitor
import com.xcm.test_plugin.StringGuardUtils
import com.xcm.test_plugin.XorUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor

public class ClassHandler {


    public static void injectUpdateClass(String path, Project project) {

        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File classFile ->
                if (!classFile.getName().equals("R.class")
                        && !classFile.getName().startsWith("R\$")
                        && classFile.getName().endsWith(".class")) {
                    //指定目录的
                    if (filterDir(classFile.getPath())) {
                        handlerUpdate(classFile);
                    }
                    //指定文件的
                    else if (filterClass(classFile.getName())) {
                        handlerUpdate(classFile);
                    }
                }
            }
        }
    }

    /**
     * 处理修改
     * @param classFile
     */
    public static void handlerUpdate(File classFile) {
        if (GlobalConfig.getParams().decryptKey.toString() != null && GlobalConfig.getParams().decryptKey.toString().length() > 0) {
            byte[] keyBytes = getBytes();
            XorUtils.keyBytes = keyBytes
            System.out.println("decryptKey:>>>>>>" + GlobalConfig.getParams().decryptKey.toString()+"<<<<<<")
        }
        System.out.println("handlerUpdate classFile = "+classFile)
        ClassReader cr = new ClassReader(new FileInputStream(classFile));
        System.out.println("handlerUpdate #1")
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        System.out.println("handlerUpdate #2")
        ClassVisitor cv = new GuardClassVisitor(cw, GlobalConfig.getParams().decryptFile, GlobalConfig.getParams().decryptMethod);
        System.out.println("handlerUpdate #3")
        cr.accept(cv, Opcodes.ASM4);
        System.out.println("handlerUpdate #4")
        File newFile = new File(classFile.getAbsolutePath());
        System.out.println("handlerUpdate #5")
        boolean isSucc = StringGuardUtils.saveFileData(newFile, cw.toByteArray());
        System.out.println("handlerUpdate #6")
        System.out.println("Modify the result of this class->" + classFile.getName() + "_result:" + isSucc);

        ClassConst.guardFinalStaticFieldMaps.clear();
        ClassConst.guardFinalFiedlMaps.clear();
    }

    /**
     * String 转 byte[]
     *
     * @param data
     * @return
     */
    public static byte[] getBytes() {
        byte[] bytes = new byte[GlobalConfig.getParams().decryptKey.size()];
        int i = 0;
        GlobalConfig.getParams().decryptKey.each { res ->
            bytes[i] = res;
            i++
        }
        return bytes;
    }

    /**
     * 需要加密的
     * @param name
     * @return
     */
    public static boolean filterClass(String name) {
        boolean result = false;
        GlobalConfig.getParams().stringEncryList.each { res ->
            System.out.println("Class of current input filtering->:" + name);
            if (name.contains(res)) {
                System.out.println("Ready to modify this class->:" + name);
                result = true;
                return false;
            }
        }
        return result;
    }

    /**
     * 需要加密的目录
     * @param name
     * @return
     */
    public static boolean filterDir(String dir) {
        boolean result = false;
        GlobalConfig.getParams().stringEncryDirList.each { res ->
            File file = new File(res);
            System.out.println("dir of current input filtering->:" + file.getPath());
            if (dir.contains(file.getPath())) {
                System.out.println("Ready to modify this dir->:" + dir);
                result = true;
                return false;
            }
        }
        return result;
    }

}