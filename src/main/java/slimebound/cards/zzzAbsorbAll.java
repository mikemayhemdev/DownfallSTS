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


public class zzzAbsorbAll extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzAbsorbAll";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/absorball.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public zzzAbsorbAll() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockgain = 0;
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {

                if (o instanceof SpawnedSlime) {


                    AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));
                    blockgain = blockgain + 3;

                }
            }
            if (blockgain > 0) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, blockgain));

            }
        }
    }

    public AbstractCard makeCopy() {
        return new zzzAbsorbAll();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);


        }
    }
}

