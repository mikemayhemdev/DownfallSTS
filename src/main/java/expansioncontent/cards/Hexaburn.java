package expansioncontent.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.PretendHexWheelPower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;


public class Hexaburn extends AbstractExpansionCard {
    public final static String ID = makeID("Hexaburn");

    public Hexaburn() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_hexaghost.png", "expansioncontentResources/images/1024/bg_boss_hexaghost.png");
        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = magicNumber = 2;
        baseDamage = 4;
        loadJokeCardImage(this, "Hexaburn.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PretendHexWheelPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}





