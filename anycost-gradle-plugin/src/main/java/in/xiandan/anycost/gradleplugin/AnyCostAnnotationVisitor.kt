package `in`.xiandan.anycost.gradleplugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by xiandanin on 2021/3/29 14:12
 */
class AnyCostAnnotationVisitor(annotationVisitor: AnnotationVisitor?) : AnnotationVisitor(Opcodes.ASM7, annotationVisitor) {

    var name: String? = null
    var value: Any? = null

    override fun visit(name: String, value: Any) {
        this.name = name
        this.value = value
        super.visit(name, value)
    }
}