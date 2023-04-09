package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import downfall.tutorials.CharbossTutorials;
import downfall.tutorials.GuardianTutorials;
import downfall.tutorials.HexaghostTutorials;
import hermit.util.HermitTutorials;

import java.io.IOException;

public class MessageCaller extends AbstractGameAction {
    private float startingDuration;
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
                    AbstractDungeon.ftue = new HexaghostTutorials();
                    break;
                case 3:
                    AbstractDungeon.ftue = new CharbossTutorials();
                    break;
            }
            downfallMod.unseenTutorials[code] = false;

            try {
                downfallMod.saveTutorialsSeen();
                this.isDone = true;
                ;
            } catch (IOException e) {
                e.printStackTrace();
                this.isDone = true;
            }
        }


    }
}