package guardian.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import guardian.GuardianMod;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.patches.AbstractCardEnum;

public class MassTimeBomb extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("MassTimeBomb");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/massBomb.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 3;

    //TUNING CONSTANTS
    private static final int DAMAGE = 20;
    private static final int UPGRADE_DAMAGE = 10;
    private static final int TURNS = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public MassTimeBomb() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        int bombCount = 0;
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
            //GuardianMod.logger.info("i = " + i + " result = " + (AbstractDungeon.player.orbs.get(i) == null));
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                bombCount++;
            } else {
                //GuardianMod.logger.info("i = " + i + " not empty, " + AbstractDungeon.player.orbs.get(i));

            }

        }
        //GuardianMod.logger.info("bombcount = " + bombCount);
        for (int i = 0; i < bombCount; i++) {
            AbstractCard c = new TimeBomb();
            if (upgraded) c.upgrade();
            AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(c));

        }

        if (bombCount >= 2) {
            this.exhaust = true;
        }

    }

    public AbstractCard makeCopy() {
        return new MassTimeBomb();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean result;
        if (GuardianMod.canSpawnStasisOrb()) {
            result = true;
        } else {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            result = false;
        }
        return result;
    }
}


