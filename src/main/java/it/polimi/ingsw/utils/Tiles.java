package it.polimi.ingsw.utils;


import java.io.Serializable;

/**
 * Enumeration of the different kind of tile that is possible to use
 */
public enum Tiles implements Serializable {

    GREEN(new String[]{"/images/item_tiles/Gatti1.1.png", "/images/item_tiles/Gatti1.2.png", "/images/item_tiles/Gatti1.3.png"}),
    BLUE(new String[]{"/images/item_tiles/Cornici1.1.png", "/images/item_tiles/Cornici1.2.png", "/images/item_tiles/Cornici1.3.png"}),
    YELLOW(new String[]{"/images/item_tiles/Giochi1.1.png", "/images/item_tiles/Giochi1.2.png", "/images/item_tiles/Giochi1.3.png"}),
    WHITE(new String[]{"/images/item_tiles/Libri1.1.png", "/images/item_tiles/Libri1.2.png", "/images/item_tiles/Libri1.3.png"}),
    PINK(new String[]{"/images/item_tiles/Piante1.1.png", "/images/item_tiles/Piante1.2.png", "/images/item_tiles/Piante1.3.png"}),
    LIGHT_BLUE(new String[]{"/images/item_tiles/Trofei1.1.png", "/images/item_tiles/Trofei1.2.png", "/images/item_tiles/Trofei1.3.png"}),
    NOT_ALLOWED(null),
    EMPTY(null),
    POSITION(null);

    private final String[] image;

    /**
     * Initialize the tile with the right position of corresponding image
     * @param image positions of the corresponding image
     */
    Tiles(String[] image){
        this.image=image;
    }

    /**
     * <strong>Getter</strong> -> Returns the positions of the image
     * @return positions of the image
     */
    public  String[] getImage(){
        return image;
    }


}
