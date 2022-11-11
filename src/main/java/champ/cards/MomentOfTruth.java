package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class MomentOfTruth extends AbstractChampCard {

    public final static String ID = makeID("MomentOfTruth");

    //stupid intellij stuff attack, enemy, rare

    public MomentOfTruth() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        selfRetain = true;
        tags.add(ChampMod.FINISHER);
        baseMagicNumber = magicNumber = 1;
        postInit();
        loadJokeCardImage(this, "ShieldSlam.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new DrawCardAction(magicNumber));

        finisher();
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}