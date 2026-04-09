package jogoaviao;

import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.Random;

public class Presente
{
    public int posX, posY, vX, vY, w, h;
    Color cor;
    public Rectangle2D.Double corpo = new Rectangle2D.Double();
    
    Presente(int posX, int posY, int vX, Color cor)
    {
        this.posX=posX;
        this.posY=posY;
        this.vX=vX;
        this.vY=0;
        this.cor=cor;
        this.h=30;
        this.w=30;
    }
    
    public void draw(Graphics2D g2d)
    {
        Color old=g2d.getColor();
        g2d.setColor(cor);
        corpo.setRect(posX, posY, w, h);
        g2d.fill(corpo);
        g2d.setColor(old);
    }
    
    public void move()
    {
        this.posX+=vX;
        this.posY-=vY;
        vY+=1;
    }
    
    public boolean hit(Predio predio)
    {
        if(corpo.getBounds2D().intersects(predio.corpo.getBounds()))
        {
            return true;
        }
        return false;
    }
}
