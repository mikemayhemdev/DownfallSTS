package slimebound.cards;



import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;


public class zzzDuplicateSlimes extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzDuplicateSlimes";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/duplicateslimes.png";

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;

    public zzzDuplicateSlimes() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        int orbsize = p.orbs.size();
        int i = 0;
        String[] tempOrbs = new String[orbsize];
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                logger.info("Found a slime: " + o.ID);
                tempOrbs[i] = o.ID;
            }
            i++;
        }
        logger.info("Moving to spawn mode.");
        /*
        for (String s : tempOrbs) {
            if (s != null) {
                logger.info("Attempting to spawn a " + s);
                if (s == "SplitTorchHead") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.TorchHeadSlime(), false, false));
                }
                if (s == "SplitBruiser") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.AttackSlime(), false, false));
                }
                if (s == "SplitAcid") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.PoisonSlime(), false, false));
                }
                if (s == "SplitLicking") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.SlimingSlime(), false, false));
                }
                if (s == "SplitBronze") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.BronzeSlime(), false, false));
                }
                if (s == "SplitLeeching") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.ShieldSlime(), false, false));
                }
                if (s == "SplitCultist") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.CultistSlime(), false, false));
                }
                if (s == "SplitGhostflame") {
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.HexSlime(), false, false));
                }


            }

        }
        */
        logger.info("Finished.");
    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public AbstractCard makeCopy() {
        return new zzzDuplicateSlimes();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);


        }
    }
}

