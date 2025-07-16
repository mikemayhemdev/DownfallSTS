package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class SoulRoll extends AbstractSneckoCard {

    public final static String ID = makeID("SoulRoll");

    //stupid intellij stuff SKILL, SELF, COMMON
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;

    public SoulRoll() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "SoulRoll.png");
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        this.selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new SelectCardsInHandAction(magicNumber, BaseMod.getKeywordProper("sneckomod:muddle"),
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleAction(card));
                    }
                }
        ));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);

        }
    }
}