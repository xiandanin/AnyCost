package `in`.xiandan.anycost.gradleplugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * Created by dengyuhan on 2021/3/11 11:13
 */
class AnyCostClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM7, classVisitor), Opcodes {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return AnyCostAdviceAdapter(mv, access, name, descriptor)
    }
}