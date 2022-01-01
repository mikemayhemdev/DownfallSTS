package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.SoulMark;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class SoulHarvest extends AbstractCollectorCard {
    public final static String ID = makeID("SoulHarvest");

    public SoulHarvest() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        baseDamage = 6;
        douBaseDamage = 6;
        isMultiDamage = true;
        selfRetain = true;
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
        rearDealsDmg = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new CleaveEffect()));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            applyToEnemy(mo, new SoulMark(magicNumber,mo));
            dmg(mo, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.FIRE);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}