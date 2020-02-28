package charbosses.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Mango;

public class CBR_Mango extends AbstractCharbossRelic {
    public static final String ID = "Mango";

    public CBR_Mango() {
        super(new Mango());
        this.tier = RelicTier.UNCOMMON;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 14 + LocalizedStrings.PERIOD;
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(14, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Mango();
    }
}