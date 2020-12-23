package downfall.unlocks;

import automaton.AutomatonChar;
import champ.ChampChar;
import com.megacrit.cardcrawl.core.Settings;

public class AutomatonUnlock extends com.megacrit.cardcrawl.unlock.AbstractUnlock
         {
       public static final String KEY = "Automaton";

       public AutomatonUnlock()
       {
             this.type = UnlockType.CHARACTER;
             this.key = "Automaton";
             this.title = "Automaton";
           }
    
       public void onUnlockScreenOpen()
       {
             this.player = com.megacrit.cardcrawl.core.CardCrawlGame.characterManager.getCharacter(AutomatonChar.Enums.THE_AUTOMATON);
             this.player.drawX = (Settings.WIDTH / 2.0F - 20.0F * Settings.scale);
             this.player.drawY = (Settings.HEIGHT / 2.0F + 50.0F * Settings.scale);
           }
     }