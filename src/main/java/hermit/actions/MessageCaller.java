package hermit.actions;

import hermit.HermitMod;
import hermit.util.HermitTutorials;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.io.IOException;

public class MessageCaller extends AbstractGameAction {
    private float startingDuration;
    private DamageInfo info;
    private boolean isupgraded;
    public int code;

    public MessageCaller(int code) {

        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.code = code;


    }


    public void update() {

        if (HermitMod.activeTutorials[code]) {
            AbstractDungeon.ftue = new HermitTutorials();
            HermitMod.activeTutorials[code] = false;

            try {
                HermitMod.saveData();
                this.isDone = true;;
            } catch (IOException e) {
                e.printStackTrace();
                this.isDone = true;
            }
        }


    }
}