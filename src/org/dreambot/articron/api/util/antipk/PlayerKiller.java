package org.dreambot.articron.api.util.antipk;

public class PlayerKiller {

    private String username;
    private int world;

    public PlayerKiller(String username, int world) {
        this.username = username;
        this.world = world;
    }

    public int getWorld() {
        return world;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerKiller) {
            PlayerKiller o = (PlayerKiller) obj;
            return o.getUsername().equals(this.username);
        }
        return false;
    }
}
