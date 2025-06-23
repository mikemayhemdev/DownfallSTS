package awakenedOne.cards;

import awakenedOne.patches.OnLoseEnergyPowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;

public class Brainshock extends AbstractAwakenedCard {
    public final static String ID = makeID(Brainshock.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Brainshock() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 14;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        }
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * OnLoseEnergyPowerPatch.EnergyLostThisCombat;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}