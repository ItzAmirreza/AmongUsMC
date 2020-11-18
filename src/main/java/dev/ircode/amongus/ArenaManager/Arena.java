package dev.ircode.amongus.ArenaManager;
import dev.ircode.amongus.Enums.GameState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class Arena {

    private final String name;
    private String worldName = null;
    private final int min_players;
    private final int max_players;
    private Location waitingLocation = null;
    private ArrayList<Location> spawnLocations = null;
    private HashMap<Location, String> sabotagesLocations = null;
    private HashMap<Location, String> TasksLocations = null;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> imposters = new ArrayList<>();
    private ArrayList<Player> crewmates = new ArrayList<>();
    private GameState gameState = GameState.WAITING;
    private int passedTime = 0;
    private boolean readyness;



    public Arena(String name, int min_players, int max_players) {
        this.name = name;
        this.min_players = min_players;
        this.max_players = max_players;
        this.readyness = false;
    }

    public int getPassedTime() {
        return this.passedTime;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public void removeCrewmates(Player player) {
        this.crewmates.remove(player);
    }

    public void addCrewmates(Player player) {
        this.crewmates.add(player);
    }

    public void removeImposter(Player player) {
        this.imposters.remove(player);
    }

    public void addImposter(Player player) {
        this.imposters.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void setPassedTime(int adad) {
        this.passedTime = adad;
    }

    public void setWaitingLocation(Location location) {
        this.waitingLocation = location;
    }

    public void setSpawnLocations(ArrayList<Location> list) {
        this.spawnLocations = list;
    }

    public boolean addSpawnLocation(Location loc) {
        if (this.spawnLocations.size() + 1 > this.max_players) {
            return false;
        } else {
            this.spawnLocations.add(loc);
            return true;
        }
    }

    public boolean removeLastSpawnLocation() {

        if (!(this.spawnLocations.size() == 0)) {

            this.spawnLocations.remove(this.spawnLocations.get(this.spawnLocations.size() - 1));
            return true;
        } else {

            return false;
        }
    }

    public void setSabotageLocations(HashMap<Location, String> hash) {

        this.sabotagesLocations = hash;

    }

    public void addSabotageLocation(Location loc, String sabotageType) {

        this.sabotagesLocations.put(loc, sabotageType);

    }

    public boolean removeSabotage(Location loc) {
        if (this.sabotagesLocations.containsKey(loc)) {

            this.sabotagesLocations.remove(loc);
            return true;

        } else {

            return false;

        }
    }

    public HashMap<Location, String> getSabotagesLocations() {
        return this.sabotagesLocations;
    }

    public void setTasksLocations(HashMap<Location, String> hash) {
        this.TasksLocations = hash;
    }

    public void addTasksLocations(Location loc, String sabotageType) {
        this.TasksLocations.put(loc, sabotageType);
    }

    public boolean removeTask(Location loc) {
        if (this.TasksLocations.containsKey(loc)) {
            this.TasksLocations.remove(loc);
            return true;

        } else {

            return false;

        }
    }
    public HashMap<Location, String> getTasksLocations() {
        return this.TasksLocations;
    }

    public boolean isReady() {
        return this.readyness;
    }

    public void setReadyness(boolean ready) {

        this.readyness = ready;
    }

    public void setWorldName(String str) {
        this.worldName = str;
    }

    public String getWorldName() {
        return this.worldName;
    }


    public String getName() {
        return this.name;
    }


    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Player> getImposters() {
        return this.imposters;
    }
    public ArrayList<Player> getCrewmates() {
        return this.crewmates;
    }
    public GameState getGameState() {
        return this.gameState;
    }

    public int getMin_playersCount() {
        return this.min_players;
    }
    public int getMax_playersCount() {
        return this.max_players;
    }
    public Location getWaitingLocation() {
        return this.waitingLocation;
    }
    public ArrayList<Location> getSpawnLocations() {
        return this.spawnLocations;
    }
}
