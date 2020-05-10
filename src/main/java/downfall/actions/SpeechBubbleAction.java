package downfall.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;


public class SpeechBubbleAction extends AbstractGameAction {
    public String Dialog;
    private AbstractMonster m;
    private float duration;

    public SpeechBubbleAction(String Dialog, AbstractMonster m, float duration) {
        this.Dialog = Dialog;
        this.m = m;
        this.duration = duration;

    }


    public void update() {
        AbstractDungeon.effectList.add(new SpeechBubble(m.hb.cX + m.dialogX, m.hb.cY + m.dialogY, duration, Dialog, false));
        this.isDone = true;
    }

}



