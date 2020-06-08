package repository;

import entity.User;
import entity.Game;
import entity.GameStudio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String NEW_URL = "jdbc:postgresql://localhost:5432/";
    private static User user;

    public static Connection connect(User thisUser) {
        try {
            Connection connection = DriverManager.getConnection(URL, thisUser.name, thisUser.password);
            user = thisUser;
            return createDatabase(thisUser, connection);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Game> findGameByName(Connection connection, String calling) {
        List<Game> games = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM find_games_by_calling(?)");
            statement.setString(1, calling);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                games.add(
                        new Game(result.getInt("id"), result.getInt("gameStudio_id"), result.getString("calling"),
                                result.getInt("programmers")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

    public static Connection createDatabase(User user, Connection connection) {
        PreparedStatement properCase = null;
        try {
            properCase = connection.prepareStatement("SELECT * FROM create_db_if_not_exist (?)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {

            properCase.setString(1, user.nameofDataBase);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            properCase.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Connection userBaseConnection = null;
        try {
            userBaseConnection = DriverManager
                    .getConnection(NEW_URL + user.nameofDataBase, user.name, user.password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            pushing(userBaseConnection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        createTables(userBaseConnection);
        return userBaseConnection;
    }

    private static void createTables(Connection connection) {
        try {
            connection.prepareStatement("SELECT * FROM create_tables ()").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection.prepareStatement("SELECT * FROM set_trigger ()").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void postgresVoidFunction(Connection connection, String sqlFunction) {
        try {
            connection.prepareStatement("SELECT * FROM $sqlFunction").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<GameStudio> getGameStudios(Connection connection) {
        List<GameStudio> gameStudios = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM get_gameStudios()");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                gameStudios.add(new GameStudio(result.getInt("id"), result.getString("name"),
                        result.getInt("games_programmers")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameStudios;
    }

    public static List<Game> getGames(Connection connection) {
        List<Game> games = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM get_games()");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                games.add(new Game(result.getInt("id"), result.getInt("gameStudio_id"),
                        result.getString("calling"), result.getInt("programmers")));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return games;
    }

    public static void clearGameStudios(Connection connection) {
        postgresVoidFunction(connection, "clear_gameStudios()");
    }

    public static void clearGames(Connection connection) {
        postgresVoidFunction(connection, "clear_games()");
    }

    public static void clearTables(Connection connection) {
        postgresVoidFunction(connection, "clear_tables()");
    }

    public static void addGame(Connection connection, Game game) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM add_game(?, ?, ?, ?)");
            statement.setInt(1, game.id);
            statement.setInt(2, game.gameStudioId);
            statement.setString(3, game.calling);
            statement.setInt(4, game.programmers);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void addGameStudio(Connection connection, GameStudio gameStudio) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM add_gameStudio(?, ?)");
            statement.setInt(1, gameStudio.id);
            statement.setString(2, gameStudio.name);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void deleteGameStudioByName(Connection connection, String name) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM delete_gameStudios_by_name(?)");
            statement.setString(1, name);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void deleteGameByName(Connection connection, String calling) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM delete_games_by_calling(?)");
            statement.setString(1, calling);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void deleteGameById(Connection connection, int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM delete_games_by_id(?)");
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public static void deleteGameStudioById(Connection connection, int id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM delete_gameStudios_by_id(?)");
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static List<GameStudio> findGameStudioByName(Connection connection, String name) {
        List<GameStudio> gameStudios = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM find_gameStudios_by_name(?)");
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                gameStudios.add(
                        new GameStudio(result.getInt("id"), result.getString("name"),
                                result.getInt("games_programmers")));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return gameStudios;
    }



    public static void deleteDatabase(Connection connection) {
        if (user != null) {
            try {
                connection.close();
                connection = DriverManager.getConnection(URL, user.name, user.password);
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM delete_database(?)");
                statement.setString(1, user.nameofDataBase);
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            user = null;
        }
    }
    public static void pushing(Connection connection) throws SQLException {
        String sqlFunctions = """
                    CREATE OR REPLACE FUNCTION create_tables() RETURNS VOID AS $$
                    BEGIN
                    	CREATE TABLE IF NOT EXISTS GameStudios (
                    		id INTEGER PRIMARY KEY,
                    		name TEXT,
                    		games_programmers INTEGER CHECK(games_programmers >= 0) DEFAULT 0
                    	);
                    	CREATE INDEX name_index ON GameStudios (name);
                    	
                    	CREATE TABLE IF NOT EXISTS Games (
                    		id INTEGER PRIMARY KEY,
                    		gameStudio_id INTEGER REFERENCES GameStudios(id),
                    		calling TEXT,
                    		programmers INTEGER CHECK(programmers >= 0)
                    	);
                    	CREATE INDEX calling_index ON Games (calling);
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION delete_tables() RETURNS VOID AS $$
                    BEGIN
                    	DROP TABLE IF EXISTS GameStudios CASCADE;
                    	DROP TABLE IF EXISTS Games CASCADE;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION get_gameStudios() RETURNS
                    TABLE(
                    	id INTEGER,
                    	name TEXT,
                    	games_programmers INTEGER
                    ) AS $$
                    BEGIN
                    	RETURN QUERY SELECT * FROM GameStudios;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION get_games() RETURNS
                    TABLE(
                    	id INTEGER,
                    	gameStudio_id INTEGER,
                    	calling TEXT,
                    	programmers INTEGER
                    ) AS $$
                    BEGIN
                    	RETURN QUERY SELECT * FROM Games;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION clear_gameStudios() RETURNS VOID AS $$
                    BEGIN
                    	TRUNCATE GameStudios;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION clear_games() RETURNS VOID AS $$
                    BEGIN
                    	TRUNCATE Games;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION clear_tables() RETURNS VOID AS $$
                    BEGIN
                    	TRUNCATE GameStudios CASCADE;
                    	TRUNCATE Games CASCADE;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION add_game(integer, integer, text, integer) RETURNS VOID AS $$
                    BEGIN
                    	INSERT INTO Games VALUES($1, $2, $3, $4);
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION add_gameStudio(integer, text) RETURNS VOID AS $$
                    BEGIN
                    	INSERT INTO GameStudios VALUES($1, $2);
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION delete_games_by_calling(text) RETURNS VOID AS $$
                    BEGIN
                    	DELETE FROM games WHERE calling = $1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION delete_gameStudios_by_name(text) RETURNS VOID AS $$
                    BEGIN
                    	DELETE FROM Games WHERE gameStudio_id in (SELECT gameStudios.id FROM GameStudios WHERE name=$1);
                    	DELETE FROM GameStudios WHERE name=$1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION delete_games_by_id(integer) RETURNS VOID AS $$
                    BEGIN
                    	DELETE FROM Games WHERE id = $1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION delete_gameStudios_by_id(integer) RETURNS VOID AS $$
                    BEGIN
                    	DELETE FROM Games WHERE gameStudio_id = $1;
                    	DELETE FROM GameStudios WHERE id = $1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION find_gameStudios_by_name(text) RETURNS
                    TABLE(
                    	id INTEGER,
                    	name TEXT,
                    	games_programmers INTEGER
                    ) AS $$
                    BEGIN
                    	RETURN QUERY SELECT * FROM GameStudios WHERE GameStudios.name=$1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION find_games_by_calling(text) RETURNS
                    TABLE(
                    	id INTEGER,
                    	gameStudio_id INTEGER,
                    	calling TEXT,
                    	programmers INTEGER
                    ) AS $$
                    BEGIN
                    	RETURN QUERY SELECT * FROM Games WHERE Games.calling=$1;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION calculate_total_programmers() RETURNS TRIGGER AS $$
                    BEGIN
                    	UPDATE gameStudios SET games_programmers=(SELECT SUM(programmers) FROM games WHERE gameStudio_id = gameStudios.id);
                    	UPDATE gameStudios SET games_programmers=0 WHERE games_programmers IS null;
                    	RETURN NEW;
                    END; $$
                    LANGUAGE 'plpgsql';
                    CREATE OR REPLACE FUNCTION set_trigger() RETURNS VOID AS $$
                    BEGIN
                    	CREATE TRIGGER total_programmers_trigger AFTER INSERT OR UPDATE OR DELETE OR TRUNCATE ON games EXECUTE PROCEDURE calculate_total_programmers();
                    END; $$
                    LANGUAGE 'plpgsql';
                """.trim();
        connection.createStatement().execute(sqlFunctions);
    }

}
