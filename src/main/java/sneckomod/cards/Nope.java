package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.NopeAction;

public class Nope extends AbstractSneckoCard {

    public final static String ID = makeID("Nope");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Nope() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "Nope.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new NopeAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}