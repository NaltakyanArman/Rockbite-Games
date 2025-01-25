package model;

import java.io.Serializable;

public record Item(String name, Rarity rarity) implements Serializable {

    @Override
    public String toString() {
        return String.format("%s %s", name, rarity);
    }
}