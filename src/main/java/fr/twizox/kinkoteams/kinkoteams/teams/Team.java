package fr.twizox.kinkoteams.kinkoteams.teams;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@DatabaseTable(tableName = "team")
public class Team {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private UUID owner;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private HashMap<UUID, String> members;

    public Team() {}

    public Team(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
    }

    public Team(UUID owner, String name, HashMap<UUID, String> members) {
        this.owner = owner;
        this.name = name;
        this.members = members;
    }

    public Team(UUID owner, String name, Player member) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        members.put(member.getUniqueId(), member.getName());
    }

    public UUID getOwnerUuid() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Map<UUID, String> getMembers() {
        return members;
    }

    public Set<UUID> getMemberUuids() {
        return members.keySet();
    }

    public void addMember(UUID uuid, String name) {
        this.members.put(uuid, name);
    }

    public void removeMember(UUID uuid) {
        this.members.remove(uuid);
    }

    public boolean hasMember(UUID uuid) {
        return this.members.containsKey(uuid);
    }

    public boolean hasMember(String name) {
        return this.members.containsValue(name);
    }

    public boolean isOwner(UUID uuid) {
        return uuid.equals(owner);
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }


}
