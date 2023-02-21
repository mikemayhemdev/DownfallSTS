package charbosses.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;

public class CBR_DarkstonePeriapt extends AbstractCharbossRelic {
    public static final String ID = "Darkstone Periapt";

    public CBR_DarkstonePeriapt() {
        super(new DarkstonePeriapt());
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 6 + LocalizedStrings.PERIOD;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DarkstonePeriapt();
    }
}

