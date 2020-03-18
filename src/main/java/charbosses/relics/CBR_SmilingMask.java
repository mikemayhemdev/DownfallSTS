package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SmilingMask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CBR_SmilingMask extends AbstractCharbossRelic {
    public static final String ID = "Smiling Mask";

    public CBR_SmilingMask() {
        super(new SmilingMask());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SmilingMask();
    }
}
