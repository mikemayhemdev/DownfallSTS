package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.cards.unknowns.AbstractUnknownCard;

public class DiceBlock extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBlock");

    public DiceBlock() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 12; //Min Block
        baseBlock = 16; //Max Block
        this.tags.add(SneckoMod.RNG);
        this.tags.add(SneckoMod.OVERFLOW); // small buff with overflow mechanic
        SneckoMod.loadJokeCardImage(this, "DiceBlock.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, getRandomNum(magicNumber, block, this)));
        if (isOverflowActive()) {
                block = baseBlock;
        }
    }

    @Override
    protected void applyPowersToBlock() {
        int CURRENT_MAGIC_NUMBER = baseMagicNumber;
        int CURRENT_BLOCK = baseBlock;
        baseBlock = CURRENT_MAGIC_NUMBER;
        super.applyPowersToBlock();
        magicNumber = block;
        isMagicNumberModified = block != baseBlock;

        baseBlock = CURRENT_BLOCK;
        super.applyPowersToBlock();
        isBlockModified = baseBlock != block;
    }

    //Glowverflow - make the card glow if overflow is active~
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isOverflowActive()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            upgradeBlock(4);
        }
    }
}