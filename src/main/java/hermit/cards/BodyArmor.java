package hermit.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.BodyArmorAction;
import hermit.characters.hermit;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class BodyArmor extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(BodyArmor.class.getSimpleName());
    public static final String IMG = makeCardPath("body_armor.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = downfallMod.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/


    public BodyArmor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        loadJokeCardImage(this, "body_armor.png");
    }

    /*
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            AbstractCard s = (new Defend_Hermit()).makeCopy();
            s.upgrade();
            this.addToTop(new MakeTempCardInDiscardAction(s, 1));
        } else {
            this.addToTop(new MakeTempCardInDiscardAction(new Defend_Hermit(), 1));
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }
    */

    // Actions the card should actually unironically literally do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BodyArmorAction(p, p, 1, block, false));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
