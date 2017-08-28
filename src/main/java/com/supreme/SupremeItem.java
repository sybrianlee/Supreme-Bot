package com.supreme;

public class SupremeItem {

    private final Section section;
    private final String name;
    private final int colorIndex;
    private final Size size;

    public enum Section {
        All("all"),
        JACKETS("jackets"),
        SHIRTS("shirts"),
        TOPS_SWEATERS("tops_sweaters"),
        SWEATSHIRTS("sweatshirts"),
        PANTS("pants"),
        SHORTS("shorts"),
        TSHIRTS("t-shirts"),
        HATS("hats"),
        BAGS("bags"),
        ACCESSORIES("accessories"),
        SKATE("skate");

        private final String name;

        Section(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Size {
        SMALL("Small"),
        MEDIUM("Medium"),
        LARGE("Large"),
        XLARGE("XLarge");

        private final String size;

        Size(String size) {
            this.size = size;
        }

        public String getValue() {
            return size;
        }
    }

    public SupremeItem(Section section, String name, int colorIndex, Size size) {
        this.section = section;
        this.name = name;
        this.colorIndex = colorIndex;
        this.size = size;
    }

    public Section getSection() {
        return section;
    }

    public String getName() {
        return name;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "SupremeItem{" +
                "name='" + name + '\'' +
                ", colorIndex='" + colorIndex + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
