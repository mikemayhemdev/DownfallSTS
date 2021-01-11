package champ.cards;

import champ.ChampMod;
import champ.actions.CircumventAction;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Circumvent extends AbstractChampCard {

    public final static String ID = makeID("Circumvent");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 6;


    public Circumvent() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        // tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //  techique();
        blck();
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(magicNumber), magicNumber));
        if (dcombo()) {
            addToBot(new CircumventAction());
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        //tags.add(ChampMod.TECHNIQUE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}