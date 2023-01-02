package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Uncaged extends AbstractAwakenedCard {
    public final static String ID = makeID(Uncaged.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 14, 4, , , , 

    public Uncaged() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            atb(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }

        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean usedPower = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().anyMatch(q -> q.type == CardType.POWER);
        if (usedPower) {
            return super.canUse(p, m);
        } else {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
    }

    public void upp() {
        upgradeDamage(4);
    }
}