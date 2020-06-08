package presentation

import entity.User
import entity.Game
import entity.GameStudio
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import repository.Main
import java.sql.Connection


class MainView : Application() {

    private lateinit var root: Pane
    private lateinit var gameStudioTable: TableView<GameStudio>
    private lateinit var gamesTable: TableView<Game>

    private var isGameStudiosShowed = true
    private var isUserLogin = false
    private var actualConnection: Connection? = null

    override fun start(stage: Stage) {
        gameStudioTable = createGameStudiosTable()
        gamesTable = createGamesTable()

        val menuBar = createMenuBar()

        root = VBox()
        root.children.add(menuBar)

        stage.title = "My Application"
        val scene = Scene(root, 1000.0, 500.0)
        stage.icons.add(Image("icon.jpg"))
        stage.scene = scene
        scene.stylesheets.add(("css/Login.css"))
        stage.apply {
            initStyle(StageStyle.DECORATED)
        }
        stage.show()
    }

    private val gameStudios: ObservableList<GameStudio>
        get() {
            return FXCollections.observableArrayList()
        }

    private val games: ObservableList<Game>
        get() {
            return FXCollections.observableArrayList()
        }

    private fun createGameStudiosTable(): TableView<GameStudio> {
        val idColumn = TableColumn<GameStudio, Long>("id")
        val nameColumn = TableColumn<GameStudio, String>("name")
        val peopleColumn = TableColumn<GameStudio, Long>("programmers")

        idColumn.cellValueFactory = PropertyValueFactory("id")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        peopleColumn.cellValueFactory = PropertyValueFactory("people")

        idColumn.sortType = TableColumn.SortType.DESCENDING
        val table = TableView<GameStudio>()
        peopleColumn.minWidth = 100.0

        table.columns.addAll(idColumn, nameColumn, peopleColumn)
        table.items = gameStudios

        return table
    }

    private fun createGamesTable(): TableView<Game> {

        val idColumn = TableColumn<Game, Long>("id")
        val gameStudioIdColumn = TableColumn<Game, Long>("studio id")
        val callingColumn = TableColumn<Game, String>("name")
        val programmersColumn = TableColumn<Game, Long>("programmers")

        idColumn.cellValueFactory = PropertyValueFactory("id")
        gameStudioIdColumn.cellValueFactory = PropertyValueFactory("gameStudioId")
        callingColumn.cellValueFactory = PropertyValueFactory("calling")
        programmersColumn.cellValueFactory = PropertyValueFactory("programmers")

        idColumn.sortType = TableColumn.SortType.DESCENDING

        val table = TableView<Game>()
        table.columns.addAll(idColumn, gameStudioIdColumn, callingColumn, programmersColumn)

        table.items = games
        return table
    }

    private fun createMenuBar(): MenuBar {
        val menuBar = MenuBar()



        val gamesMen = Menu("Games")
        val gamenu = Menu("Game Studio")

        val mainmen = Menu("Database")
        val createorChangeDb = MenuItem("Connect/Create").apply {
            onAction = EventHandler {
                val dialog = Stage().apply {
                    initStyle(StageStyle.DECORATED)
                    icons.add(Image("icon.jpg"))
                }
                dialog.initModality(Modality.APPLICATION_MODAL);
                val box = VBox().apply {
                    alignment = Pos.CENTER
                }
                val buttons = HBox().apply {
                    alignment = Pos.CENTER
                }
                val userName = TextField().apply {
                    promptText = "user"
                }
                val password = TextField().apply {
                    promptText = "password"
                }
                val databaseName = TextField().apply {
                    promptText = "Database name"
                }
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val btnLogin = Button("Login").apply {
                    onMouseClicked = EventHandler {
                        if (userName.text != "" && password.text != "" && databaseName.text != "") {
                            val user = User(userName.text, password.text, databaseName.text)
                            actualConnection = Main.connect(user)
                            if (actualConnection != null) {
                                isUserLogin = true
                                isGameStudiosShowed = !isGameStudiosShowed
                                switchTables()
                                refreshData()
                                dialog.close()
                            }
                        }
                    }
                }
                btnLogin.background = Background(BackgroundFill(Color.LIGHTGREEN, CornerRadii(100.0), Insets.EMPTY))
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                btnBack.background = Background(BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii(100.0), Insets.EMPTY))

                buttons.children.addAll(
                        btnLogin,
                        btnBack)
                val n0 = TextField()
                n0.isVisible = false
                box.children.addAll(
                        n0,
                        userName,
                        n2,
                        password,
                        n1,
                        databaseName,
                        n3,
                        buttons,
                        n4)
                val scene = Scene(box)
                dialog.scene = scene
                scene.stylesheets.add(("css/Login.css"))
                dialog.show()
            }
        }
        val deleteDb = MenuItem("Delete").apply {
            onAction = EventHandler {
                isUserLogin = false
                gameStudioTable.items.clear()
                gamesTable.items.clear()
            }
        }

        val adding = Menu("Other").apply {

        }
        val addgs2 = MenuItem("Change Table").apply {
            onAction = EventHandler { switchTables() }

        }
        val addgs3 = MenuItem("Refresh").apply {
            onAction = EventHandler { refreshData() }

        }
        val addgs = MenuItem("add").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val idField = TextField().apply {
                    promptText = "studio id"
                }
                val nameField = TextField().apply {
                    promptText = "name"
                }

                val btnAdd = Button("Add").apply {

                    onMouseClicked = EventHandler {
                        if (idField.text != "" && nameField.text != "") {
                            val gameStudio = GameStudio(idField.text.toInt(), nameField.text.toString(), 0)
                            Main.addGameStudio(actualConnection!!, gameStudio)
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                btnBack.background = Background(BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii(100.0), Insets.EMPTY))
                btnAdd.background = Background(BackgroundFill(Color.LIGHTGREEN, CornerRadii(100.0), Insets.EMPTY))
                buttons.children.addAll(btnAdd, btnBack)
                val n2 = TextField()
                n2.isVisible = false
                val n1 = TextField()
                n1.isVisible = false
                val n3 = TextField()
                n3.isVisible = false
                val n4 = TextField()
                n4.isVisible = false
                val n5 = TextField()
                n5.isVisible = false
                val n0 = TextField()
                n0.isVisible = false
                box.children.addAll(
                        n0,
                        idField,
                        n1,
                        nameField,
                        n2,
                        buttons,
                n3)

                val scene = Scene(box)
                dialog.scene = scene
                scene.stylesheets.add(("css/Login.css"))
                dialog.show()
            }
        }
        val addg = MenuItem("add").apply {

            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val idField = TextField().apply {
                    promptText = "id"
                }
                val gameStudioIdField = TextField().apply {
                    promptText = "gameStudioId"
                }
                val callingField = TextField().apply {
                    promptText = "name"
                }
                val programmersField = TextField().apply {
                    promptText = "programmers"
                }

                val btnAdd = Button("Add").apply {

                    onMouseClicked = EventHandler {
                        if (idField.text != "" && gameStudioIdField.text != ""
                                && callingField.text != "" && programmersField.text != ""
                        ) {
                            val game = Game(
                                    idField.text.toInt(),
                                    gameStudioIdField.text.toInt(),
                                    callingField.text,
                                    programmersField.text.toInt()
                            )

                            Main.addGame(actualConnection!!, game)
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnAdd, btnBack)
                val n2 = TextField()
                n2.isVisible = false
                val n1 = TextField()
                n1.isVisible = false
                val n3 = TextField()
                n3.isVisible = false
                val n4 = TextField()
                n4.isVisible = false
                val n5 = TextField()
                n5.isVisible = false
                val n0 = TextField()
                n0.isVisible = false

                box.children.addAll(
                        n0,
                        idField,
                        n1,
                        gameStudioIdField,
                        n2,
                        callingField,
                        n3,
                        programmersField,
                        n4,
                        buttons,
                        n5
                )
                val scene = Scene(box)
                dialog.scene = scene
                scene.stylesheets.add(("css/Login.css"))
                dialog.show()
            }
        }



        val searchname = MenuItem("find by name").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val nameField = TextField().apply {
                    promptText = "name"
                }

                val btnFind = Button("Find").apply {

                    onMouseClicked = EventHandler {
                        if (nameField.text != "") {
                            gamesTable.items.clear()
                            gamesTable.items.addAll(Main.findGameByName(actualConnection!!, nameField.text))
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnFind, btnBack)

                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val n0 = TextField()
                n0.isVisible = false

                box.children.addAll(
                        n0,
                        nameField,
                        n1,
                        buttons,
                        n2
                )
                val scene = Scene(box)
                dialog.scene = scene
                scene.stylesheets.add(("css/Login.css"))
                dialog.show()
            }
        }
        val searchcalling = MenuItem("find by name").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val nameField = TextField().apply {
                    promptText = "name"
                }

                val btnFind = Button("Find").apply {

                    onMouseClicked = EventHandler {
                        if (nameField.text != "") {
                            gameStudioTable.items.clear()
                            gameStudioTable.items.addAll(Main.findGameStudioByName(actualConnection!!, nameField.text))
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnFind, btnBack)
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val n0 = TextField()
                n0.isVisible = false
                box.children.addAll(
                        n0,
                        nameField,
                        n1,
                        buttons,
                        n2
                )
                val scene = Scene(box)
                scene.stylesheets.add(("css/Login.css"))
                dialog.scene = scene
                dialog.show()
            }
        }

        val delte = Menu("Delete")
        val gameid = MenuItem("delete by id").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val idField = TextField().apply {
                    promptText = "id"
                }

                val btnOK = Button("Delete").apply {

                    onMouseClicked = EventHandler {
                        if (idField.text != "") {
                            Main.deleteGameById(actualConnection!!, idField.text.toInt())
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnOK, btnBack)
                val n0 = TextField()
                n0.isVisible = false
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;

                box.children.addAll(
                        n0,
                        idField,
                        n1,
                        buttons,
                        n2
                )
                val scene = Scene(box)
                scene.stylesheets.add(("css/Login.css"))
                dialog.scene = scene
                dialog.show()
            }
        }
        val gamename = MenuItem("delete by name").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val callingField = TextField().apply {
                    promptText = "id"
                }

                val btnOK = Button("Delete").apply {

                    onMouseClicked = EventHandler {
                        if (callingField.text != "") {
                            Main.deleteGameByName(actualConnection!!, callingField.text)
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnOK, btnBack)
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val n0 = TextField()
                n0.isVisible = false
                box.children.addAll(
                        n0,
                        callingField,
                        n2,
                        buttons
                        , n1
                )
                val scene = Scene(box)
                scene.stylesheets.add(("css/Login.css"))
                dialog.scene = scene
                dialog.show()
            }
        }
        val gamesd = MenuItem("delete by id").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val idField = TextField()
                        .apply {
                            promptText = "id"
                        }

                val btnOK = Button("Delete").apply {

                    onMouseClicked = EventHandler {
                        if (idField.text != "") {
                            Main.deleteGameStudioById(actualConnection!!, idField.text.toInt())
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnOK, btnBack)
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val n0 = TextField()
                n0.isVisible = false

                box.children.addAll(
                        n0,
                        idField,
                        n1,
                        buttons,
                        n2
                )
                val scene = Scene(box)
                scene.stylesheets.add(("css/Login.css"))
                dialog.scene = scene
                dialog.show()
            }
        }
        val bycalling = MenuItem("delete by name").apply {
            onAction = EventHandler {
                val dialog = Stage()
                dialog.initStyle(StageStyle.DECORATED)
                dialog.icons.add(Image("icon.jpg"))
                val box = VBox()
                box.alignment = Pos.CENTER
                val buttons = HBox()
                buttons.alignment = Pos.CENTER

                val nameField = TextField()
                        .apply {
                            promptText = "name"
                        }

                val btnOK = Button("Delete").apply {

                    onMouseClicked = EventHandler {
                        if (nameField.text != "") {
                            Main.deleteGameStudioByName(actualConnection!!, nameField.text)
                            refreshData()
                            dialog.close()
                        }
                    }

                }
                val btnBack = Button("Back").apply {
                    onMouseClicked = EventHandler { dialog.close() }
                }
                buttons.children.addAll(btnOK, btnBack)
                val n2 = TextField()
                n2.isVisible = false;
                val n1 = TextField()
                n1.isVisible = false;
                val n3 = TextField()
                n3.isVisible = false;
                val n4 = TextField()
                n4.isVisible = false;
                val n0 = TextField()
                n0.isVisible = false

                box.children.addAll(
                        n0,
                        nameField,
                        n1,
                        buttons,
                        n2
                )
                val scene = Scene(box)
                scene.stylesheets.add(("css/Login.css"))
                dialog.scene = scene
                dialog.show()
            }
        }

        val cleanniung = Menu("Clean All")
        val cg = MenuItem("Delete table").apply {
            onAction = EventHandler {
                gamesTable.items.clear()
                Main.clearGames(actualConnection!!)
            }
        }
        val cgs = MenuItem("Delete table").apply {
            onAction = EventHandler {
                gameStudioTable.items.clear()
                Main.clearGameStudios(actualConnection!!)
            }
        }
        val cleanTable = MenuItem("all").apply {
            onAction = EventHandler {
                gamesTable.items.clear()
                gameStudioTable.items.clear()
                Main.clearTables(actualConnection!!)
            }
        }

        gamesMen.items.addAll(addg,searchname,gameid, gamename, cg)
        gamenu.items.addAll(addgs,searchcalling,gamesd, bycalling, cgs)
        cleanniung.items.addAll(cleanTable)
        adding.items.addAll(addgs2, addgs3)
        mainmen.items.addAll(createorChangeDb, deleteDb)

        menuBar.menus.addAll(mainmen, gamesMen, gamenu, adding, cleanniung)
        return menuBar
    }

    private fun switchTables() {
        if (isUserLogin) {
            if (isGameStudiosShowed) {
                root.children.remove(gameStudioTable)
                root.children.add(gamesTable)
            } else {
                root.children.remove(gamesTable)
                root.children.add(gameStudioTable)
            }
            isGameStudiosShowed = !isGameStudiosShowed
        }
    }

    private fun refreshData() {
        gameStudioTable.items.clear()
        gameStudioTable.items.addAll(Main.getGameStudios(actualConnection!!))
        gamesTable.items.clear()
        gamesTable.items.addAll(Main.getGames(actualConnection!!))
    }
}

fun main() {
    Application.launch(MainView::class.java)
}