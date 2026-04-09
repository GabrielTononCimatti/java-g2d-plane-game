package jogoaviao;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.nio.DoubleBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.lang.Math;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import java.lang.Math;

public class JogoAviao extends Frame{

    Image offscreenImage;
    Graphics2D offscreenGraphics;
    int mouseX, mouseY, LARGURA, ALTURA, presenteCount=0, points=0;
    boolean firstTime=true, atirou=false, fim=false;
    AffineTransform originalTransform, changedTransform;
    Aviao plane;
    Nuvem[] nuvem = new Nuvem[10];
    Predio[] predio = new Predio[10];
    Presente[] presente = new Presente[100];
    
    JogoAviao()
    {
        addWindowListener(new MyFinishWindow());
    }
    
    public void write(String palavra, int x, int y, Color cor, int tamanho, Graphics2D g2d)
    {   
        Color old=g2d.getColor();
        g2d.setTransform(originalTransform);
        g2d.setFont(new Font("Dialog", Font.PLAIN, tamanho));
        g2d.setColor(cor);
        g2d.drawString(palavra, x, ALTURA-y);
        g2d.setTransform(changedTransform);
        g2d.setColor(old);
    }
    @Override
    public void update(Graphics g)
    {
        if (offscreenImage == null || offscreenImage.getWidth(this) != getWidth() || offscreenImage.getHeight(this) != getHeight()) {
            offscreenImage = createImage(getWidth(), getHeight());
            offscreenGraphics = (Graphics2D) offscreenImage.getGraphics();
        }
        offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());
        paint(offscreenGraphics);
        g.drawImage(offscreenImage, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void atirar(Graphics2D g2d)
    {
        if(Teclado.SPACE && !atirou)
        {
            presente[presenteCount] = new Presente(plane.posX, plane.posY-30, plane.vX*plane.direction, Color.RED);
            presenteCount++;
            atirou=true;
        }
        if(!Teclado.SPACE)
        {
            atirou=false;
        }
    }
    
    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d=(Graphics2D) g;
        
        //setup
        if(firstTime)
        {
            originalTransform = g2d.getTransform();
            changedTransform = AffineTransform.getTranslateInstance(0, ALTURA);
            changedTransform.scale(1, -1);
            plane = new Aviao(500, 500, 10, 10, Color.GRAY, Color.BLACK);
            
            nuvem[0] = new Nuvem(LARGURA, 10, Color.WHITE);
            for(int i=1; i<10; i++)
            {
                nuvem[i] = new Nuvem(nuvem[i-1].posX+nuvem[i-1].w+10+new Random().nextInt(100), 10, Color.WHITE);
            }
            
            
            predio[0] = new Predio(LARGURA, 10, Color.GRAY);
            for(int i=1; i<10; i++)
            {
                predio[i] = new Predio(predio[i-1].posX+predio[i-1].w+10+new Random().nextInt(100), 10, Color.GRAY);
            }
            firstTime = false;
        }
        
        //loop
        this.mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
        this.mouseY = 1079-(int)MouseInfo.getPointerInfo().getLocation().getY();
        g2d.setTransform(changedTransform);
        
        g2d.setColor(Color.getHSBColor(0.547f, 0.43f, 0.92f));
        g2d.fillRect(0, 0, LARGURA, ALTURA);

        plane.draw(g2d);
        if(!fim) plane.move();
        
        for(int i=0; i<100; i++)
        {
            if(presente[i]!=null)
            {
                presente[i].draw(g2d);
                if(!fim) presente[i].move();
                if(presente[i].posY==0)
                {
                    presente[i]=null;
                    presenteCount--;
                }
            }
        }
        
        nuvem[0].draw(g2d);
        if(!fim) nuvem[0].move(null, nuvem[nuvem.length-1]);
        for(int i=1; i<nuvem.length-1; i++)
        {
            nuvem[i].draw(g2d);
            if(!fim) nuvem[i].move(nuvem[i-1], nuvem[nuvem.length-1]);
        }
        nuvem[nuvem.length-1].draw(g2d);
        if(!fim) nuvem[nuvem.length-1].move(nuvem[nuvem.length-2], null);
        
        predio[0].draw(g2d);
        if(!fim) predio[0].move(null, predio[predio.length-1]);
        for(int i=1; i<predio.length-1; i++)
        {
            predio[i].draw(g2d);
            if(!fim) predio[i].move(predio[i-1], predio[predio.length-1]);
        }
        predio[predio.length-1].draw(g2d);
        if(!fim) predio[predio.length-1].move(predio[predio.length-2], null);
        
        if(!fim) atirar(g2d);

        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, LARGURA, ALTURA);
        
        for(int i=0; i<10; i++)
        {
            if(nuvem[i].hit(plane) || predio[i].hit(plane))
            {
                if(!fim)
                {
                    fim = true;
                    JOptionPane.showMessageDialog(null, "Perdeu");
                    System.exit(0);
                }
            }
        }
        for(int i=0; i<100; i++)
        {
            if(presente[i]!=null)
            {    
                for(int j=0; j<10; j++)
                {
                    if(presente[i].hit(predio[j]))
                    {
                        if(!predio[j].pintado)
                        {
                            points++;
                        }
                        presenteCount--;
                        presente[i]=null;
                        predio[j].cor=Color.RED;
                        predio[j].pintado=true;
                    }
                }
            }
        }
        write("Pontos: "+points, 70, ALTURA-30, Color.BLACK, 20, g2d);
        /*if(Teclado.RIGHT)
            write("→", 70, 97, Color.RED, 50, g2d);
        else
            write("→", 70, 97, Color.GRAY, 50, g2d);
        if(Teclado.LEFT)
            write("←", 10, 97, Color.RED, 50, g2d);
        else
            write("←", 10, 97, Color.GRAY, 50, g2d);
        if(Teclado.UP)
            write("↑", 50, 120, Color.RED, 50, g2d);
        else
            write("↑", 50, 120, Color.GRAY, 50, g2d);
        if(Teclado.DOWN)
            write("↓", 50, 70, Color.RED, 50, g2d);
        else
            write("↓", 50, 70, Color.GRAY, 50, g2d);*/
        
    }

    public static void main(String[] args)
    {
        JogoAviao jogo = new JogoAviao();
        jogo.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jogo.setUndecorated(true);
        jogo.setVisible(true);
        Monitor.LARGURA=jogo.getWidth()-1;
        Monitor.ALTURA=jogo.getHeight()-1;
        jogo.LARGURA=jogo.getWidth()-1;
        jogo.ALTURA=jogo.getHeight()-1;
        jogo.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    Teclado.RIGHT = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    Teclado.LEFT = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    Teclado.UP = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    Teclado.DOWN = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    jogo.fim=true;
                    JOptionPane.showMessageDialog(null, "Programa Finalizado");
                    System.exit(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    Teclado.SPACE = true;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                   Teclado.RIGHT = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    Teclado.LEFT = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    Teclado.UP = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    Teclado.DOWN = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    Teclado.SPACE = false;
                }
            }
        });
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(jogo::repaint, 0, 17, TimeUnit.MILLISECONDS);
    }
}