package awakenedOne.cards;

import awakenedOne.actions.ForTheHexAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeID;
@Deprecated
@CardIgnore
public class EyeScratch extends AbstractAwakenedCard {
    public final static String ID = makeID(EyeScratch.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public EyeScratch() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new ForTheHexAction(this.magicNumber, m));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }

    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}