package `in`.xiandan.debugtimer.gradleplugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.bsh.commands.dir
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileOutputStream

/**
 * Created by xiandanin on 2021/3/11 9:53
 */
class DebugTimerPlugin : Transform(), Plugin<Project> {
    override fun getName(): String {
        return javaClass.simpleName
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun apply(project: Project) {
        project.extensions.getByType(AppExtension::class.java).registerTransform(this)
    }

    override fun transform(transformInvocation: TransformInvocation) {
        val outputProvider = transformInvocation.outputProvider
        val isIncremental = transformInvocation.isIncremental
        if (!isIncremental) {
            outputProvider.deleteAll()
        }

        val context = transformInvocation.context
        val inputs = transformInvocation.inputs
        inputs.forEach {
            it.directoryInputs.forEach { directoryInput ->
                handleDirectoryInputs(context, directoryInput, outputProvider)
            }
        }
    }

    private fun handleDirectoryInputs(context: Context, directoryInput: DirectoryInput, outputProvider: TransformOutputProvider) {
        val regex = "^((?!BuildConfig\\.class|R\\.class).)*\\.class((?!BuildConfig\\.class|R\\.class).)*$".toRegex()
        directoryInput.file.walk()
                .filter { it.isFile && regex.matches(it.name) }
                .forEach {
                    handleClassFile(it)
                }

    }

    private fun handleClassFile(classFile: File) {
        println("handleClassFile--->${classFile.absolutePath}")
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val classVisitor = DebugTimerClassVisitor(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        val codeByteArray = classWriter.toByteArray()
        val fos = FileOutputStream(classFile.absolutePath)
        fos.write(codeByteArray)
        fos.close()

    }


}