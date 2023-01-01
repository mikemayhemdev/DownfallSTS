package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class Brightblind extends AbstractAwakenedCard {
    public final static String ID = makeID(Brightblind.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 5, 1, , , 1, 1

    public Brightblind() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractMonster q = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                att(new ApplyPowerAction(q, p, new VulnerablePower(q, magicNumber, false)));
                att(new ApplyPowerAction(q, p, new WeakPower(q, magicNumber, false)));
                dmgTop(q, AttackEffect.SLASH_VERTICAL);
            }
        });
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}