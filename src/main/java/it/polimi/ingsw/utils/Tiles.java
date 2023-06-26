package it.polimi.ingsw.utils;


import java.io.Serializable;

/**
 * Enumeration of the different kind of tile that is possible to use
 */
public enum Tiles implements Serializable {

    /**
     * Green tile.
     */
    GREEN(new String[]{"/images/item_tiles/Gatti1.1.png", "/images/item_tiles/Gatti1.2.png", "/images/item_tiles/Gatti1.3.png"}),
    /**
     * Blue tile.
     */
    BLUE(new String[]{"/images/item_tiles/Cornici1.1.png", "/images/item_tiles/Cornici1.2.png", "/images/item_tiles/Cornici1.3.png"}),
    /**
     * Yellow tile.
     */
    YELLOW(new String[]{"/images/item_tiles/Giochi1.1.png", "/images/item_tiles/Giochi1.2.png", "/images/item_tiles/Giochi1.3.png"}),
    /**
     * White tile.
     */
    WHITE(new String[]{"/images/item_tiles/Libri1.1.png", "/images/item_tiles/Libri1.2.png", "/images/item_tiles/Libri1.3.png"}),
    /**
     * Pink tile.
     */
    PINK(new String[]{"/images/item_tiles/Piante1.1.png", "/images/item_tiles/Piante1.2.png", "/images/item_tiles/Piante1.3.png"}),
    /**
     * Light blue tile.
     */
    LIGHT_BLUE(new String[]{"/images/item_tiles/Trofei1.1.png", "/images/item_tiles/Trofei1.2.png", "/images/item_tiles/Trofei1.3.png"}),
    /**
     * Not allowed tile.
     */
    NOT_ALLOWED(null),
    /**
     * Empty tile.
     */
    EMPTY(null),
    /**
     * Position tile used by print common objective in Command Line Interface.
     */
    POSITION(null);



    /**
     * Array used to save all tile images path.
     */
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
