
package jogoaviao;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Random;

public class Nuvem
{
    public int posX, posY, velocidade, w, h;
    public double escalaX, escalaY;
    public Color cor;
    public Arc2D.Double[] arc = new Arc2D.Double[3];
    
    Nuvem(int posX, int velocidade, Color cor)
    {
        this.posX=posX;
        this.escalaX=0.7+Math.abs(Math.random());
        this.escalaY=0.7+Math.abs(Math.random());
        this.w=(int)(250*escalaX);
        this.h=(int)(100*escalaY);
        this.posY=540+new Random().nextInt(540-h);
        this.velocidade=12;
        this.cor=cor;
        arc[0] = new Arc2D.Double();
        arc[1] = new Arc2D.Double();
        arc[2] = new Arc2D.Double();  
    }
    
    public void draw(Graphics2D g2d)
    {
        Color old=g2d.getColor();
        g2d.setColor(cor);
        arc[0].setArc(posX+(int)(100*escalaX), posY, (int)(150*escalaX), (int)(80*escalaY), 0, 360, Arc2D.PIE);
        arc[1].setArc(posX+(int)(60*escalaX), posY+(int)(20*escalaY), (int)(120*escalaX), (int)(80*escalaY), 0, 360, Arc2D.PIE);
        arc[2].setArc(posX, posY, (int)(120*escalaX), (int)(80*escalaY), 0, 360, Arc2D.PIE);
        g2d.fill(arc[0]);
        g2d.fill(arc[1]);
        g2d.fill(arc[2]);
        g2d.setColor(old);
    }
    
    public void move(Nuvem nuvemAnterior, Nuvem nuvemFinal)
    {
        posX-=velocidade;
        if(posX+w<=0)
        {
            if(nuvemFinal==null)
            {
                posX=nuvemAnterior.posX+nuvemAnterior.w+10+new Random().nextInt(100);
                if(posX<Monitor.LARGURA)
                {
                    posX=Monitor.LARGURA;
                }
                posY=540+new Random().nextInt(540-h);
                escalaX=0.7+Math.abs(Math.random());
                escalaY=0.7+Math.abs(Math.random());
                w=(int)(250*escalaX);
                h=(int)(100*escalaY);
                return;
            }
            if(nuvemFinal.posX+nuvemFinal.w<=Monitor.LARGURA)
            {
                if(nuvemAnterior==null)
                {
                    posX=nuvemFinal.posX+nuvemFinal.w+10+new Random().nextInt(100);
                    if(posX<Monitor.LARGURA)
                    {
                        posX=Monitor.LARGURA;
                    }
                }
                else
                {
                    posX=nuvemAnterior.posX+nuvemAnterior.w+10+new Random().nextInt(100);
                    if(posX<Monitor.LARGURA)
                    {
                        posX=Monitor.LARGURA;
                    }
                }
                posY=540+new Random().nextInt(540-h);
                escalaX=0.7+Math.abs(Math.random());
                escalaY=0.7+Math.abs(Math.random());
                w=(int)(250*escalaX);
                h=(int)(100*escalaY);
            }
        }
    }  
    public boolean hit(Aviao plane)
    {
        for(int i=0; i<arc.length; i++)
        {
            if(arc[i].getBounds2D().intersects(plane.cabine.getBounds()) || arc[i].getBounds2D().intersects(plane.corpo.getBounds()) || arc[i].getBounds2D().intersects(plane.asa.getBounds()) || arc[i].getBounds2D().intersects(plane.cauda.getBounds()))
            {
                return true;
            }
        }
        return false;
    }
}
