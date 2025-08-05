package awakenedOne.cards;

import awakenedOne.patches.OnLoseEnergyPowerPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Unleash extends AbstractAwakenedCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Unleash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath("Unleash.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += AbstractDungeon.player.hand.size() - 1;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += AbstractDungeon.player.hand.size() - 1;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}