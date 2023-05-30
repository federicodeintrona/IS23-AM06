package it.polimi.ingsw.utils;


import java.io.Serializable;
import java.util.Random;

public enum Tiles implements Serializable {

    GREEN(new String[]{"css/images/item tiles/Gatti1.1.png", "css/images/item tiles/Gatti1.2.png","css/images/item tiles/Gatti1.3.png"}),
    BLUE(new String[]{"css/images/item tiles/Cornici1.1.png", "css/images/item tiles/Cornici1.2.png","css/images/item tiles/Cornici1.3.png"}),
    YELLOW(new String[]{"css/images/item tiles/Giochi1.1.png", "css/images/item tiles/Giochi1.2.png","css/images/item tiles/Giochi1.3.png"}),
    WHITE(new String[]{"css/images/item tiles/Libri1.1.png", "css/images/item tiles/Libri1.2.png","css/images/item tiles/Libri1.3.png"}),
    PINK(new String[]{"css/images/item tiles/Piante1.1.png", "css/images/item tiles/Piante1.2.png","css/images/item tiles/Piante1.3.png"}),
    LIGHT_BLUE(new String[]{"css/images/item tiles/Trofei1.1.png", "css/images/item tiles/Trofei1.2.png","css/images/item tiles/Trofei1.3.png"}),
    NOT_ALLOWED(null),
    EMPTY(null),
    POSITION(null);

    private final String[] image;

    Tiles(String[] image){
        this.image=image;
    }

    public  String[] getImage(){
        return image;
    }


}
