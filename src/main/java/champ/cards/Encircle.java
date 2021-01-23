package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import downfall.util.CardIgnore;

import static champ.ChampMod.fatigue;

public class Encircle extends AbstractChampCard {

    public final static String ID = makeID("Encircle");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 7;

    public Encircle() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        //  tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // if (upgraded) techique();

        atb(new SFXAction("ATTACK_WHIRLWIND"));
        atb(new VFXAction(new WhirlwindEffect(), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

        for (AbstractMonster q : monsterList()) {
            techique();
        }

    }

    public void upp() {
        // tags.add(ChampMod.TECHNIQUE);
        upgradeDamage(3);
    }
}