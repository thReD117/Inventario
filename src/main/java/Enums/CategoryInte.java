package Enums;
public enum CategoryInte implements CategoryEnumerable{
    BRAS("Sostén"),
    PANTIES("Braga"),
    BODYSUITS("Body"),
    SHAPEWEAR("Faja"),
    LINGERIE("Lencería"),
    UNDERSHIRTS("Camiseta interior"),
    TIGHTS("Pantyhose"),
    BOXERS("Boxer"),
    BRIEFS("Bóxers ajustados"),
    TRUNKS("Bóxers largos"),
    SLIPS("Pantis"),
    PAJAMAs("Pijama"),
    BATHROBES("Bata"),
    SOCKS("Calcetín");

    private final String displayName;

    CategoryInte(String displayName) {
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
