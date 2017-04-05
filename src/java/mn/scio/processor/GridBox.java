/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.scio.processor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lupino
 */
public class GridBox {
    public int x = 0;
    public int y = 0;
    public int rect_x = 0;
    public int rect_y = 0;
    public int rect_width = 0;
    public int rect_height = 0;
    public int width = 0;
    public int height = 0;
    public int area = 0;
    public int unit_h = 25;
    
    public List<GridBox> childs = new ArrayList<GridBox>();
    public boolean used = false;
    
    public GridBox(int x, int y, int width, int height, int rectx, int recty, int w2, int h2){
        this.x = x; this.y = y; this.width = width; this.height = height;
        this.rect_x = rectx; this.rect_y = recty;
        this.rect_width = w2; this.rect_height = h2;
        this.area = this.rect_width * this.rect_height;
    }
    
    public GridBox(int x, int y, int width, int height, int rectx, int recty, int w2, int h2, int unit){
        this.x = x; this.y = y; this.width = width; this.height = height;
        this.rect_x = rectx; this.rect_y = recty;
        this.rect_width = w2; this.rect_height = h2;
        this.area = this.rect_width * this.rect_height;
        this.unit_h = unit;
    }
    
    public GridBox(GridBox cl){
        this.x = cl.x; this.y = cl.y; this.width = cl.width; this.height = cl.height;
        this.rect_x = cl.rect_x; this.rect_y = cl.rect_y;
        this.rect_width = cl.rect_width; this.rect_height = cl.rect_height;
        this.area = cl.area;
    }
    
    public void setUnitH(int unit){
        this.unit_h = unit;
    }
    
    public void merge(){
        for(int i = 0; i < this.childs.size(); i++){
            GridBox temp = this.childs.get(i);
            if((this.x == temp.x || this.x+1 == temp.x) && 
                    (this.y == temp.y || this.y+1 == temp.y)){
                this.childs.remove(i);
                i--;
            }
        }
    }
}
