package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DollysMirror;

public class CBR_DollysMirror extends AbstractCharbossRelic {
    public static final String ID = "DollysMirror";

    private String addedDesc = "";

    public CBR_DollysMirror() {
        super(new DollysMirror());
        this.tier = RelicTier.RARE;
    }

    public CBR_DollysMirror(String cardName) {
        super(new DollysMirror());
        this.tier = RelicTier.RARE;
        addedDesc = cardName;

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedDesc;
    }


    @Override
    public void onEquip() {

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DollysMirror();
    }
}
