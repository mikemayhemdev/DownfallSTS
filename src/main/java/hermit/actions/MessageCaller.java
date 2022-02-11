package hermit.actions;

import downfall.downfallMod;
import downfall.tutorials.GuardianTutorials;
import hermit.HermitMod;
import hermit.util.HermitTutorials;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.io.IOException;
import java.security.Guard;

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

        if (downfallMod.unseenTutorials[code]) {
            switch (code) {
                case 0:
                    AbstractDungeon.ftue = new HermitTutorials();
                    break;
                case 1:
                    AbstractDungeon.ftue = new GuardianTutorials();
                    break;
                case 2:
                    AbstractDungeon.ftue = new GuardianTutorials();
                    break;
            }
            downfallMod.unseenTutorials[code] = false;

            try {
                downfallMod.saveTutorialsSeen();
                this.isDone = true;;
            } catch (IOException e) {
                e.printStackTrace();
                this.isDone = true;
            }
        }


    }
}