package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.SmilingMask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CBR_BottledLightning extends AbstractCharbossRelic {
    public static final String ID = "Bottled Lightning";

    public CBR_BottledLightning() {
        super(new BottledLightning());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BottledLightning();
    }
}