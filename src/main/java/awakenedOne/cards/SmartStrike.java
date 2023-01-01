package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class SmartStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(SmartStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , , 

    public SmartStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (p.hand.size() >= 6) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.hand.size() >= 5 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
    }
}