/*
 * Project and Training 2: Space News - Computer Science, Berner Fachhochschule
 */

package ch.bfh.bti3001.game2048

import javafx.geometry.Pos
import javafx.stage.Stage
import tornadofx.*

class Game2048App : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.width = 500.0
        stage.height = 500.0
        super.start(stage)
    }
}

class MainView : View("ch.bfh.game2048.Game2048App") {
    override val root = hbox(alignment = Pos.CENTER) {
		val javaVersion =  System.getProperty("java.version");
		val tornadofxVersion =  "1.7.20"
        label("Hello, TornadoFX " + tornadofxVersion +
			  " running on Java " + javaVersion + ".")
    }
}

fun main(args: Array<String>) {
    launch<Game2048App>(args)
}
