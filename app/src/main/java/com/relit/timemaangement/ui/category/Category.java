package com.relit.timemaangement.ui.category;

public class Category {
    private final int id;
    private final String name;
    private final String shortcut;
    private final int color;
    private final int iconID;

    public Category(String name, String shortcut, int iconID, int color) {
        this(0, name, shortcut, iconID, color);
    }

    public Category(int id, String name, String shortcut, int iconID, int color) {
        this.id = id;
        this.name = name;
        this.shortcut = shortcut;
        this.color = color;
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public int getColor() {
        return color;
    }

    public int getIconID() {
        return iconID;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortcut='" + shortcut + '\'' +
                ", color=" + color +
                ", iconID=" + iconID +
                '}';
    }
}
