import ddf.minim.*;
Minim minim;
int boardSize = 8;
int boardSize_max =100;
int boardSize_min = 4;
int[][] board;
boolean is_mouseClicking;
boolean is_KeyPressing;
int soundVolume;
int curTurn, gridArea, boardLength, scene;
boolean[] is_AITurn = new boolean[2];
int lastWidth, lastHeight;

String username;
/*
1...white
 2...black
 
 player.ambで音量調整
 */


Robot robot = new Robot();
effects _effects = new bubble();
rankUI rui = new rankUI();
settingUI sui = new settingUI();
void setup() {
  size(800, 500);
  //fullScreen();
  surface.setIcon(loadImage("assets/ai_character01_smile.png"));
  minim = new Minim(this);
  lastWidth = width;
  lastHeight = height;
  surface.setResizable(true);
  rectMode(CENTER);
  gameEnd_sound = minim.loadFile("sounds/カーソル移動11.mp3");
  theme_load();
  gridArea = 90;
  prepare();
  aui.prepare();
  ui.prepare();
  rui.prepare();
  sui.prepare();
  scene = 0;
  soundVolume = 10;
  soundVolume = 85;
  username = "guest" + str(int(random(1000)));
  println();
  println("YOU ARE " + username);
}

void prepare() {
  if (height < width) {
    boardLength = height * gridArea / 100;
  } else {
    boardLength = width * gridArea / 100;
  }
  robot.prepare();
  mui.prepare();
  _effects.prepare();
}

awardUI aui = new awardUI();
menuUI mui = new menuUI();
gameUI ui = new gameUI();
mouseDetection is_mouse = new mouseDetection();
AI_player aI_player = new AI_player();
stoneTurn turn = new stoneTurn();
_main main = new _main();
int turnable_num = 0;
//↓default10
int turn_stampMax = 10;
int turn_stamp = turn_stampMax;
//↓default50
int ai_thinking_stampMax = 50;
int ai_thinking_stamp = ai_thinking_stampMax;

void draw() {
  //theme_load();
  if (lastWidth != width || lastHeight != height) {
    prepare();
  }
  lastWidth = width;
  lastHeight = height;

  switch(scene) {
  case 0:
    background(back_col);
    _effects.run();
    mui.draw_logo();
    if (mui.draw_button("PLAY", width - width / 6, height - height / 3)) {
      scene = 3;
    }
    if (mui.draw_button("SCORES", width - width / 6, height - height / 5)) {
      scene = 4;
      rui.prepare();
    }
    if (mui.draw_settingButton(height / 10 ,height - height / 10)) {
      scene = 5;
      sui.prepare();
    }
    break;
  case 1:
    background(back_col);
    _effects.run();
    ui.draw_board();
    ui.draw_grid();
    ui.draw_stone();
    robot.draw_robot();
    ui.draw_score((width - boardLength) / 4, (width - boardLength) / 5, 2);
    ui.draw_score(width - (width - boardLength) / 4, height - (width - boardLength) / 5, 1);
    if (0 < turnable_num) {
      if (boardSize / 2 < turnable_num) {
        if (is_AITurn[curTurn - 1]) {
          robot.swicthFace(7);
        } else {
          robot.swicthFace(5);
        }
      }
      int pos = turn.get_turn_pos(turnable_num - 1);
      int x = pos-floor(pos/boardSize)*boardSize;
      int y = pos/boardSize;
      if (0 < turn_stamp) {
        ui.draw_turning(y, x);
        turn_stamp--;
      } else {
        board[y][x] = curTurn;
        turn_stamp = turn_stampMax;
        turnable_num--;
        if (turnable_num <= 0) {
          curTurn = main.turn_change(curTurn);
          int[] game_end = aI_player.select_pos();
          if (game_end[0] == -1 && game_end[1] == -1) {
            scene = 2;
            int score1_tmp = main.count_stone(1);
            int score2_tmp = main.count_stone(2);
            if(score1_tmp < score2_tmp){
              main.rank(score2_tmp * 1000 / (boardSize * boardSize), is_AITurn[1]);
            }else{
              main.rank(score1_tmp * 1000 / (boardSize * boardSize), is_AITurn[0]);
            }
            gameEnd_sound.rewind();
            gameEnd_sound.play();
          }
        }
      }
    } else {
      if (curTurn == 1) {
        if (is_AITurn[curTurn - 1]) {
          ai_put();
        } else {
          player_put();
        }
      } else {
        if (is_AITurn[curTurn - 1]) {
          ai_put();
        } else {
          player_put();
        }
      }
    }
    break;
  case 2:
    background(back_col);
    _effects.run();
    //アワード
    aui.draw_display();
    aui.draw_announce();
    break;
  case 3:
    background(back_col);
    _effects.run();
    //ゲームモード
    if (mui.draw_button("0P", width / 2 - width / 8 + int(1 - (width / 2 - mouseX) * 0.01), height / 2 - height / 8 + int(1 - (height / 2 - mouseY) * 0.01))) {
      scene = 1;
      main.prepare();
      is_AITurn[0] = true;
      is_AITurn[1] = true;
    }
    if (mui.draw_button("1P", width / 2 - width / 8 + int(1 - (width / 2 - mouseX) * 0.01), height / 2 + int(1 - (height / 2 - mouseY) * 0.01))) {
      scene = 1;
      main.prepare();
      is_AITurn[0] = true;
      is_AITurn[1] = false;
    }
    if (mui.draw_button("2P", width / 2 - width / 8 + int(1 - (width / 2 - mouseX) * 0.01), height / 2 + height / 8 + int(1 - (height / 2 - mouseY) * 0.01))) {
      scene = 1;
      main.prepare();
      is_AITurn[0] = false;
      is_AITurn[1] = false;
    }
    if (mui.draw_button("BACK", width - width / 6, height - height / 10)) {
      scene = 0;
    }
    break;
  case 4:
    background(back_col);
    _effects.run();
    //スコア
    rui.draw_score();
    if (mui.draw_button("BACK", width - width / 6, height - height / 10)) {
      scene = 0;
    }
    break;
  case 5:
    background(back_col);
    _effects.run();
    //設定
    sui.draw_setting();
    if (mui.draw_button("BACK", width - width / 6, height - height / 10)) {
      scene = 0;
    }
    break;
  default:
    println("Unknown scene. Scene " + scene + " not found.");
    scene = 0;
    break;
  }
}

void player_put() {
  ui.draw_gridCursor();
  if (is_mouseClicking && is_mouse.onBoard()) {
    turnable_num = turn.searchLevel(is_mouse.gridX(), is_mouse.gridY());
  }
  is_mouseClicking = false;
}

void ai_put() {
  if (0 < ai_thinking_stamp) {
    if (ai_thinking_stamp == ai_thinking_stampMax) {
      robot.swicthFace(1);
    } else if (ai_thinking_stamp == 20) {
      robot.swicthFace(2);
    }
    ai_thinking_stamp --;
  } else {
    int[] ai_selectPos = aI_player.select_pos();
    turnable_num = turn.searchLevel(ai_selectPos[0], ai_selectPos[1]);
    robot.swicthFace(0);
    ai_thinking_stamp = ai_thinking_stampMax;
  }
}

void mousePressed() {
  is_mouseClicking = true;
}

void mouseReleased() {
  is_mouseClicking = false;
}

void keyPressed(){
  is_KeyPressing = true;
}

void keyReleased(){
  is_KeyPressing = false;
}

class _main {
  void prepare() {
    board = new int[boardSize][boardSize];
    for (int i = 0; i < board.length; i++) {
      for (int q = 0; q < board.length; q++) {
        board[i][q] = 0;
      }
    }
    board[boardSize/2][boardSize/2] = 1;
    board[boardSize/2-1][boardSize/2-1] = 1;
    board[boardSize/2][boardSize/2-1] = 2;
    board[boardSize/2-1][boardSize/2] = 2;
    curTurn = 2;
  }

  int turn_change(int currentTurn) {
    if (currentTurn == 1) {
      return 2;
    } else {
      return 1;
    }
  }

  int count_stone(int Turn) {
    int scoreCount = 0;
    for (int i = 0; i < boardSize; i++) {
      for (int q = 0; q < boardSize; q++) {
        if (board[i][q] == Turn) {
          scoreCount++;
        }
      }
    }
    return scoreCount;
  }

  void rank(int curScore, boolean AI_tgl) {
    JSONObject save = loadJSONObject(save_path);
    JSONArray scores = save.getJSONArray("scores");
    int count = scores.size();
    String[] name_tmp = new String[count];
    int[] score_tmp = new int[count];
    for (int i = 0; i < count; i++) {
      JSONObject part = scores.getJSONObject(i);
      name_tmp[i] = part.getString("name");
      score_tmp[i] = part.getInt("score");
    }

    int index = 0;
    while (index < count && score_tmp[index] >= curScore) {
      index++;
    }
    //データの更新
    if(index < count){
      for (int i = count - 1; i > index; i--) {
        name_tmp[i] = name_tmp[i - 1];
        score_tmp[i] = score_tmp[i - 1];
      }
      if(AI_tgl){
        name_tmp[index] = "[AI]" + username;
      }else{
        name_tmp[index] = username;
      }
      score_tmp[index] = curScore;
      for (int i = 0; i < count; i++) {
        JSONObject part = new JSONObject();
        part.setString("name", name_tmp[i]);
        part.setInt("score", score_tmp[i]);
        scores.setJSONObject(i, part);
      }
    }
    saveJSONObject(save, "data/" + save_path);
  }
}
