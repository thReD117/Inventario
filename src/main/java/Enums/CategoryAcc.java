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
    
    private String displayName;
    CategoryAcc(String displayName){
        this.displayName = displayName;
    }
    
    @Override
    public String getCategory(){
        return displayName;
    }
}
