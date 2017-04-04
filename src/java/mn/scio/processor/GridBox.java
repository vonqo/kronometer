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
    public int width = 0;
    public int height = 0;
    public int area = 0;
    
    public List<GridBox> childs = new ArrayList<GridBox>();
    public boolean used = false;
    
    public GridBox(int x, int y, int width, int height, int rectx, int recty){
        this.x = x; this.y = y; this.width = width; this.height = height;
        this.rect_x = rectx; this.rect_y = recty;
        this.area = this.width * this.height;
    }
    
    public GridBox(GridBox cl){
        this.x = cl.x; this.y = cl.y; this.width = cl.width; this.height = cl.height;
        this.rect_x = cl.rect_x; this.rect_y = cl.rect_y;
        this.area = cl.area;
    }
}
