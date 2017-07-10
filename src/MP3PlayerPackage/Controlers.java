/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MP3PlayerPackage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Controlers {
    public String location;
    public long pauseLocation;
    FileInputStream FIS;
    BufferedInputStream BIS;
    public Player player;
    public long songTotalLength;
    
    public void stop(){
        if (player!=null) {
            player.close();
            pauseLocation=0;
            songTotalLength=0;
            MP3PlayerGUII.songTitleR.setText("");
        }
    }
    public void pause(){
        if (player!=null) {
            try {
                pauseLocation=FIS.available();
                player.close();
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
    }
    public void play(String path){
        try {
            FIS=new FileInputStream(path);
            BIS=new BufferedInputStream(FIS);
            player =new Player(BIS);
            songTotalLength = FIS.available();
            location=path+"";
        } catch (FileNotFoundException | JavaLayerException ex) {
           ex.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        new Thread(){
            @Override
            public void run(){ 
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                   ex.getStackTrace();
                }
            
            }
        }.start();
    }
    
    
    public void resume(){
        try {
            FIS=new FileInputStream(location);
            BIS=new BufferedInputStream(FIS);
            player =new Player(BIS);
            FIS.skip(songTotalLength-pauseLocation);
        } catch (FileNotFoundException | JavaLayerException ex) {
           ex.getStackTrace();
        } catch (IOException ex) {
            ex.getMessage();
        }
        new Thread(){
            @Override
            public void run(){ 
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                   ex.getStackTrace();
                }
            
            }
        }.start();
    }
    
}
