package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import downfall.cards.MajorBeam;
import expansioncontent.actions.ExhaustVirusAction;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.DeEnergizedPower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;


public class Virus extends AbstractExpansionCard {
    public final static String ID = makeID("Virus");


    private static final int MAGIC = 3;

    public Virus() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        //todo skill bg instead of attack bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_automaton.png", "expansioncontentResources/images/1024/bg_boss_automaton.png");

        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);
        cardsToPreview = new MajorBeam();
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;

        loadJokeCardImage(this, "Virus.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExhaustVirusAction(magicNumber, false, true, true));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
