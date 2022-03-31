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
    public void initializeDescription() {

        if (AbstractDungeon.player !=null) {
            AbstractPlayer p = AbstractDungeon.player;
            this.rawDescription = DESCRIPTION;

            if (p.hasPower(VigorPower.POWER_ID)) {
                block = baseBlock + p.getPower(VigorPower.POWER_ID).amount;
            }


            this.rawDescription = this.rawDescription + " NL " + EXTENDED_DESCRIPTION[0] + baseBlock + EXTENDED_DESCRIPTION[1];

        }
    }

    @Override
    public void applyPowers() {
        block = baseBlock;
        super.applyPowers();

    }


    public void upp() {
        upgradeBlock(3);
    }
}