package collector.cards;

import collector.powers.SoulbindPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class ShadowWrappings extends AbstractCollectorCard {
    public final static String ID = makeID(ShadowWrappings.class.getSimpleName());
    // intellij stuff attack, enemy, common, 3, 1, , , 2, 

    public ShadowWrappings() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 3;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new DamageCallbackAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, (dealt) -> {
                att(new ApplyPowerAction(m, p, new SoulbindPower(m, dealt), dealt));
            }));
        }
    }

    public void upp() {
        upgradeDamage(1);
    }
}