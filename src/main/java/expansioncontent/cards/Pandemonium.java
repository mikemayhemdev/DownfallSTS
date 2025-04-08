package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MayhemPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.PandemoniumPower;
import expansioncontent.powers.VexVinciblePower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class Pandemonium extends AbstractExpansionCard {
    public final static String ID = makeID("Pandemonium");

    public Pandemonium() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = magicNumber = 1;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
        loadJokeCardImage(this, "Pandemonium.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new PandemoniumPower(p, this.magicNumber)));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}

