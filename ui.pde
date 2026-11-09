color back_col;
color displayBack_col;
color displayText_col;
color displayTextShadow_col;
color stone2_col;
color stone1_col;
color board_col;
color boardFrame_col;
color grid_col;
color alertText_col;
color bubble_col;

AudioPlayer gameEnd_sound;
AudioPlayer  award_sound;
AudioPlayer  turn_sound;
AudioPlayer  click;

boolean effectsTgl = true;

JSONObject save;
String save_path = "save_data.json";
String theme_path;
String defaultTheme_path = "themes/default.json";
void theme_load() {
  if (loadJsonFile(save_path)) {
    println(save_path + " loaded successfully.");
    save = loadJSONObject(save_path);
    theme_path = save.getString("theme_path");
  } else {
    println(save_path + " does not exist.");
  }
  JSONObject theme;
  if (loadJsonFile(theme_path)) {
    println(theme_path + " loaded successfully.");
    theme = loadJSONObject(theme_path).getJSONObject("colors");
  } else {
    println(theme_path + " does not exist.");
    theme = loadJSONObject(defaultTheme_path).getJSONObject("colors");
    if (loadJsonFile(defaultTheme_path)) {
      println(defaultTheme_path + " loaded successfully.");
    } else {
      println(defaultTheme_path + " does not exist.");
      exit();
    }
  }
  back_col = getColor(theme, "back_col");
  displayBack_col = getColor(theme, "displayBack_col");
  displayText_col = getColor(theme, "displayText_col");
  displayTextShadow_col = getColor(theme, "displayTextShadow_col");
  stone2_col = getColor(theme, "stone2_col");
  stone1_col = getColor(theme, "stone1_col");
  board_col = getColor(theme, "board_col");
  boardFrame_col = getColor(theme, "boardFrame_col");
  grid_col = getColor(theme, "grid_col");
  alertText_col = getColor(theme, "alertText_col");
  bubble_col = getColor(theme, "bubble_col");
}

color getColor(JSONObject json, String keyWord) {
  JSONArray arr = json.getJSONArray(keyWord);
  if (arr.size() == 3) {
    return color(arr.getInt(0), arr.getInt(1), arr.getInt(2));
  } else if (arr.size() == 4) {
    return color(arr.getInt(0), arr.getInt(1), arr.getInt(2), arr.getInt(3));
  } else {
    println("Error: Unexpected number of color components for " + keyWord);
    return color(0);
  }
}

boolean loadJsonFile(String filepath) {
  //半角スペースが無視される？？
  JSONObject json = loadJSONObject(filepath);
  return json != null;
}

void set_volume(float value){
  click.setGain(value);
  award_sound.setGain(value);
  turn_sound.setGain(value);
  gameEnd_sound.setGain(value);
}


class _edit {
  int text_cursor;
  int text_cursor_timerMax;
  int text_cursor_timer;
  int key_repeatMax;
  int key_repeat;

  PShape checkMark;
  void prepare() {
    text_cursor = -2;
    key_repeatMax = 10;
    key_repeat = 0;
    text_cursor_timerMax = 40;
    text_cursor_timer = text_cursor_timerMax;
    checkMark = loadShape("data/assets/チェックマークの無料アイコン.svg");
    PShape part = checkMark.getChild(0);
    part.disableStyle();
  }

  int num_bar(int x, int y, int num) {
    noStroke();
    fill(displayText_col);
    rect(x, y, width / 3, height / 50);
    textAlign(CENTER, CENTER);
    textSize(height / 15);
    text("-", x - width / 5, y);
    text("+", x + width / 5, y);
    if (abs(mouseX - x) < width / 3 && abs(mouseY - y) < height / 50 && is_mouseClicking) {
      int surplus = x - width / 6;
      num = (mouseX - surplus) * (boardSize_max - boardSize_min + 1) / (width / 3) + 4;
    }
    if (num < boardSize_min) {
      num = boardSize_min;
    } else if (boardSize_max < num) {
      num = boardSize_max;
    }
    fill(board_col);
    rect(x - width / 6 + (width * (num - 4) / 3 / (boardSize_max - boardSize_min + 1)), y, height / 50, height / 20);
    fill(displayText_col);
    textSize(height / 30);
    text(num, x - width / 6 + (width * (num - 4) / 3 / (boardSize_max - boardSize_min + 1)), y + height / 20);
    return num;
  }
  
  char key_buffer;
  String textbox(int x, int y, String text, color textColor) {
    noStroke();
    fill(displayText_col);
    rect(x, y, floor(width / 2.5), height / 15);
    int charPosX = x - width / 5 + (height / 40);
    int charPosY = y - height / 200;
    textAlign(LEFT, CENTER);
    textSize(height / 20);
    fill(textColor);
    text(text, charPosX, charPosY);
    char[] textArray = text.toCharArray();
    int[] text_space = new int[textArray.length + 1];
    ArrayList<Character> text_split = new ArrayList<Character>();
    String s = "";
    text_space[0] = charPosX;
    for (int i = 0; i < textArray.length; i++) {
      text_split.add(textArray[i]);
      s = s + textArray[i];
      //println(s, int(textWidth(s)));
      text_space[i + 1] = charPosX + int(textWidth(s));
    }
    if (is_mouseClicking) {
      text_cursor = -2;
      if (abs(mouseY - y) < height / 30) {
        for (int i = 0; i < text_split.size(); i++) {
          charPosX = text_space[i];
          int len_mouseX = mouseX - charPosX;
          if (abs(len_mouseX) < height / 40) {
            if (len_mouseX <= 0) {
              text_cursor = i;
            } else {
              text_cursor = i + 1;
            }
            is_mouseClicking = false;
            break;
          }
        }
      }
    }
    if (text_cursor != -2) {
      if (text_cursor_timerMax / 2 < text_cursor_timer) {
        stroke(0);
        strokeWeight(width / 800);
        line(text_space[text_cursor], charPosY - height / 40, text_space[text_cursor], charPosY + height / 40);
        noStroke();
      }
      if (0 < text_cursor_timer) {
        text_cursor_timer--;
      } else {
        text_cursor_timer = text_cursor_timerMax;
      }
      if(!is_KeyPressing || key != key_buffer){
        key_repeat = 0;
      }
      key_buffer = key;
      if (is_KeyPressing && key_repeat <= 0) {
        if (isPrintableKey()) {
          text_split.add(text_cursor, key);
          text_cursor++;
        } else if (key == CODED && keyCode == LEFT && 0 < text_cursor) {
          text_cursor--;
        } else if (key == CODED && keyCode == UP && 0 <= text_cursor) {
          text_cursor = 0;
        } else if (key == CODED && keyCode == RIGHT && text_cursor < text_split.size()) {
          text_cursor++;
        } else if (key == CODED && keyCode == DOWN && text_cursor < text_split.size()) {
          text_cursor = text_split.size();
        } else if (key == BACKSPACE && 0 < text_cursor) {
          text_split.remove(text_cursor - 1);
          text_cursor--;
        } else if (key == DELETE && text_cursor == text_split.size() && 0 < text_cursor) {
          text_split.remove(text_cursor);
        }
        key_repeat = key_repeatMax;
      }
    }
    StringBuilder sb = new StringBuilder();
    for (Character c : text_split) {
      sb.append(c);
    }
    if (0 < key_repeat) {
      key_repeat--;
    }
    return sb.toString();
  }
  
  boolean isPrintableKey() {
    if (key >= 32 && key <= 126) {
      return true;
    }
    if (key >= 160 && key <= 255) {
      return true;
    }
    return false;
  }

  void textBox_button(int x, int y, String text) {
    noStroke();
    int w = height / 15;
    fill(board_col);
    rect(x, y, w, w);
    textAlign(CENTER, CENTER);
    textSize(height / 15);
    if (abs(mouseX - x) < w && abs(mouseY - y) < w) {
      fill(displayTextShadow_col);
      if (is_mouseClicking) {
        text_cursor = text.length();
        is_mouseClicking = false;
      }
    } else {
      fill(displayText_col);
    }
    text("←", x, y - height / 150);
  }

  boolean checkBox(int x, int y, boolean proposition) {
    fill(board_col);
    if (abs(mouseX - x) < height / 2 / 15 && abs(mouseY - y) < height / 2 / 15) {
      stroke(displayText_col);
      strokeWeight(height / 200);
      if (is_mouseClicking) {
        proposition = !proposition;
        is_mouseClicking = false;
      }
    } else {
      noStroke();
    }
    rect(x, y, height / 15, height / 15);
    if (proposition) {
      shapeMode(CENTER);
      checkMark.setFill(displayText_col);
      shape(checkMark, x, y, height / 20, height / 20);
    }
    return proposition;
  }
}


class settingUI {
  _edit edit = new _edit();

  void prepare() {
    edit.prepare();
  }

  void draw_setting() {
    int x = int((1 - (width / 2 - mouseX) * 0.01)) + width / 2 - width / 8;
    int y = int(1 - (height / 2 - mouseY) * 0.01) + height / 2;
    noStroke();
    fill(displayBack_col);
    rect(x, y, width / 2, height - height / 10);
    textAlign(CENTER, CENTER);
    textSize(height / 10);
    fill(displayTextShadow_col);
    text("SETTING", x, height / 8 + int(1 - (height / 2 - mouseY) * 0.01) + height / 150);
    fill(displayText_col);
    text("SETTING", x, height / 8 + int(1 - (height / 2 - mouseY) * 0.01));
    rect(x, height / 5 + int(1 - (height / 2 - mouseY) * 0.01), width / 2 - height / 50, height / 100);
    //背景効果の有効化
    draw_text("BACKGROUND EFFECTS", x, height * 28 / 100 + int(1 - (height / 2 - mouseY) * 0.01) - height / 200);
    effectsTgl = edit.checkBox(x, height * 35 / 100 + int(1 - (height / 2 - mouseY) * 0.01), effectsTgl);
    //音量
    draw_text("SOUND VOLUME", x, height * 65 / 100 + int(1 - (height / 2 - mouseY) * 0.01) - height / 200);
    soundVolume = edit.num_bar(x, height * 70 / 100 + int(1 - (height / 2 - mouseY) * 0.01), soundVolume);
    set_volume(map(soundVolume, 0, 100, -80, 10));
    //グリッドサイズ
    draw_text("GRID SIZE", x, height * 80 / 100 + int(1 - (height / 2 - mouseY) * 0.01) - height / 200);
    boardSize = edit.num_bar(x, height * 85 / 100 + int(1 - (height / 2 - mouseY) * 0.01), boardSize);
    //名前変更
    color editerText_col = displayTextShadow_col;
    if (loadJsonFile(theme_path)) {
      editerText_col = displayTextShadow_col;
    } else {
      editerText_col = alertText_col;
    }
    draw_text("THEME", x, height * 45 / 100 + int(1 - (height / 2 - mouseY) * 0.01) - height / 200);
    String theme_path_buffer = theme_path;
    theme_path = edit.textbox(x - height / 50, height * 52 / 100 + int(1 - (height / 2 - mouseY) * 0.01), theme_path, editerText_col);
    edit.textBox_button(x + width / 5, height * 52 / 100 + int(1 - (height / 2 - mouseY) * 0.01), theme_path);
    //println(theme_path_buffer , theme_path, !theme_path.equals(theme_path_buffer));
    if (!theme_path.equals(theme_path_buffer)) {
      JSONObject save = loadJSONObject(save_path);
      save.setString("theme_path", theme_path);
      saveJSONObject(save, "data/" + save_path);
      theme_load();
    }
  }

  void draw_text(String text, int x, int y) {
    textAlign(CENTER, CENTER);
    textSize(height / 20);
    fill(displayTextShadow_col);
    text(text, x, y + height / 200);
    fill(displayText_col);
    text(text, x, y);
  }
}

class rankUI {
  String[] name;
  int[] score;
  _edit edit = new _edit();

  void prepare() {
    save = loadJSONObject(save_path);
    JSONArray scores = save.getJSONArray("scores");
    name = new String[scores.size()];
    score = new int[scores.size()];
    for (int i = 0; i < scores.size(); i++) {
      JSONObject part = scores.getJSONObject(i);
      name[i] = part.getString("name");
      score[i] = part.getInt("score");
    }
    edit.prepare();
  }

  void draw_score() {
    int x = int((1 - (width / 2 - mouseX) * 0.01)) + width / 2 - width / 8;
    int y = int(1 - (height / 2 - mouseY) * 0.01) + height / 2;
    noStroke();
    fill(displayBack_col);
    rect(x, y, width / 2, height - height / 10);
    textAlign(CENTER, CENTER);
    textSize(height / 10);
    fill(displayTextShadow_col);
    text("SCORES", x, height / 8 + floor(1 - (height / 2 - mouseY) * 0.01) + height / 150);
    fill(displayText_col);
    text("SCORES", x, height / 8 + floor(1 - (height / 2 - mouseY) * 0.01));
    rect(x, height / 5 + int(1 - (height / 2 - mouseY) * 0.01), width / 2 - height / 50, height / 100);
    // スコアを表示
    fill(displayTextShadow_col);
    textSize(height / 20);
    for (int i = 0; i < name.length; i++) {
      textAlign(LEFT, CENTER);
      score_text("No." + (i + 1), int(x - (width / 2 - height / 50) / 2.5), i);
      textAlign(RIGHT, CENTER);
      score_text(int(score[i]) / 10.0 + "%", floor(x + (width / 2 + height / 50) / 2.5), i);
      textAlign(CENTER, CENTER);
      if (12 < name[i].length()) {
        char[] name_split = name[i].toCharArray();
        String name_tmp = "";
        for (int q = 0; q < 12; q++) {
          name_tmp = name_tmp + name_split[q];
        }
        score_text(name_tmp + "...", x, i);
      } else {
        score_text(name[i], x, i);
      }
    }
    //名前変更
    username = edit.textbox(x - height / 50, y + floor(height / 2.6), username, displayTextShadow_col);
    edit.textBox_button(x + width / 5, y + floor(height / 2.6), username);
  }

  void score_text(String text, int x, int counter) {
    fill(displayTextShadow_col);
    text(text, x, height / 4 + height * counter / 20 + floor(1 - (height / 2 - mouseY) * 0.01) + height / 200);
    fill(displayText_col);
    text(text, x, height / 4 + height * counter / 20 + floor(1 - (height / 2 - mouseY) * 0.01));
  }
}

class menuUI {
  PImage robot_img;
  PShape gear;

  void prepare() {
    robot_img = loadImage("assets/ai_character01_smile.png");
    click = minim.loadFile("sounds/決定ボタンを押す12.mp3");
    gear = loadShape("data/assets/設定の歯車アイコン　その2.svg");
    PShape part = gear.getChild(0);
    part.disableStyle();
    shapeMode(CENTER);
  }

  boolean draw_button(String text, int x, int y) {
    int w = height / 3;
    int h = height / 10;
    noStroke();
    if (abs(x - mouseX) < w / 2 && abs(y - mouseY) < h / 2) {
      stroke(displayText_col);
      strokeWeight(height / 150);
      text = ">" + text + "<";
    }
    fill(displayBack_col);
    rect(x, y, w, h);
    textAlign(CENTER, CENTER);
    textSize(height / 15);
    fill(displayTextShadow_col);
    text(text, x, y + height / 150);
    fill(displayText_col);
    text(text, x, y);
    if (abs(x - mouseX) < w / 2 && abs(y - mouseY) < h / 2 && is_mouseClicking) {
      click.rewind();
      click.play();
      is_mouseClicking = false;
      return true;
    }
    return false;
  }

  boolean draw_settingButton(int x, int y) {
    int w = height / 10;
    noStroke();
    if (abs(x - mouseX) < w / 2 && abs(y - mouseY) < w / 2) {
      stroke(displayText_col);
      strokeWeight(height / 150);
      gear.setFill(displayTextShadow_col);
    } else {
      gear.setFill(displayText_col);
    }
    fill(displayBack_col);
    rect(x, y, w, w);
    shape(gear, x, y, height / 15, height / 15);
    if (abs(x - mouseX) < w / 2 && abs(y - mouseY) < w / 2 && is_mouseClicking) {
      click.play();
      click.rewind();
      is_mouseClicking = false;
      return true;
    }
    return false;
  }

  void draw_logo() {
    int x = int((1 - (width / 2 - mouseX) * 0.01)) + width / 2 - width / 8;
    int y = int(1 - (height / 2 - mouseY) * 0.01) + height / 2 - height / 10;
    textAlign(CENTER, CENTER);
    fill(displayTextShadow_col);
    textSize(height / 15);
    text("with Processing", x + height / 15, y + height / 10);
    textSize(height / 6);
    text("OTHELLO", x, y + height / 100);
    fill(displayText_col);
    text("OTHELLO", x, y);
    imageMode(CENTER);
    robot_img.resize(0, height / 6);
    image(robot_img, x + height * 2 / 5, y);
  }
}

class awardUI {
  _main main = new _main();

  void prepare() {
    award_sound = minim.loadFile("sounds/決定ボタンを押す24.mp3");
  }

  void draw_announce() {
    noStroke();
    int x = width / 2;
    int y = height - height / 8;
    String menu_alert = "Click to return to menu";
    textAlign(CENTER, CENTER);
    textSize(boardLength / 8);
    fill(displayTextShadow_col);
    text(menu_alert, x + (width / 2 - mouseX) * 0.01, y + boardLength / 80 + (height / 2 - mouseY) * 0.01);
    fill(alertText_col);
    text(menu_alert, x + (width / 2 - mouseX) * 0.01, y + (height / 2 - mouseY) * 0.01);
    if (is_mouseClicking) {
      //記録達成時のみ
      award_sound.play();
      award_sound.rewind();
      scene = 0;
    }
  }

  void draw_display() {
    noStroke();
    fill(displayBack_col);
    rect(width / 2, height / 2, boardLength, boardLength / 2);
    int color1_score = main.count_stone(1);
    int color2_score = main.count_stone(2);
    int d = boardLength / 4;
    textAlign(LEFT, CENTER);
    textSize(boardLength / 4);
    if (color1_score < color2_score) {
      fill(stone1_col);
      ellipse(width / 2 - d, height / 2 + (d / 20), d, d);
      fill(stone2_col);
      ellipse(width / 2 - d, height / 2, d, d);
      fill(displayTextShadow_col);
      text(color2_score, width / 2, height / 2 + (d / 20));
      fill(displayText_col);
      text(color2_score, width / 2, height / 2);
    } else {
      fill(stone2_col);
      ellipse(width / 2 - d, height / 2 + (d / 20), d, d);
      fill(stone1_col);
      ellipse(width / 2 - d, height / 2, d, d);
      fill(displayTextShadow_col);
      text(color1_score, width / 2, height / 2 + (d / 20));
      fill(displayText_col);
      text(color1_score, width / 2, height / 2);
    }
  }
}


class gameUI {
  float stone_per = 0.8;
  _main main = new _main();

  void prepare() {
    turn_sound = minim.loadFile("sounds/カーソル移動9.mp3");
  }

  void draw_board() {
    noStroke();
    fill(boardFrame_col);
    rect(width / 2, height / 2, boardLength + boardLength / 25, boardLength + boardLength / 25);
    fill(board_col);
    rect(width / 2, height / 2, boardLength, boardLength);
  }

  void draw_grid() {
    stroke(grid_col);
    strokeWeight(1);
    for (int i = 0; i <= boardSize; i++) {
      line((width - boardLength) / 2 + boardLength * i / boardSize, (height - boardLength) / 2, (width - boardLength) / 2 + boardLength * i / boardSize, height - (height - boardLength) / 2);
      line((width - boardLength) / 2, (height - boardLength) / 2 + boardLength * i / boardSize, width - (width - boardLength) / 2, (height - boardLength) / 2 + boardLength * i / boardSize);
    }
  }

  void draw_stone() {
    noStroke();
    for (int i = 0; i < boardSize; i++) {
      for (int q = 0; q < boardSize; q++) {
        if (board[i][q] != 0) {
          float x = (width-boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * q / boardSize;
          float y = (height-boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * i / boardSize;
          float shadow_y = y + boardLength / boardSize / 30;
          if (board[i][q] == 1) {
            fill(stone2_col);
            ellipse(x, shadow_y, boardLength * stone_per / boardSize, boardLength * stone_per / boardSize);
            fill(stone1_col);
            ellipse(x, y, boardLength * stone_per / boardSize, boardLength * stone_per / boardSize);
          } else {
            fill(stone1_col);
            ellipse(x, shadow_y, boardLength * stone_per / boardSize, boardLength * stone_per / boardSize);
            fill(stone2_col);
            ellipse(x, y, boardLength * stone_per / boardSize, boardLength * stone_per / boardSize);
          }
        }
      }
    }
  }

  void draw_gridCursor() {
    if (is_mouse.onBoard()) {
      noFill();
      stroke(235, 222, 52);
      strokeWeight(5);
      rectMode(CENTER);
      rect((width-boardLength)/2 + (boardLength/boardSize/2) + boardLength * is_mouse.gridX() / boardSize, (height-boardLength)/2 + (boardLength/boardSize/2) + boardLength * is_mouse.gridY() / boardSize, boardLength / boardSize, boardLength / boardSize);
    }
  }

  void draw_turning(int y, int x) {
    fill(board_col);
    rectMode(CENTER);
    rect((width - boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * x / boardSize, (height - boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * y / boardSize, boardLength * 0.9 / boardSize, boardLength * 0.9 / boardSize);
    if (curTurn == 1) {
      fill(stone1_col);
    } else {
      fill(stone2_col);
    }

    if (turn_stamp == turn_stampMax) {
      turn_sound.rewind();
      turn_sound.play();
    }

    ellipse((width - boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * x / boardSize, (height - boardLength) / 2 + (boardLength / boardSize / 2) + boardLength * y / boardSize, (boardLength / boardSize * stone_per *  turn_stamp / turn_stampMax), (boardLength * stone_per / boardSize));
  }

  void draw_score(int x, int y, int displayTurn) {
    // バックグラウンド
    fill(displayBack_col);
    if (displayTurn == curTurn) {
      stroke(alertText_col);
      strokeWeight(height / 200);
    } else {
      noStroke();
    }
    rect(x, y, (width - boardLength) / 2.5, (width - boardLength) / 5);
    // スコア
    noStroke();
    int scoreCount = main.count_stone(displayTurn);
    textAlign(LEFT, CENTER);
    textSize(width / 15);
    fill(displayTextShadow_col);
    text(scoreCount, x, y + height / 150);
    fill(displayText_col);
    text(scoreCount, x, y);
    // 石
    if (displayTurn == 1) {
      fill(stone2_col);
      ellipse(x - (width - boardLength) / 10, y + height / 150, (width - boardLength) / 8, (width - boardLength) / 8);
      fill(stone1_col);
      ellipse(x - (width - boardLength) / 10, y, (width - boardLength) / 8, (width - boardLength) / 8);
    } else {
      fill(stone1_col);
      ellipse(x - (width - boardLength) / 10, y + height / 150, (width - boardLength) / 8, (width - boardLength) / 8);
      fill(stone2_col);
      ellipse(x - (width - boardLength) / 10, y, (width - boardLength) / 8, (width - boardLength) / 8);
    }
  }
}


class Robot {
  //String[][] chat = new String[8][6];
  PImage robot_img;
  int x;
  int y;
  int ly;
  int bound_area;
  boolean bound_tgl;

  void prepare() {
    robot_img = loadImage("assets/ai_character01_smile.png");
    imageMode(CENTER);
    x = width - (width - boardLength) / 4;
    y = height - (height / 3);
    ly = 0;
    bound_tgl = false;
    bound_area = boardLength / 10;
  }

  void swicthFace(int faceType) {
    switch(faceType) {
    case 0:
      robot_img = loadImage("assets/ai_character01_smile.png");
      break;
    case 1:
      robot_img = loadImage("assets/ai_character08_think.png");
      break;
    case 2:
      robot_img = loadImage("assets/ai_character05_idea.png");
      break;
    case 3:
      robot_img = loadImage("assets/ai_character09_question.png");
      break;
    case 4:
      robot_img = loadImage("assets/ai_character11_sleep.png");
      break;
    case 5:
      robot_img = loadImage("assets/ai_character07_surprise.png");
      break;
    case 6:
      robot_img = loadImage("assets/ai_character03_cry.png");
      break;
    case 7:
      robot_img = loadImage("assets/ai_character12_tehe.png");
      break;
    default:
      robot_img = loadImage("assets/no_image_logo.png");
      break;
    }
  }

  void draw_robot() {
    if (bound_tgl) {
      if ((ly / 10) < -bound_area) {
        bound_tgl = !bound_tgl;
      } else {
        ly -= boardLength / 30;
      }
    } else {
      if (0 < (ly / 10)) {
        bound_tgl = !bound_tgl;
      } else {
        ly += boardLength / 50;
      }
    }
    robot_img.resize(0, boardLength / 2);
    image(robot_img, x, y + (ly / 10));
  }
}



class effects {
  //countは設定で変更可能
  int count = 50;
  int[] x = new int[count];
  int[] y = new int[count];
  int[] dx = new int[count];
  int[] dy = new int[count];
  int[] d = new int[count];

  void prepare() {
  }

  void run() {
    //ここに何か記入しても実行されない？
  }
}

class bubble extends effects {
  void prepare() {
    for (int i = 0; i < count; i++) {
      x[i] = int(random(width));
      y[i] = int(random(height));
      dx[i] = int(random(-1, 1));
      dy[i] = int(random(1, ceil(height / 100)));
      d[i] = int(random(boardLength / 50, boardLength / 25));
    }
  }

  void run() {
    if (effectsTgl) {
      stroke(bubble_col);
      strokeWeight(1);
      noFill();
      for (int i = 0; i < count; i++) {
        ellipse(x[i], y[i], d[i], d[i]);
        if (y[i] < 0) {
          y[i] = height;
          x[i] = int(random(width));
        } else {
          x[i] -= dx[i];
          y[i] -= dy[i];
        }
      }
    }
  }
}
