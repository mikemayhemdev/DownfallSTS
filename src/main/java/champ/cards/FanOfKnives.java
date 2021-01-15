package champ.cards;

import champ.ChampMod;
import champ.vfx.DaggerSprayAnyColorEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class FanOfKnives extends AbstractChampCard {

    public final static String ID = makeID("FanOfKnives");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    private boolean found;

    public FanOfKnives() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new DaggerSprayAnyColorEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), Color.FIREBRICK), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

        if (bcombo()) {
            atb(new VFXAction(new DaggerSprayAnyColorEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), Color.RED), 0.0F));
            allDmg(AbstractGameAction.AttackEffect.NONE);
            if (upgraded) {

                atb(new VFXAction(new DaggerSprayAnyColorEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), Color.SCARLET), 0.0F));
                allDmg(AbstractGameAction.AttackEffect.NONE);
            }
        }
        berserkOpen();

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        // upgradeDamage(UPG_DAMAGE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}