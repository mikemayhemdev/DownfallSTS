package charbosses.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.relics.BrassTacks;

public class CBR_BrassTacks extends AbstractCharbossRelic {
    public static final String ID = "BrassTacks";

    public CBR_BrassTacks() {
        super(new BrassTacks());
        setTextureOutline(BrassTacks.IMG, BrassTacks.OUTLINE);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new MetallicizePower(this.owner, 2)));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }


    public void setTexture(Texture t) {
        this.img = t;
        this.outlineImg = t;
    }

    public void setTextureOutline(Texture t, Texture o) {
        this.img = t;
        this.outlineImg = o;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BrassTacks();
    }
}
