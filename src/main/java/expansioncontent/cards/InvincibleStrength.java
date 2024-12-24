package expansioncontent.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.VexVinciblePower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class InvincibleStrength extends AbstractExpansionCard {
    public final static String ID = makeID("InvincibleStrength");

    public InvincibleStrength() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");

        tags.add(expansionContentMod.STUDY);
        loadJokeCardImage(this, "InvincibleStrength.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VexVinciblePower(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}

