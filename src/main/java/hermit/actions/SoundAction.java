package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

public class SoundAction extends AbstractGameAction {
    private final String sfx;

    public SoundAction(String sound) {
        sfx = sound;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        CardCrawlGame.sound.play(sfx);

        this.isDone=true;
    }

}