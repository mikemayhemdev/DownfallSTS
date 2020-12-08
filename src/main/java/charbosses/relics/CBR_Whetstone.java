package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Whetstone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CBR_Whetstone extends AbstractCharbossRelic {
    public static final String ID = "Whetstone";

    public CBR_Whetstone() {
        super(new Whetstone());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Whetstone();
    }
}
