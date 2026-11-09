import java.util.HashSet;
import java.util.Collections;

class stoneTurn {
  ArrayList<Integer> stack = new ArrayList<Integer>();
  ArrayList<Integer> turnList = new ArrayList<Integer>();
  ArrayList<Integer> removeList = new ArrayList<Integer>();
  
  int searchLevel(int x, int y) {
    removeList.clear();
    removeList.add(-1);
    turnList.clear();
    if(board[y][x] == 0){
      each_search(1, 0, x, y);
      each_search(-1, 0, x, y);
      each_search(0, 1, x, y);
      each_search(0, -1, x, y);
      each_search(1, 1, x, y);
      each_search(-1, -1, x, y);
      each_search(-1, 1, x, y);
      each_search(1, -1, x, y);
    }
    Collections.reverse(turnList);
    return turnList.size();
  }
  
  void each_search(int vx, int vy, int x, int y) {
    if(turnList.size() == 0){
      stack.add(y*boardSize+x);
    }else{
      stack.add(-1);
    }
    search(vx, vy, x, y);
    stack.clear();
  }

  void search(int vx, int vy, int x, int y) {
    x += vx;
    y += vy;
    //println("検索中: ", vx, vy, x, y);
    boolean is_inGrid = 0 <= x && x < boardSize && 0 <= y && y < boardSize;
    if (is_inGrid && board[y][x] != curTurn && board[y][x] != 0) {
      stack.add(y*boardSize+x);
      search(vx, vy, x, y);
    } else if (is_inGrid && board[y][x] == curTurn) {
      //println("検索終了: ", vx, vy, x, y);
      //println(stack.size());
      if (1 < stack.size()) {
        stack.removeAll(removeList);
        turnList.addAll(stack);
      }
    }
  }
  
  int get_turn_pos(int index) {
    return turnList.get(index);
  }

  int pop(ArrayList<Integer> array) {
    if (array.size() == 0) {
      println("List is empty");
      return -1;
    }
    return array.remove(array.size() - 1);
  }
}


class AI_player {
  stoneTurn turn = new stoneTurn();
  int[] select_pos() {
    int posScore_max = 1;
    int[] posScore_index = {-1, -1};
    for (int i = 0; i < boardSize; i++) {
      for (int q = 0; q < boardSize; q++) {
        int posScore = turn.searchLevel(q, i);
        if (posScore_max <= posScore && board[i][q] == 0) {
          if(posScore_max == posScore){
            if(int(random(2)) == 0){
              posScore_max = posScore;
              posScore_index[0] = q;
              posScore_index[1] = i;
            }
          }else{
            posScore_max = posScore;
            posScore_index[0] = q;
            posScore_index[1] = i;
          }
          
        }
      }
    }
    return posScore_index;
  }
}
