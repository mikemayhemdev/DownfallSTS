package expansioncontent.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.PretendHexWheelPower;


public class Hexaburn extends AbstractExpansionCard {
    public final static String ID = makeID("Hexaburn");

    public Hexaburn() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_hexaghost.png", "expansioncontentResources/images/1024/bg_boss_hexaghost.png");

        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PretendHexWheelPower(5));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

}





