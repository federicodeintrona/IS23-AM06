package it.polimi.ingsw;



public enum Tiles {
    GREEN{
        @Override
        public String toString() {
            return "\u001B[32m" +" G "+ "\u001B[0m";
        }
    },
    BLUE{
        @Override
        public String toString() {
            return "\u001B[34m" +" B "+"\u001B[0m";
        }
    },
    YELLOW{
        @Override
        public String toString() {
            return "\u001B[33m" +" Y "+ "\u001B[0m";
        }
    },
    WHITE{
        @Override
        public String toString() {
            return " W ";
        }
    },
    PINK{
        @Override
        public String toString() {
            return "\u001B[35m P \u001B[0m";
        }
    },
    LIGHT_BLUE{
        @Override
        public String toString() {
            return "\u001B[36m C \u001B[0m";
        }
    },
    NOTALLOWED{
        @Override
        public String toString() {
            return " ";
        }
    },
    EMPTY{
        @Override
        public String toString() {
            return " ";
        }
    };
}
