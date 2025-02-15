package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.HoleUp;
import slimebound.cards.SlimeCrush;
import sneckomod.SneckoMod;

public class Cower extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Cower");

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;

    public Cower() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "Cower.png");
        magicNumber = baseMagicNumber = MAGIC;
        this.exhaust = true;
        this.cardsToPreview = new HoleUp();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractCard g = new HoleUp();
        if (this.upgraded) {
            g.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            AbstractCard q = new HoleUp();
            q.upgrade();
            cardsToPreview = q;
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
