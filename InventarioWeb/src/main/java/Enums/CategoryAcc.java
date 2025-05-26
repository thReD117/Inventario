package Enums;
public enum CategoryAcc implements CategoryEnumerable{
    CAPS("Gorra"),
    HATS("Sombrero"),
    HEADBANDS("Diadema"),
    NECKLACES("Collar"),
    SCARVES("Bufanda"),
    TIES("Corbata"),
    RINGS("Anillo"),
    BRACELETS("Pulsera"),
    BELTS("Cinturon"),
    GLASSES("Gafas");
    
    private final String displayName;
    CategoryAcc(String displayName){
        this.displayName = displayName;
    }
    
    @Override
    public String getDisplayName(){
        return displayName;
    }

    @Override
    public String getEnumName() {
        return this.name();
    }
}
