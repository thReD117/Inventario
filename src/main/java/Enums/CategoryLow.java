package Enums;
public enum CategoryLow implements CategoryEnumerable{
    PANTS("Pantalón"),
    JEANS("Jeans"),
    SHORTS("Pantalón corto"),
    SKIRTS("Falda"),
    DRESSES("Vestido"),
    CULOTTES("Pantalón culotte"),
    LEGGINGS("Leggings"),
    TROUSERS("Pantalones"),
    CAPRI("Pantalón Capri"),
    JOGGERS("Pantalón jogger"),
    CARGO("Pantalón cargo"),
    OVERALLS("Mono"),
    BERMUDA("Bermuda"),
    SWEATPANTS("Pantalón de chándal");

    private final String displayName;

    CategoryLow(String displayName) {
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
