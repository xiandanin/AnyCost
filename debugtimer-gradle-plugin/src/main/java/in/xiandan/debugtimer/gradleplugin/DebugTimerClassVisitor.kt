package `in`.xiandan.debugtimer.gradleplugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * Created by dengyuhan on 2021/3/11 11:13
 */
class DebugTimerClassVisitor : ClassVisitor, Opcodes {
    constructor(classVisitor: ClassVisitor) : super(Opcodes.ASM7, classVisitor)

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        println("visitMethod ${name}")
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        val methodVisitor = DebugTimerMethodVisitor(mv)
        return methodVisitor
    }
}