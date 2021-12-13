import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println(Arrays.toString(arrayDiff(new int[]{1, 1, 2, 2, 3, 4, 4, 5}, new int[]{1, 3, 4})));
        System.out.println
          (Arrays.toString
          (DataReverse
          (new int[]{1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0})));
        System.out.println(toCamelCase("this_is_a_string_that_needs_to_go_to_camel_case"));
        System.out.println(greedy(new int[]{1, 2, 1, 1, 5, 5}));
        System.out.println(validParentheses("word((word_two), word_three)"));
        */
        String bruh =
                "..W.W..W.\n" +
                "....W....\n" +
                "...WW....\n" +
                "....W...W\n" +
                "..WW...WW\n" +
                ".....W..W\n" +
                "....W.W..\n" +
                ".W.W.W...\n" +
                ".W...WW..";
        System.out.println(pathFinder(bruh));
    }

    public static int[] arrayDiff(int[] a, int[] b) {
        ArrayList<Integer> ret = new ArrayList<>();
        outer:
        for (int num : a) {
            for (int target : b) {
                if (num == target) continue outer;
            }
            ret.add(num);
        }
        int[] ret_arr = new int[ret.size()];
        for (int i = 0; i < ret.size(); i++) {
            ret_arr[i] = ret.get(i);
        }
        return ret_arr;
    }
    public static int[] DataReverse(int[] data) {
        ArrayList<ArrayList<Integer>> container = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        int count = 0;
        for (int num : data) {
            count++;
            b.add(num);
            if (count % 8 == 0) {
                container.add(b);
                b = new ArrayList<>();
            }
        }
        int[] ret = new int[data.length];
        int pointer = 0;
        for (int index = container.size() - 1; index >= 0; index--) {
            for (int num : container.get(index)) {
                ret[pointer] = num;
                pointer++;
            }
        }
        return ret;
    }
    public static String toCamelCase(String s) {
        StringBuilder ret = new StringBuilder(); boolean cap_next = false;
        for (char ch : s.toCharArray()) {
            if (ch == '-' || ch == '_') {
                cap_next = true;
            }
            else {
                if (cap_next) {
                    ret.append(Character.toUpperCase(ch));
                    cap_next = false;
                }
                else ret.append(ch);
            }
        }
        return ret.toString();
    }
    public static int greedy(int[] dice) {
        int count, score = 0;
        // Runs for every possible dice value
        for (int x : new int[]{1, 2, 3, 4, 5, 6}) {
            count = 0;
            // Runs for every value of the dice array parameter
            for (int i : dice) {
                // Gets amount of times x occurs in dice array
                if (i == x) count++;
            }
            if (count >= 3) {
                if (x == 1) score += 1000;
                else {
                    score += x * 100;
                }
                if (x == 1) score += (count % 3) * 100;
                if (x == 5) score += (count % 3) * 50;
            }
            else if (x == 1 || x == 5) {
                if (x == 1) score += count * 100;
                else score += count * 50;
            }
        }
        return score;
    }
    public static boolean validParentheses(String parens) {
        int depth = 0;
        // For each character in parens
        for (char ch : parens.toCharArray()) {
            // Depth is how many layers of parentheses we are in
            if (ch == '(') depth++;
            else if (ch == ')') {
                depth--;
                // When layer goes under 0 we return false
                if (depth < 0) return false;
            }
        }
        return (depth == 0);
    }
    static int pathFinder(String maze) {
        String[] lines = maze.split("\n");
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
        int count, row = 0;
        for (String line : lines) {
            ArrayList<Tile> grid_line = new ArrayList<>();
            count = 0;
            for (char ch : line.toCharArray()) {
                grid_line.add(new Tile(ch == 'W', count, row));
                count++;
            }
            grid.add(grid_line);
            row++;
        }
        ArrayList<Tile> STACK = new ArrayList<>();
        STACK.add(grid.get(grid.size() - 1).get(grid.get(grid.size() - 1).size() - 1));
        STACK.get(0).cost = 0;
        while (true) {
            Tile current = STACK.get(STACK.size() - 1);
            try {
                // Going Down
                if (!(grid.get(current.row + 1).get(current.index).checked)) {
                    if (!(grid.get(current.row + 1).get(current.index).is_wall)) {
                        if (!(grid.get(current.row + 1).get(current.index).on_stack)) {
                            grid.get(current.row + 1).get(current.index).on_stack = true;
                            grid.get(current.row + 1).get(current.index).cost = current.cost + 1;
                            STACK.add(grid.get(current.row + 1).get(current.index));
                            continue;
                        }
                    }
                    else grid.get(current.row + 1).get(current.index).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Right
                if (!(grid.get(current.row).get(current.index + 1).checked)) {
                    if (!(grid.get(current.row).get(current.index + 1).is_wall)) {
                        if (!(grid.get(current.row).get(current.index + 1).on_stack)) {
                            grid.get(current.row).get(current.index + 1).on_stack = true;
                            grid.get(current.row).get(current.index + 1).cost = current.cost + 1;
                            STACK.add(grid.get(current.row).get(current.index + 1));
                            continue;
                        }
                    }
                    else grid.get(current.row).get(current.index + 1).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Up
                if (!(grid.get(current.row - 1).get(current.index).checked)) {
                    if (!(grid.get(current.row - 1).get(current.index).is_wall)) {
                        if (!(grid.get(current.row - 1).get(current.index).on_stack)) {
                            grid.get(current.row - 1).get(current.index).on_stack = true;
                            grid.get(current.row - 1).get(current.index).cost = current.cost + 1;
                            STACK.add(grid.get(current.row - 1).get(current.index));
                            continue;
                        }
                    }
                    else grid.get(current.row - 1).get(current.index).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Left
                if (!(grid.get(current.row).get(current.index - 1).checked)) {
                    if (!(grid.get(current.row).get(current.index - 1).is_wall)) {
                        if (!(grid.get(current.row).get(current.index - 1).on_stack)) {
                            grid.get(current.row).get(current.index - 1).on_stack = true;
                            grid.get(current.row).get(current.index - 1).cost = current.cost + 1;
                            STACK.add(grid.get(current.row).get(current.index - 1));
                            continue;
                        }
                    }
                    else grid.get(current.row).get(current.index - 1).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            current.checked = true;
            current.on_stack = false;
            STACK.remove(STACK.size() - 1);
            if (STACK.size() == 0) break;
        }
        STACK.add(grid.get(0).get(0)); STACK.get(0).on_stack = true;
        while (true) {
            Tile current = STACK.get(STACK.size() - 1);
            boolean Tile_added = false;
            if (current.row + 1 == grid.size()
                    && current.index + 1 == grid.get(grid.size() - 1).size()) return STACK.size() - 1;
            try {
                // Going Down
                if (!(grid.get(current.row + 1).get(current.index).checked)) {
                    if (!(grid.get(current.row + 1).get(current.index).is_wall)) {
                        if (!(grid.get(current.row + 1).get(current.index).on_stack)) {
                            if (!(Tile_added)) {
                                grid.get(current.row + 1).get(current.index).on_stack = true;
                                STACK.add(grid.get(current.row + 1).get(current.index));
                                Tile_added = true;
                                continue;
                            }
                            else if (grid.get(current.row + 1)
                                    .get(current.index).cost < STACK.get(STACK.size()).cost) {
                                STACK.remove(STACK.size() - 1);
                                grid.get(current.row + 1).get(current.index).on_stack = true;
                                STACK.add(grid.get(current.row + 1).get(current.index));
                                continue;
                            }
                        }
                    }
                    else grid.get(current.row + 1).get(current.index).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Right
                if (!(grid.get(current.row).get(current.index + 1).checked)) {
                    if (!(grid.get(current.row).get(current.index + 1).is_wall)) {
                        if (!(grid.get(current.row).get(current.index + 1).on_stack)) {
                            if (!(Tile_added)) {
                                grid.get(current.row).get(current.index + 1).on_stack = true;
                                STACK.add(grid.get(current.row).get(current.index + 1));
                                Tile_added = true;
                                continue;
                            }
                            else if (grid.get(current.row)
                                    .get(current.index + 1).cost < STACK.get(STACK.size() - 1).cost) {
                                grid.get(current.row).get(current.index + 1).on_stack = true;
                                STACK.add(grid.get(current.row).get(current.index + 1));
                                continue;
                            }
                        }
                    }
                    else grid.get(current.row).get(current.index + 1).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Up
                if (!(grid.get(current.row - 1).get(current.index).checked)) {
                    if (!(grid.get(current.row - 1).get(current.index).is_wall)) {
                        if (!(grid.get(current.row - 1).get(current.index).on_stack)) {
                            if (!(Tile_added)) {
                                grid.get(current.row - 1).get(current.index).on_stack = true;
                                STACK.add(grid.get(current.row - 1).get(current.index));
                                Tile_added = true;
                                continue;
                            }
                            else if (grid.get(current.row - 1)
                                    .get(current.index).cost < STACK.get(STACK.size() - 1).cost) {
                                grid.get(current.row - 1).get(current.index).on_stack = true;
                                STACK.add(grid.get(current.row - 1).get(current.index));
                                continue;
                            }
                        }
                    }
                    else grid.get(current.row - 1).get(current.index).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            try {
                // Going Left
                if (!(grid.get(current.row).get(current.index - 1).checked)) {
                    if (!(grid.get(current.row).get(current.index - 1).is_wall)) {
                        if (!(grid.get(current.row).get(current.index - 1).on_stack)) {
                            if (!(Tile_added)) {
                                grid.get(current.row).get(current.index - 1).on_stack = true;
                                STACK.add(grid.get(current.row).get(current.index - 1));
                                Tile_added = true;
                                continue;
                            }
                            else if (grid.get(current.row)
                                    .get(current.index - 1).cost < STACK.get(STACK.size() - 1).cost) {
                                grid.get(current.row).get(current.index - 1).on_stack = true;
                                STACK.add(grid.get(current.row).get(current.index - 1));
                                continue;
                            }
                        }
                    }
                    else grid.get(current.row).get(current.index - 1).checked = true;
                }
            }
            catch (IndexOutOfBoundsException ignored){}
            current.checked = true;
            current.on_stack = false;
            STACK.remove(STACK.size() - 1);
            if ((STACK.size() == 0)) return -1;
            if (current.row + 1 == grid.size()
                    && current.index + 1 == grid.get(grid.size() - 1).size()) return STACK.size() - 1;
        }
    }
    // Purely used to hold information about the given space in the maze
    static class Tile {
        boolean is_wall, on_stack, checked = false; int index, row, cost;
        public Tile(boolean is_wall, int index, int row) {
            this.is_wall = is_wall; this.index = index; this.row = row; if (this.is_wall) cost = -1;}
        public String toString() { return Integer.toString(this.cost); }
    }
    /*
    For testing purposes, the maze values we come up with
    $   17  W   19  W   11  10  W   12
    17  16  17  18  W   10  9   10  11
    16  15  16  W   W   9   8   9   10
    15  14  15  16  W   8   7   8   W
    14  13  W   W   8   7   6   W   W
    13  12  11  10  9   W   5   4   W
    14  13  12  11  W  -1   W   3   2
    15  W   13  W   17  W   3   2   1
    16  W   14  15  16  W   W   1   %
     */
}
