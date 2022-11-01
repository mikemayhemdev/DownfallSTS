package champ.cards;

import champ.powers.PushThroughPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class IgnorePain extends AbstractChampCard {

    public final static String ID = makeID("IgnorePain");

    //stupid intellij stuff skill, self, rare

    public IgnorePain() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        // tags.add(ChampMod.TECHNIQUE);
        //  myHpLossCost = 5;
        postInit();
        loadJokeCardImage(this, "IgnorePain.png");

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // techique();
        // fatigue(5);
        applyToSelf(new PushThroughPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}