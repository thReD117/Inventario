package Enums;
public enum CategorySup implements CategoryEnumerable{
    T_SHIRTS("Camiseta"),
    SHIRTS("Camisa"),
    BLOUSES("Blusa"),
    SWEATERS("Suéter"),
    HOODIES("Sudadera con capucha"),
    JACKETS("Chaqueta"),
    COATS("Abrigo"),
    CARDIGANS("Cárdigan"),
    VESTS("Chaleco"),
    TANK_TOPS("Camiseta sin mangas"),
    PULLOVERS("Suéter de cuello cerrado"),
    TUNICS("Túnica"),
    BUTTON_DOWN_SHIRTS("Camisa abotonada");

    private final String displayName;

    CategorySup(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getEnumName() {
        return name();
    }
}