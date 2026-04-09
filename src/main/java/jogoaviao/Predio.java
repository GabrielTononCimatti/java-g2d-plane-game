package jogoaviao;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;


public class Predio
{
    public int posX, posY, w, h, velocidade;
    public Color cor;
    public Rectangle2D.Double corpo = new Rectangle2D.Double();
    public boolean pintado=false;
    
    Predio(int posX, int velocidade, Color cor)
    {
        this.posX=posX;
        this.posY=0;
        this.w=50+new Random().nextInt(301);
        this.h=50+new Random().nextInt(301);
        this.velocidade=velocidade;
        this.cor=cor;
    }
    
    public void draw(Graphics2D g2d)
    {
        Color old=g2d.getColor();
        g2d.setColor(cor);
        corpo.setRect(posX, posY, w, h);
        g2d.fill(corpo);
        g2d.setColor(old);
    }
    
    public void move(Predio predioAnterior, Predio predioFinal)
    {
        posX-=velocidade;
        if(posX+w<=0)
        {
            if(predioFinal==null)
            {
                posX=predioAnterior.posX+predioAnterior.w+10+new Random().nextInt(100);
                if(posX<Monitor.LARGURA)
                {
                    posX=Monitor.LARGURA;
                }
                cor=Color.GRAY;
                pintado=false;
                w=50+new Random().nextInt(200);
                h=50+new Random().nextInt(360);
                return;
            }
            if(predioFinal.posX+predioFinal.w<=Monitor.LARGURA)
            {
                if(predioAnterior==null)
                {
                    posX=predioFinal.posX+predioFinal.w+10+new Random().nextInt(100);
                    if(posX<Monitor.LARGURA)
                    {
                        posX=Monitor.LARGURA;
                    }
                }
                else
                {
                    posX=predioAnterior.posX+predioAnterior.w+10+new Random().nextInt(100);
                    if(posX<Monitor.LARGURA)
                    {
                        posX=Monitor.LARGURA;
                    }
                }
                cor=Color.GRAY;
                pintado=false;
                w=50+new Random().nextInt(200);
                h=50+new Random().nextInt(360);
            }
        }
    }
    
    public boolean hit(Aviao plane)
    {
        if(corpo.getBounds2D().intersects(plane.cabine.getBounds()) || corpo.getBounds2D().intersects(plane.corpo.getBounds()) || corpo.getBounds2D().intersects(plane.asa.getBounds()) || corpo.getBounds2D().intersects(plane.cauda.getBounds()))
        {
            return true;
        }
        return false;
    }
}
