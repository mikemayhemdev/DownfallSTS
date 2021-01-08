package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.DeEnergizedPower;


public class HyperBeam extends AbstractExpansionCard {
    public final static String ID = makeID("HyperBeam");


    private static final int DAMAGE = 26;
    private static final int UPGRADE_DAMAGE = 8;

    public HyperBeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_automaton.png", "expansioncontentResources/images/1024/bg_boss_automaton.png");

        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new MindblastEffect(p.hb.cX, p.hb.cY, p.flipHorizontal), 0.1F));
        atb(new WaitAction(0.1F));

        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new ApplyPowerAction(p, p, new DeEnergizedPower(1), 1));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}
