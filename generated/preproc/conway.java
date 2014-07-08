import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class conway extends PApplet {

int gridHeightAndWidth = 300;
int boxSize = 3;

Cell[][] grid = new Cell[gridHeightAndWidth][gridHeightAndWidth];

public void setup() {
  size(gridHeightAndWidth * boxSize, gridHeightAndWidth * boxSize);
  frameRate(30);
  generateRandomGrid();
}

public void generateRandomGrid() {
  for (int i = 0; i < gridHeightAndWidth; i++) {
    for (int j = 0; j < gridHeightAndWidth; j++) {
      grid[i][j] = new Cell(i, j, random(1) > 0.1f);
    }
  }
}

public void draw() {
    background(255);
    for (int i = 0; i < gridHeightAndWidth; i++) {
      for (int j = 0; j < gridHeightAndWidth; j++) {
        noStroke();
        Cell cell = grid[i][j];
        if (cell.isLit()) {
          fill(255);
        } else {
          fill(0);
        }
        rect(i * boxSize, j * boxSize, boxSize, boxSize);
      }
    }
    updateGrid();
}

public void updateGrid() {
  for (int i = 0; i < gridHeightAndWidth; i++) {
    for (int j = 0; j < gridHeightAndWidth; j++) {
      Cell cell = grid[i][j];
      ArrayList<Cell> neighbours = getSurroundingCells(cell);
      if (cell.isLit()) {
        if (neighbours.size() < 2 || neighbours.size() > 3) {
          cell.setLit(false);
        }
      } else {
        if (neighbours.size() == 3) {
          cell.setLit(true);
        }
      }
    }
  }
}

public ArrayList<Cell> getSurroundingCells(Cell baseCell) {
  ArrayList<Cell> neighbours = new ArrayList<Cell>();
  IntList xInRange = getCoordsInRange(baseCell.getX());
  IntList yInRange = getCoordsInRange(baseCell.getY());
  for (int x : xInRange) {
    for (int y : yInRange) {
      Cell neighbour = grid[x][y];
      if (neighbour.isLit()) {
        neighbours.add(neighbour);
      }
    }
  }
  return neighbours;
}

public IntList getCoordsInRange(int baseCoord) {
  IntList coordsInRange = new IntList();
  if (baseCoord + 1 < gridHeightAndWidth) {
    coordsInRange.append(baseCoord + 1);
  }
  if (baseCoord - 1 > 0) {
    coordsInRange.append(baseCoord - 1);
  }
  return coordsInRange; 
}

class Cell {
  int x;
  int y;
  boolean lit;
  
  public Cell(int x, int y, boolean lit) {
    this.x = x;
    this.y = y;
    this.lit = lit;
  } 
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public boolean isLit() {
    return lit;
  }
  
  public void setLit(boolean lit) {
    this.lit = lit;
  }
}

    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#ECE9D8", "conway" });
    }
}
