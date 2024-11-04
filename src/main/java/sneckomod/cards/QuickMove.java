package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleRandomCardAction;

public class QuickMove extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("QuickMove");

    private static final int BASE_BLOCK = 8;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public QuickMove() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        SneckoMod.loadJokeCardImage(this, "QuickMove.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new MuddleRandomCardAction(magicNumber, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
