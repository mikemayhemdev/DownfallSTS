package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.SoulMark;
import collector.powers.Suffering;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class CullingBlow extends AbstractCollectorCard {
    public final static String ID = makeID("CullingBlow");

    public CullingBlow() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        douDamage = douBaseDamage = damage = baseDamage = 10;
        magicNumber = baseMagicNumber = 2;
        isMultiDamage = true;
        rearDealsDmg = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), 0.8F));

        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
         if (m.hasPower(Suffering.POWER_ID)) {
            applyToEnemy(m, new SoulMark(magicNumber, m));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}
