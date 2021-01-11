package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class TornadoPunch extends AbstractChampCard {

    public final static String ID = makeID("TornadoPunch");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;

    public TornadoPunch() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (upgraded) techique();
        atb(new SFXAction("ATTACK_WHIRLWIND"));
        atb(new VFXAction(new WhirlwindEffect(), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (dcombo())
            for (AbstractMonster q : monsterList()) {
                atb(new ApplyPowerAction(p, p, new CounterPower(magicNumber), magicNumber));
                atb(new GainBlockAction(p, block));
            }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        //  tags.add(ChampMod.TECHNIQUE);
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
        upgradeBlock(UPG_BLOCK);
    }
}