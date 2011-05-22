package heartsinjava2;

// Think of position of players as follows:
//       position 0 in "south" position
//       position 1 in "west" position
//       position 2 in "north" position
//       position 3 in "east" position
public enum PassDirection {

    LEFT {

        public PassDirection next() {
            return PassDirection.RIGHT;
        }

        public int target(int position) {
            return position == 3 ? 0 : (position + 1);
        }
    },
    RIGHT {

        public PassDirection next() {
            return PassDirection.ACROSS;
        }

        public int target(int position) {
            return position == 0 ? 3 : (position - 1);
        }
    },
    ACROSS {

        public PassDirection next() {
            return PassDirection.EAT;
        }

        public int target(int position) {
            return position > 1 ? (position - 2) : (position + 2);
        }
    },
    EAT {

        public PassDirection next() {
            return PassDirection.LEFT;
        }

        public int target(int position) {
            return position;
        }
    };

    public abstract PassDirection next();

    public abstract int target(int position);
}