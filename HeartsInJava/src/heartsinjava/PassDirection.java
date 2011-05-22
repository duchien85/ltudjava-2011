package heartsinjava;

// TODO: Java enums are new to me so there may be a better way of doing the
//       "next" pattern.
// NOTE: Think of position of players as follows:
//       index 0 in "south" position
//       index 1 in "west" position
//       index 2 in "north" position
//       index 3 in "east" position
public enum PassDirection {

    LEFT {

        public PassDirection next() {
            return PassDirection.RIGHT;
        }

        public int target(int index) {
            return index == 3 ? 0 : (index + 1);
        }
    },
    RIGHT {

        public PassDirection next() {
            return PassDirection.ACROSS;
        }

        public int target(int index) {
            return index == 0 ? 3 : (index - 1);
        }
    },
    ACROSS {

        public PassDirection next() {
            return PassDirection.EAT;
        }

        public int target(int index) {
            return index > 1 ? (index - 2) : (index + 2);
        }
    },
    EAT {

        public PassDirection next() {
            return PassDirection.LEFT;
        }

        public int target(int index) {
            return index;
        }
    };

    public abstract PassDirection next();

    public abstract int target(int index);
}