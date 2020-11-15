package dev.ircode.amongus.ArenaManager;
import dev.ircode.amongus.Enums.GameState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class Arena {

    private final String name;
    private final UUID uuid;
    private final String worldName;
    private final int min_players;
    private final int max_players;
    private final int requiredplayers;
    private final Location waitingLocation;
    private final ArrayList<Location> spawnLocations;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> imposters = new ArrayList<>();
    private ArrayList<Player> crewmates = new ArrayList<>();
    private GameState gameState = GameState.WAITING;
    private int passedTime;



    public Arena(String name, UUID uuid, String worldName, int min_players, int max_players, int requiredplayers, Location waitingLocation, ArrayList<Location> spawnLocations) {
        this.name = name;
        this.uuid = uuid;
        this.min_players = min_players;
        this.max_players = max_players;
        this.requiredplayers = requiredplayers;
        this.waitingLocation = waitingLocation;
        this.spawnLocations = spawnLocations;
        this.worldName = worldName;
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

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
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
    public int getRequiredplayersCount() {
        return this.requiredplayers;
    }
    public Location getWaitingLocation() {
        return this.waitingLocation;
    }
    public ArrayList<Location> getSpawnLocations() {
        return this.spawnLocations;
    }
}
