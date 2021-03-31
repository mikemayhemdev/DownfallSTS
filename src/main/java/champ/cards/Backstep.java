package champ.cards;

import champ.ChampMod;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class Backstep extends AbstractChampCard {

    public final static String ID = makeID("Backstep");

    //stupid intellij stuff skill, self, uncommon

    public Backstep() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);

        baseBlock = 6;
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();
        if (bcombo()) {
            if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
                int x = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;
                atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, x));
            }
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        upgradeBaseCost(0);
    }
}