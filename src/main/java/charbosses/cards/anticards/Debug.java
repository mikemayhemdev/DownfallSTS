package charbosses.cards.anticards;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.powers.InverseBiasPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import expansioncontent.cards.AbstractExpansionCard;

public class Debug extends AbstractExpansionCard {
    public final static String ID = makeID("Debug");

    public Debug() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (m instanceof CharBossDefect) {
                    this.addToBot(new ApplyPowerAction(m, m, new FocusPower(m, -10), -10));
                    this.addToBot(new ApplyPowerAction(m, m, new InverseBiasPower(m, 5), 5));

                    this.isDone = true;// 18
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


