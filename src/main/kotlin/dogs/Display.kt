package dogs

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import kotlin.toString

class Display {

    fun show() {
        val textArea = JTextArea().apply {
            isEditable = false
            font = Font(Font.SANS_SERIF, Font.ITALIC, 16)
            margin = Insets(32, 32, 32, 32)
        }
        val scrollPane = JScrollPane(textArea)
        JFrame().apply {
            isVisible = true
            size = Dimension(400, 400)
            isResizable = false
            add(scrollPane)
        }
        DogsRepository.Companion.getInstance("sango")
            .dogs
            .joinToString("\n")
            .let { textArea.text = it }
    }

}