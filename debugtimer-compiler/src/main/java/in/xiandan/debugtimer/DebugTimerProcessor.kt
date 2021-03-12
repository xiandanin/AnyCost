package `in`.xiandan.debugtimer

import `in`.xiandan.debugtimer.annotation.DebugTimerEnd
import `in`.xiandan.debugtimer.annotation.DebugTimerMark
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement




/**
 * Created by dengyuhan on 2021/3/3 11:46
 */
class DebugTimerProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf<String>(DebugTimerMark::class.java.canonicalName, DebugTimerEnd::class.java.canonicalName)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val map = HashMap<String, List<VariableElement>>()
        val elements = roundEnv.getElementsAnnotatedWith(DebugTimerMark::class.java)
        elements?.forEach {
            val kind = it.kind
            if (kind == ElementKind.METHOD) {
                resolveDebugTimerMark(it)
            }
        }
        return true
    }

    private  fun resolveDebugTimerMark(element: Element) {
        val annotation = element.getAnnotation(DebugTimerMark::class.java)
        val key = annotation.key
    }

}