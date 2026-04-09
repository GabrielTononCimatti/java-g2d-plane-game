package jogoaviao;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;

public class Aviao
{
    public int posX, posY, vX, vY;
    public Color corInterior, corBorda;
    public Arc2D.Double cabine;
    public Rectangle2D.Double corpo;
    public GeneralPath cauda, asa;
    public int direction;
    
    Aviao(int posX, int posY, int vX, int vY, Color corInterior, Color corBorda)
    {
        this.posX=posX;
        this.posY=posY;
        this.vX=vX;
        this.vY=vY;
        this.corInterior=corInterior;
        this.corBorda=corBorda;
        this.cabine = new Arc2D.Double();
        this.corpo = new Rectangle2D.Double();
        this.cauda = new GeneralPath();
        this.asa = new GeneralPath();
    }
    
    public void draw(Graphics2D g2d)
    {
        Color old=g2d.getColor();
        g2d.setColor(corInterior);
        
        cabine.setArc(posX-100,posY-25,100,50,0,-90, Arc2D.PIE);
        corpo.setRect(posX-215, posY, 165, 25);
        
        cauda.reset();
        cauda.moveTo(posX-215, posY+45);
        cauda.lineTo(posX-215, posY+25);
        cauda.lineTo(posX-200, posY+25);
        cauda.closePath();
        
        asa.reset();
        asa.moveTo(posX-126, posY);
        asa.lineTo(posX-94, posY);
        asa.lineTo(posX-143, posY-20);
        asa.lineTo(posX-158, posY-20);
        asa.closePath();
        
        g2d.fill(cabine);
        g2d.fill(corpo);
        g2d.fill(cauda);
        g2d.fill(asa);
        
        g2d.setColor(corBorda);
        g2d.drawArc(posX-100,posY-25,100,50,0,-90);
        g2d.drawLine(posX,posY,posX-94,posY);
        g2d.drawLine(posX-126,posY,posX-215,posY);
        g2d.drawLine(posX-50,posY+25,posX-200,posY+25);
        g2d.drawLine(posX-200,posY+25,posX-215,posY+45);
        g2d.drawLine(posX-215,posY+45,posX-215,posY);
        g2d.drawLine(posX-110,posY+10, posX-158, posY-20);
        g2d.drawLine(posX-158,posY-20, posX-143, posY-20);
        g2d.drawLine(posX-143,posY-20, posX-70, posY+10);
        
        g2d.setColor(old);
    }
    
    public void move()
    {
        direction=0;
        if(Teclado.RIGHT)
        {
            this.posX+=vX;
            direction=1;
        }
        if(Teclado.LEFT)
        {
            direction=-1;
            this.posX-=vX;
        }
        if(Teclado.UP)
        {
            this.posY+=vY;
        }
        if(Teclado.DOWN)
        {
            this.posY-=vY;
        }
    }   
    
    public boolean hit(int x, int y)
    {
        if(48*(y-this.posY-10)+30*this.posX-3300<=30*x && 30*x<=73*(y-this.posY)+30*this.posX-2830 && this.posY-20<=y && y<=this.posY)
        {
            return true;
        }
        if(this.posX-50<=x && this.posY<=y && y<=this.posY+25 && (x-this.posX+50)*(x-this.posX+50)+4*(y-this.posY)*(y-this.posY)<=2500)
        {
            return true;
        }
        if(this.posX-215<=x && x<=this.posX-50 && this.posY<=y && y<=this.posY+25)
        {
            return true;
        }
        if(this.posX-215<=x && 4*x<=4*this.posX+3*this.posY-725-3*y && this.posY+25<=y && y<=this.posY+45)
        {
            return true;
        }
        return false;
    }
}
