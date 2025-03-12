package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.orbs.StasisOrb;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;

public class StasisField extends AbstractGuardianCard implements InStasisCard {
    public static final String ID = GuardianMod.makeID("StasisField");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/stasisField.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public StasisField() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.baseBlock = 6;
        this.socketCount = 0;
        this.tags.add(GuardianMod.SELFSTASIS);
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("StasisField.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        this.useGems(p, m);
    }

//    @Override
//    public void onEvoke(StasisOrb orb) {
//        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
//    }


    public AbstractCard makeCopy() {
        return new StasisField();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }

    @Override
    public void onStartOfTurn(StasisOrb orb) {

    }
}