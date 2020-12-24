package sample;

public class Item {
    public String type, name, folder, band;
    public int position;

    public Item(String name, String type, String folder, int position) {
        this.name = name;
        this.type = type;
        this.folder = folder;
        this.position = position;
    }

    public Item(String name, String type, String folder, String band, int position) {
        this(name, type, folder, position);
        this.band = band;
    }

    public String getType() { return type; }

    public String getName() { return name; }

    public String getFolder() { return folder; }

    public String getBand() { return band; }

    public int getPosition() { return position; }
}
