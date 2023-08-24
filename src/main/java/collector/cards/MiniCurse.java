package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class MiniCurse extends AbstractCollectorCard {
    public final static String ID = makeID(MiniCurse.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public MiniCurse() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            forAllMonstersLiving(q -> {
                atb(new VFXAction(new FlashAtkImgEffect(q.hb.cX, q.hb.cY, AbstractGameAction.AttackEffect.POISON)));
                applyToEnemy(q, new WeakPower(q, 1, false));
                applyToEnemy(q, new VulnerablePower(q, 1, false));
            });
        }
        else {
            atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.POISON)));
            applyToEnemy(m, new WeakPower(m, 1, false));
            applyToEnemy(m, new VulnerablePower(m, 1, false));
        }
    }

    public void upp() {
        target = CardTarget.ALL_ENEMY;
        uDesc();
    }
}