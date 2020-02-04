package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.powers.UnknownUpgradedPostCombatPower;

public class MorePower extends AbstractSneckoCard {

    public final static String ID = makeID("MorePower");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public MorePower() {
        super(ID,  1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new UnknownUpgradedPostCombatPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}