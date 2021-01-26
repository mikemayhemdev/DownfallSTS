package expansioncontent.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.VexVinciblePower;

public class InvincibleStrength extends AbstractExpansionCard {
    public final static String ID = makeID("InvincibleStrength");

    public InvincibleStrength() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");

        tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VexVinciblePower(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}

