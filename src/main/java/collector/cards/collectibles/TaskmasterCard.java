package collector.cards.collectibles;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.applyToSelf;

public class TaskmasterCard extends AbstractCollectibleCard {
    public final static String ID = makeID(TaskmasterCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 7, 2, , , 1, 1

    public TaskmasterCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 10;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, secondMagic));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
        upgradeSecondMagic(3);
    }
}