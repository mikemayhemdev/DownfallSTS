package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class RotwoodKindling extends AbstractCollectorCard {
    public final static String ID = makeID(RotwoodKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 3, 2

    public RotwoodKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 4;
        tags.add(expansionContentMod.UNPLAYABLE);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        forAllMonstersLiving(q -> {
            applyToEnemy(q, new VulnerablePower(q, magicNumber, false));
            applyToEnemy(q, new DoomPower(q, secondMagic));
        });
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(2);
    }
}
