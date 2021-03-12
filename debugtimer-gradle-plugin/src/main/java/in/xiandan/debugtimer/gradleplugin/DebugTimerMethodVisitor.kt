package `in`.xiandan.debugtimer.gradleplugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


/**
 * Created by dengyuhan on 2021/3/11 11:22
 */
class DebugTimerMethodVisitor : MethodVisitor {
    constructor(methodVisitor: MethodVisitor?) : super(Opcodes.ASM7, methodVisitor)

    override fun visitCode() {
        println("visitCode ")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "in.xiandan.debugtimer.DebugTimer", "mark", "(Ljava/lang/String;)I", false)
        super.visitCode()
    }
}