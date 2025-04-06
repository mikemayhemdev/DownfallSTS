package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.DeEnergizedPower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;


public class HyperBeam extends AbstractExpansionCard {
    public final static String ID = makeID("HyperBeam");


    private static final int DAMAGE = 17;
    private static final int UPGRADE_DAMAGE = 4;

    public HyperBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_automaton.png", "expansioncontentResources/images/1024/bg_boss_automaton.png");

        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);
        cardsToPreview = new VoidCard();
        baseDamage = DAMAGE;
       this.exhaust = true;
        this.isMultiDamage = true;
        loadJokeCardImage(this, "HyperBeam.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new MindblastEffect(p.hb.cX, p.hb.cY, p.flipHorizontal), 0.1F));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
        allDmg(AbstractGameAction.AttackEffect.NONE);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}
