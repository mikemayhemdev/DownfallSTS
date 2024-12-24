package expansioncontent.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.BeatOfDeathThatDoesntKillYouPower;
import expansioncontent.powers.VexVinciblePower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class BeatOfDeath extends AbstractExpansionCard {
    public final static String ID = makeID("BeatOfDeath");

    private static final int MAGIC = 2;

    public BeatOfDeath() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");
        tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        loadJokeCardImage(this, "BeatOfDeath.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BeatOfDeathThatDoesntKillYouPower(p, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}

