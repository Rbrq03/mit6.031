package autograder;

import org.junit.Test;

import minesweeper.Board;

/**
 * Test method in Board
 */
class BoardTets {

    @Test(expected = AssertionError.class)
    public void testAssert(){
        assert false;
    }

    @Test
    public void testBoard() {
        Board board = new Board(10, 10);
        System.out.println(board);
        board.dig(0, 0);
        System.out.println(board);
        board.flag(3, 1);
        System.out.println(board);
    }


}
