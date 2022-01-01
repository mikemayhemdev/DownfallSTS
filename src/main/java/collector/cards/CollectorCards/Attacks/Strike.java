package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorChar.getTorchHead;

public class Strike extends AbstractCollectorCard {
    public final static String ID = makeID("Strike");

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY,DTCardTarget.BOTH);
        baseDamage = 6;
        douDamage = douBaseDamage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontTorchHead()) {
            atb(new DamageAction(m, new DamageInfo(getTorchHead(), douDamage, DamageInfo.DamageType.NORMAL)));
        } else dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeDTDragonDamage(3);
    }
}
