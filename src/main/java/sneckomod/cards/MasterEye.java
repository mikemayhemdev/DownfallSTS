package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.DrawPower;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

@Deprecated
@CardIgnore
public class MasterEye extends AbstractSneckoCard {
    public final static String ID = makeID("MasterEye");

    public MasterEye() {
        super(ID, 3, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        rawDescription = UPGRADE_DESCRIPTION;
        tags.add(SneckoMod.SNEKPROOF);
        initializeDescription();
        SneckoMod.loadJokeCardImage(this, "MasterEye.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ConfusionPower(p));
        applyToSelf(new DrawPower(p, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}