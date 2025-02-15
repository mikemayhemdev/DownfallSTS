package sneckomod.cards;

import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class TrashToTreasure extends AbstractSneckoCard {

    public final static String ID = makeID("TrashToTreasure");

    //stupid intellij stuff SKILL, SELF, UNCOMMON
    private static final int BASE_BLOCK = 9;

    public TrashToTreasure() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "TrashToTreasure.png");
        baseBlock = BASE_BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new RecycleAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}