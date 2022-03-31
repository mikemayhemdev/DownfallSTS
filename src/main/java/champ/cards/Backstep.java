package champ.cards;

import champ.ChampMod;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);

        baseBlock = 6;
        baseMagicNumber = magicNumber = 1;
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        //tags.add(ChampMod.COMBO);
        //tags.add(ChampMod.COMBOBERSERKER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();

    }

    @Override
    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        super.applyPowers();
        if (bcombo()) {
            if (p.hasPower(VigorPower.POWER_ID)) {
                baseBlock = 6 + p.getPower(VigorPower.POWER_ID).amount;
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        upgradeBlock(3);
    }
}