package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import theHexaghost.patches.ExhaustCardTickPatch;

public class GhostShield extends AbstractHexaCard {

    public final static String ID = makeID("GhostShield");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 6;
    private static final int MAGIC = 1;
    private static final int UPG_BLOCK = 4;

    public GhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (ExhaustCardTickPatch.exhaustedLastTurn)
            applyToSelf(new BlurPower(p, MAGIC));
    }

    @Override
    public void afterlife() {
        use(AbstractDungeon.player, null);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = ExhaustCardTickPatch.exhaustedLastTurn ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }// 68

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}