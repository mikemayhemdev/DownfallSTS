package slimebound.cards;


import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import sneckomod.SneckoMod;


public class SlimeTap extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SlimeTap";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/slimetap.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    private int numEaten = 0;


    public SlimeTap() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;

        //this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (canUse) {

            canUse = false;
            for (AbstractOrb o : p.orbs) {
                if (o instanceof SpawnedSlime) {
                    canUse = true;
                }
            }
            if (!canUse) this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        }
        return canUse;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractOrb o = SlimeboundMod.getLeadingSlime();
        if (o != null) {
            numEaten = numEaten + 1;
            AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DrawCardAction(AbstractDungeon.player, this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(2));
            return;
        }
    }

    public AbstractCard makeCopy() {
        return new SlimeTap();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);


        }
    }
}

