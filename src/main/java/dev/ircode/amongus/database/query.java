package dev.ircode.amongus.database;

public class query {
    public final static String ArenaTable = "CREATE TABLE IF NOT EXISTS `arena` (\n" +
            "  `uuid` varchar(255) NOT NULL,\n" +
            "  `name` varchar(255) NOT NULL,\n" +
            "  `min_player` int(1) NOT NULL,\n" +
            "  `max_player` int(2) NOT NULL,\n" +
            "  `world` varchar(255) NOT NULL,\n" +
            "  `spawn_locations` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`spawn_locations`)),\n" +
            "  `waiting_location` varchar(255) NOT NULL\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n";

}
