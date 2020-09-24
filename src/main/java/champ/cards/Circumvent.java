package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Circumvent extends AbstractChampCard {

    public final static String ID = makeID("Circumvent");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public Circumvent() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        blck();
        if (dcombo()) addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
    }
}