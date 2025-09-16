package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class SecondStrikePreviewCard extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SecondStrikePreviewCard");
    public static final String NAME;
    public static final String IMG_PATH = "cards/SecondStrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 7;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 2;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public SecondStrikePreviewCard() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        isEthereal = true;
        exhaust = true;


        //this.sockets.add(GuardianMod.socketTypes.RED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new SecondStrikePreviewCard();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //upgradeDamage(UPGRADE_BONUS);
            if (this.socketCount < 4) {
                this.socketCount++;
                this.saveGemMisc();
            }
        }
    }

    public void updateDescription() {
        this.initializeDescription();
    }
}


