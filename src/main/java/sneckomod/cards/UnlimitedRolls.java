package sneckomod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.powers.UnlimitedRollsPower;

public class UnlimitedRolls extends AbstractSneckoCard {

    public final static String ID = makeID("UnlimitedRolls");

    //stupid intellij stuff POWER, SELF, RARE

    public UnlimitedRolls() {
        super(ID,  1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        AbstractCard q = new SoulRoll();
        q.exhaust = true;
        q.rawDescription = q.rawDescription + " NL Exhaust.";
        q.initializeDescription();
        cardsToPreview = q;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new UnlimitedRollsPower(upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            AbstractCard q = new SoulRoll();
            q.upgrade();
            q.exhaust = true;
            q.rawDescription = q.rawDescription + " NL Exhaust.";
            q.initializeDescription();
            cardsToPreview = q;
            initializeDescription();
        }
    }
}