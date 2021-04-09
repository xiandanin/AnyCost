package `in`.xiandan.anycost.gradleplugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter


/**
 * Created by xiandanin on 2021/3/29 13:57
 */
class AnyCostAdviceAdapter(methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {

    private var mAnnotationVisitor: AnyCostAnnotationVisitor? = null

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        val annotationVisitor = super.visitAnnotation(descriptor, visible)
        if ("Lin/xiandan/anycost/annotation/AnyCostMark;" == descriptor) {
            return AnyCostAnnotationVisitor(annotationVisitor).also { mAnnotationVisitor = it }
        }
        return annotationVisitor
    }

    override fun onMethodEnter() {
        mAnnotationVisitor?.also {

            mv.visitCode()
            mv.visitLdcInsn(it.value)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "in/xiandan/anycost/AnyCost", "begin", "(Ljava/lang/String;)V", false)

            mv.visitEnd()

        }
    }

    override fun onMethodExit(opcode: Int) {
        mAnnotationVisitor?.also {
            mv.visitCode()

            mv.visitLdcInsn(it.value)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "in/xiandan/anycost/AnyCost", "end", "(Ljava/lang/String;)J", false)

            mv.visitEnd()
        }
    }
}