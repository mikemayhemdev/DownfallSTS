package charbosses.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackStar;
import downfall.downfallMod;

public class CBR_BlackStar extends AbstractCharbossRelic {
    public static final String ID = "BlackStar";
    private int numRelics;

    public CBR_BlackStar() {
        super(new BlackStar());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + this.numRelics + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.15F), DamageInfo.DamageType.HP_LOSS));

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BlackStar();
    }
}
