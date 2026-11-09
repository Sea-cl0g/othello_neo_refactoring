
class mouseDetection {
  boolean onBoard() {
    return ((width-boardLength)/2 < mouseX && mouseX < width-(width-boardLength)/2 && (height-boardLength)/2 < mouseY && mouseY < height-(height-boardLength)/2);
  }

  int gridX() {
    int x_tmp = floor((mouseX-(width-boardLength)/2)/(boardLength/boardSize));
    if(x_tmp < boardSize){
      return x_tmp;
    }
    return 7;
  }

  int gridY() {
    int y_tmp = floor((mouseY-(height-boardLength)/2)/(boardLength/boardSize));
    if(y_tmp < boardSize){
      return y_tmp;
    }
    return 7;
  }
}
