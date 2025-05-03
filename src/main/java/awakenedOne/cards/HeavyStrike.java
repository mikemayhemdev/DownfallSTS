package awakenedOne.cards;

import awakenedOne.actions.DrawPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.Drained;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class HeavyStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(HeavyStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public HeavyStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 13;
        secondDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(true);
        altDmg(mo, AbstractGameAction.AttackEffect.FIRE);
        this.addToBot(new ApplyPowerAction(p, p, new Drained(p,p, 1), 1));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeSecondDamage(3);
    }
}