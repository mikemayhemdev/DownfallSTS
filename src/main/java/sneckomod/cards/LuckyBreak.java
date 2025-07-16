package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;


public class LuckyBreak extends AbstractSneckoCard {

    public final static String ID = makeID("LuckyBreak");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 1;
    private static final int BASE_DRAW = 0;

    public LuckyBreak() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "LuckyBreak.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        int additionalHits = 0;

        if (this.costForTurn >= 2) {
            additionalHits++;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.costForTurn >= 2 && card != this) {
                additionalHits++;
            }
        }
        int totalHits = BASE_DRAW + additionalHits;
        addToBot(new DrawCardAction(p, totalHits));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}